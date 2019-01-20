package com.vuhuynh.data.repository;

import com.example.domain.User;
import com.example.domain.repository.UserRepository;
import com.vuhuynh.data.entity.mapper.UserEntityDataMapper;
import com.vuhuynh.data.repository.datasource.UserDataStore;
import com.vuhuynh.data.repository.datasource.UserDataStoreFactory;
import io.reactivex.Observable;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

/**
 * {@link UserRepository} for retrieving user data
 */
@Singleton
public class UserDataRepository implements UserRepository {

    private final UserDataStoreFactory userDataStoreFactory;
    private final UserEntityDataMapper userEntityDataMapper;

    /**
     * Construct a {@link UserDataRepository}
     *
     * @param userDataStoreFactory A factory to construct different data source implementation
     * @param userEntityDataMapper {@link UserEntityDataMapper}
     */
    @Inject
    UserDataRepository(UserDataStoreFactory userDataStoreFactory, UserEntityDataMapper userEntityDataMapper){
        this.userDataStoreFactory = userDataStoreFactory;
        this.userEntityDataMapper = userEntityDataMapper;
    }

    @Override
    public Observable<List<User>> users() {
        //We always get all users from the cloud
        UserDataStore userDataStore = this.userDataStoreFactory.createCloudUserDataStore();
        return userDataStore.userEntityList().map(this.userEntityDataMapper::transform);
    }

    @Override
    public Observable<User> user(int userId) {
        UserDataStore userDataStore = this.userDataStoreFactory.create(userId);
        return userDataStore.userEntityDetails(userId).map(this.userEntityDataMapper::transform);
    }
}
