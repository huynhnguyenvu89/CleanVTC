package com.vuhuynh.data.repository.datasource;

import android.content.Context;
import android.support.annotation.NonNull;
import com.vuhuynh.data.cache.UserCache;
import com.vuhuynh.data.entity.mapper.UserEntityJsonMapper;
import com.vuhuynh.data.net.RestApi;
import com.vuhuynh.data.net.RestApiImpl;

import javax.inject.Inject;

/**
 * Factory that creates different implementation of {@link UserDataStore}
 */
public class UserDataStoreFactory {

    private final Context context;
    private final UserCache userCache;

    @Inject
    UserDataStoreFactory(@NonNull Context context, @NonNull UserCache userCache){
        this.context = context;
        this.userCache = userCache;
    }

    /**
     * Create a {@link UserDataStore} from a user ID.
     *
     * @param userId to create {@link UserDataStore} from
     * @return {@link UserDataStore} with associated ID.
     */
    public UserDataStore create(int userId) {
        UserDataStore userDataStore;

        if (!this.userCache.isExpired() && this.userCache.isCached(userId)) {
            userDataStore = new DiskUserDataStore(this.userCache);
        } else {
            userDataStore = createCloudUserDataStore();
        }
         return userDataStore;
    }

    /**
     * Create a {@link UserDataStore} to retrieve data from the cloud.
     * @return
     */
    public UserDataStore createCloudUserDataStore(){
        final UserEntityJsonMapper userEntityJsonMapper = new UserEntityJsonMapper();
        final RestApi restApi = new RestApiImpl(context, userEntityJsonMapper);
        return new CloudUserDataStore(restApi, userCache);
    }
}
