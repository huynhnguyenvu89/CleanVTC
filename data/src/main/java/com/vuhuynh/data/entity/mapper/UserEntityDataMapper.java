package com.vuhuynh.data.entity.mapper;


import com.example.domain.User;
import com.vuhuynh.data.entity.UserEntity;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Mapper class used to transform {@link UserEntity} (in data layer) to {@link User} in the domain layer
 */
public class UserEntityDataMapper {

    @Inject
    UserEntityDataMapper(){}

    /**
     * Transform a {@link UserEntity} to a {@link User}
     * @param userEntity Object to be transformed
     * @return {@link User} if valid {@link UserEntity} otherwise null
     */
    public User transform(UserEntity userEntity) {
       User user = null;
       if (userEntity != null){
           user = new User(userEntity.getUserId());
           user.setCoverUrl(userEntity.getCoverUrl());
           user.setFirstName(userEntity.getFullName());
           user.setDescription(userEntity.getDescription());
           user.setFollowers(userEntity.getFollowers());
           user.setEmail(userEntity.getEmail());
       }
       return user;
    }

    /**
     * Transform a List of {@link UserEntity} into a List of {@link User}
     * @param userEntityCollection Object collection to be transformed
     * @return List of {@link User} if valid input otherwise null.
     */
    public List<User> transform(Collection<UserEntity> userEntityCollection){
        final List<User> userList = new ArrayList<>();
        for (UserEntity userEntity : userEntityCollection){
            final User user = transform(userEntity);
            if (user != null) {
                userList.add(user);
            }
        }
        return userList;
    }
}
