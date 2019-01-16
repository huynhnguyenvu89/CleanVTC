package com.vuhuynh.data.repository.datasource;

import android.content.Context;
import android.support.annotation.NonNull;
import com.example.domain.User;
import com.vuhuynh.data.cache.UserCache;
import com.vuhuynh.data.entity.UserEntity;

import javax.inject.Inject;

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

    public UserDataStore createCloudUserDataStore(){
        return new CloudUserDataStore();
    }
}
