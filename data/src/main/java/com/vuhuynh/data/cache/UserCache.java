package com.vuhuynh.data.cache;

import com.vuhuynh.data.entity.UserEntity;
import io.reactivex.Observable;


/**
 * An interface representing a user Cache
 */
public interface UserCache {

    /**
     * Get an {@link Observable} which emits an {@link UserEntity}
     *
     * @param userId
     * @return
     */
    Observable<UserEntity> get(final int userId);

    /**
     * Put an element into the cache.
     *
     * @param userEntity
     */
    void put(UserEntity userEntity);

    /**
     * Checks if an element {@link UserEntity} exists in the cache.
     *
     * @param userId The id used to look for inside the cache.
     * @return true if the element is cached, false otherwise.
     */
    boolean isCached(final int userId);

    /**
     * Checks if the cache is expired.
     *
     * @return true if the cache is expired, false otherwise.
     */
    boolean isExpired();

    /**
     * Evict all the elements of the cache.
     */
    void evictAll();
}
