package com.example.domain.interactor;

import com.example.domain.executor.PostExecutionThread;
import com.example.domain.executor.ThreadExecutor;
import com.example.domain.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class GetUserListTest {

    private GetUserList getUserList;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ThreadExecutor threadExecutor;
    @Mock
    private PostExecutionThread postExecutionThread;

    @Before
    public void setup(){
        getUserList = new GetUserList(userRepository, threadExecutor, postExecutionThread);
    }

    @Test
    public void testGetUserListUseCaseObsevableHappyCase(){
        getUserList.buildUseCaseObservable(null);

        verify(userRepository).users();
        verifyNoMoreInteractions(userRepository);
        verifyZeroInteractions(threadExecutor);
        verifyZeroInteractions(postExecutionThread);
    }
}
