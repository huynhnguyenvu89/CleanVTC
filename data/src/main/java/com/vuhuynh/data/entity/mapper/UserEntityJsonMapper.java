package com.vuhuynh.data.entity.mapper;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.vuhuynh.data.entity.UserEntity;

import javax.inject.Inject;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Class used to transform from Json formatted String to valid objects.
 */
public class UserEntityJsonMapper {
    private final Gson gson;

    @Inject
    public UserEntityJsonMapper(){
        this.gson = new Gson();
    }

    /**
     * Transform from valid json string to {@link UserEntity}
     *
     * @param userJsonResponse A json representing a user profile.
     * @return {@link UserEntity}
     * @throws JsonSyntaxException if the json strong is not a valid json structure
     */
    public UserEntity transformUserEntity(String userJsonResponse) throws JsonSyntaxException {
        final Type userEntityType = new TypeToken<UserEntity>(){}.getType();
        return this.gson.fromJson(userJsonResponse, userEntityType);
    }

    /**
     * Transform from valid json string to List of {@link UserEntity}
     *
     * @param userListJson A json representing a collection of users.
     * @return list of {@link UserEntity}
     * @throws JsonSyntaxException if the json string is not a valid json structure.
     */
    public List<UserEntity> transformUserEntityCollection(String userListJson) throws JsonSyntaxException {
        final Type userEntityListType = new TypeToken<List<UserEntity>>(){}.getType();
        return this.gson.fromJson(userListJson, userEntityListType);
    }
}
