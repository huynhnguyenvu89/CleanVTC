package com.example.domain.interactor;

import com.example.domain.executor.PostExecutionThread;
import com.example.domain.executor.ThreadExecutor;
import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.TestScheduler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class UseCaseTest {

    private UseCaseTestClass useCase;
    private TestDisposableObserver testObserver;

    @Mock
    private ThreadExecutor threadExecutor;
    @Mock
    private PostExecutionThread postExecutionThread;

    @Before
    public void setup(){
        this.useCase = new UseCaseTestClass(threadExecutor, postExecutionThread);
        this.testObserver = new TestDisposableObserver<Object>();
        given(postExecutionThread.getScheduler()).willReturn(new TestScheduler());
    }

    @Test
    public void testBuildUseCaseObservableReturnCorrectResult(){
        useCase.execute(testObserver, Params.EMPTY);
        assertThat(testObserver.valuesCount).isZero();
    }

    @Test
    public void testSubscriptionWhenExecutingUseCase(){
        useCase.execute(testObserver, Params.EMPTY);
        useCase.dispose();

        assertThat(testObserver.isDisposed()).isTrue();
    }

    @Test
    public void testShouldFailWhenExecuteWithNullObserver(){
        useCase.execute(null, Params.EMPTY);
    }

    private static class UseCaseTestClass extends UseCase<Object, Params> {

        UseCaseTestClass(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread){
            super(threadExecutor, postExecutionThread);
        }

        @Override
        Observable<Object> buildUseCaseObservable(Params params) {
            return Observable.empty();
        }

        @Override
        public void execute(DisposableObserver<Object> observer, Params params) {
            super.execute(observer, params);
        }
    }

    private static class Params {
        private static Params EMPTY = new Params();
        private Params(){}
    }

    private class TestDisposableObserver<T> extends DisposableObserver<T>{
        private int valuesCount = 0;
        @Override
        public void onNext(Object value) {
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
