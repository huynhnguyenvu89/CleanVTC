package com.example.domain.interactor;

import com.example.domain.User;
import com.example.domain.executor.PostExecutionThread;
import com.example.domain.executor.ThreadExecutor;
import com.example.domain.repository.UserRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class GetUserDetailsTest {

    private GetUserDetails getUserDetails;
    private static final int USER_ID = 123;

    @Mock
    private ThreadExecutor threadExecutor;
    @Mock
    private PostExecutionThread postExecutionThread;
    @Mock
    private UserRepository userRepository;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setup(){
        getUserDetails = new GetUserDetails(userRepository, threadExecutor, postExecutionThread);
    }

    @Test
    public void testShouldFailWhenNoOrEmptyParameters(){
        expectedException.expect(NullPointerException.class);
        getUserDetails.buildUseCaseObservable(null);
    }

    @Test
    public void testGetUserDetailsUseCaseObservableHappyCase(){
        getUserDetails.buildUseCaseObservable(GetUserDetails.Params.forUser(USER_ID));

        verify(userRepository).user(USER_ID);
        verifyNoMoreInteractions(userRepository);
        verifyZeroInteractions(postExecutionThread);
        verifyZeroInteractions(threadExecutor);
    }
}
