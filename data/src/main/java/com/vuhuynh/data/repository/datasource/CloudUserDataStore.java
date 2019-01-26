package com.vuhuynh.data.repository.datasource;

import com.vuhuynh.data.cache.UserCache;
import com.vuhuynh.data.entity.UserEntity;
import com.vuhuynh.data.net.RestApi;
import io.reactivex.Observable;

import java.util.List;

public class CloudUserDataStore implements UserDataStore {

    private final RestApi restApi;
    private final UserCache userCache;

    CloudUserDataStore(RestApi restApi, UserCache userCache){
        this.restApi = restApi;
        this.userCache = userCache;
    }

    @Override
    public Observable<List<UserEntity>> userEntityList() {
        return this.restApi.userEntityList();
    }

    @Override
    public Observable<UserEntity> userEntityDetails(int userId) {
        return this.restApi.userEntityById(userId);
    }
}
