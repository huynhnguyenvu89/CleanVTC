package com.example.domain.interactor;

import com.example.domain.User;
import com.example.domain.executor.PostExecutionThread;
import com.example.domain.executor.ThreadExecutor;
import com.example.domain.repository.UserRepository;
import io.reactivex.Observable;

import javax.inject.Inject;
import java.util.List;

public class GetUserList extends UseCase<List<User>, Void>{

    private final UserRepository userRepository;

    @Inject
    GetUserList(UserRepository userRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread){
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override
    Observable<List<User>> buildUseCaseObservable(Void aVoid) {
        return this.userRepository.users();
    }
}
