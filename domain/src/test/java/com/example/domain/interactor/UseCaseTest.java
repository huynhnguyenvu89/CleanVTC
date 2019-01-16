package com.example.domain.interactor;

import com.example.domain.executor.PostExecutionThread;
import com.example.domain.executor.ThreadExecutor;
import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.TestScheduler;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class UseCaseTest {
    private UseCaseTestClass useCase;

    @Mock
    private ThreadExecutor threadExecutor;
    @Mock
    private PostExecutionThread postExecutionThread;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private TestDisposableObserver<Object> disposableObserver;

    @Before
    public void setup(){
        useCase = new UseCaseTestClass(threadExecutor, postExecutionThread);
        disposableObserver = new TestDisposableObserver<>();
        given(postExecutionThread.getScheduler()).willReturn(new TestScheduler());
    }

    @Test
    public void testBuildUseCaseObservableReturnCorrectResult(){
        useCase.execute(disposableObserver, new Params());
        assertThat(disposableObserver.valuesCount).isZero();
        assertThat(disposableObserver.isDisposed()).isFalse();
        useCase.dispose();
        assertThat(disposableObserver.isDisposed()).isTrue();
    }

    @Test
    public void testSubscriptionWhenExecutingUseCase(){
        useCase.execute(disposableObserver, new Params());
        useCase.dispose();
        assertThat(disposableObserver.isDisposed()).isTrue();
    }

    @Test
    public void testShouldFailWhenExecuteWithNullObserver(){
        expectedException.expect(NullPointerException.class);
        useCase.execute(null, new Params());
    }

    private class UseCaseTestClass extends UseCase<Object, Params>{

        UseCaseTestClass(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread){
            super(threadExecutor, postExecutionThread);
        }

        @Override
        Observable<Object> buildUseCaseObservable(Params params) {
            return Observable.empty();
        }
    }

    private static class Params {
        private Params (){}
    }

    private static class TestDisposableObserver<T> extends DisposableObserver<T> {
        private int valuesCount = 0;
        @Override
        public void onNext(T value) {
            valuesCount ++;
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }
}
