package com.vuhuynh.data.repository.datasource;

import android.database.Observable;
import com.vuhuynh.data.entity.UserEntity;

import java.util.List;

/**
 * Interface that represents a data store from where data is retrieved.
 */
public interface UserDataStore {
    /**
     * Get an {@link java.util.Observable} which will emit a list of {@link UserEntity}
     * @return list of {@link UserEntity}
     */
    Observable<List<UserEntity>> userEntityList();

    /**
     * Get an {@link java.util.Observable} which will emit a {@link UserEntity} by its id
     * @param userId user unique id
     * @return {@link UserEntity} with corresponding ID
     */
    Observable<UserEntity> userEntityDetails(final int userId);
}
