package com.vuhuynh.data.repository.datasource;

import android.database.Observable;
import com.vuhuynh.data.cache.UserCache;
import com.vuhuynh.data.entity.UserEntity;

import javax.inject.Inject;
import java.util.List;

public class DiskUserDataStore implements UserDataStore {

    private UserCache userCache;
    @Inject
    DiskUserDataStore(UserCache userCache){
        this.userCache = userCache;
    }

    @Override
    public Observable<List<UserEntity>> userEntityList() {
        return null;
    }

    @Override
    public Observable<UserEntity> userEntityDetails(int userId) {
        return null;
    }
}
