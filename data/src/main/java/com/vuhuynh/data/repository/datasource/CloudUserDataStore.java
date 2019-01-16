package com.vuhuynh.data.repository.datasource;

import android.database.Observable;
import com.vuhuynh.data.entity.UserEntity;

import java.util.List;

public class CloudUserDataStore implements UserDataStore {
    @Override
    public Observable<List<UserEntity>> userEntityList() {
        return null;
    }

    @Override
    public Observable<UserEntity> userEntityDetails(int userId) {
        return null;
    }
}
