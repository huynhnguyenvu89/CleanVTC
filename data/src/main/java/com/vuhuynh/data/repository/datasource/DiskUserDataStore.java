package com.vuhuynh.data.repository.datasource;

import com.vuhuynh.data.cache.UserCache;
import com.vuhuynh.data.entity.UserEntity;
import io.reactivex.Observable;

import javax.inject.Inject;
import java.util.List;

public class DiskUserDataStore implements UserDataStore {

    private UserCache userCache;

    /**
     * Construct a {@link UserDataStore} based file system data store.
     *
     * @param userCache a {@link UserCache} to cache data retrieved from the api.
     */
    @Inject
    DiskUserDataStore(UserCache userCache){
        this.userCache = userCache;
    }

    @Override
    public Observable<List<UserEntity>> userEntityList() {
        throw new UnsupportedOperationException("TODO: Operation is not available.");
    }

    @Override
    public Observable<UserEntity> userEntityDetails(int userId) {
        return this.userCache.get(userId);
    }
}
