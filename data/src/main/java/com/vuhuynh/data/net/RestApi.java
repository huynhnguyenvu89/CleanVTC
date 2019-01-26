package com.vuhuynh.data.net;

import com.vuhuynh.data.entity.UserEntity;
import io.reactivex.Observable;

import java.util.List;

/**
 * Rest API for retrieving data from the network.
 */
public interface RestApi {
    String API_BASE_URL =
            "https://raw.githubusercontent.com/android10/Sample-Data/master/Android-CleanArchitecture/";

    /** Api url for getting all users */
    String API_URL_GET_USER_LIST = API_BASE_URL + "users.json";
    /** Api url for getting a user profile: Remember to concatenate id + 'json' */
    String API_URL_GET_USER_DETAILS = API_BASE_URL + "user_";

    /**
     * Retrieve an {@link Observable} which will emit a List of {@link UserEntity}
     */
    Observable<List<UserEntity>> userEntityList();

    /**
     * Retrieves an {@link Observable} which will emit a {@link UserEntity}
     * @param usedId The user id to retrieve data
     */
    Observable<UserEntity> userEntityById(int usedId);
}
