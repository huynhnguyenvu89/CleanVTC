package com.example.domain.interactor;

import com.example.domain.Preconditions;
import com.example.domain.User;
import com.example.domain.executor.PostExecutionThread;
import com.example.domain.executor.ThreadExecutor;
import com.example.domain.repository.UserRepository;
import io.reactivex.Observable;

import javax.inject.Inject;

public class GetUserDetails extends UseCase<User, GetUserDetails.Params> {

    private final UserRepository userRepository;

    @Inject
    GetUserDetails(UserRepository userRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread){
        super (threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override
    Observable<User> buildUseCaseObservable(Params params) {
        Preconditions.checkNotNull(params);
        return this.userRepository.user(params.userId);
    }

    public static final class Params {
        private final int userId;

        private Params(int userId){
            this.userId = userId;
        }

        public static Params forUser(int userId) {
            return new Params(userId);
        }
    }
}
