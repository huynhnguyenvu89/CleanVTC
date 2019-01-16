package com.example.domain.interactor;

import com.example.domain.Preconditions;
import com.example.domain.executor.PostExecutionThread;
import com.example.domain.executor.ThreadExecutor;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This interface represents an execution unit for different use cases (This means any use case
 * in the application should implement this contract)
 *
 * By convention each UseCase implementation will return the result using a {@link CompositeDisposable}
 * that will execute its job in a background thread and will post the result in the UI thread.
 */
public abstract class UseCase<T, Params> {

    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;
    private final CompositeDisposable compositeDisposable;

    UseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread){
        this.threadExecutor= threadExecutor;
        this.postExecutionThread = postExecutionThread;
        this.compositeDisposable = new CompositeDisposable();
    }

    /**
     * Builds an {@link java.util.Observable} which will be used when executing the current {@link UseCase}
     */
    abstract Observable<T> buildUseCaseObservable(Params params);

    public void execute(DisposableObserver<T> observer, Params params) {
        Preconditions.checkNotNull(observer);
        final Observable<T> observable = this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler());
        addDisposable(observable.subscribeWith(observer));
    }

    /**
     * Dispose from current {@link CompositeDisposable}
     */
    public void dispose(){
        if (!compositeDisposable.isDisposed()){
            compositeDisposable.dispose();
        }
    }

    /**
     * Add a {@link Disposable} to {@link CompositeDisposable}
     * @param disposable
     */
    private void addDisposable(Disposable disposable){
        Preconditions.checkNotNull(disposable);
        Preconditions.checkNotNull(compositeDisposable);
        compositeDisposable.add(disposable);
    }
}
