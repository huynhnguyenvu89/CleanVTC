package com.vuhuynh.data.exception;

/**
 * Exception throw by the application when a User search can't return a valid result.
 */
public class UserNotFoundException extends Exception{

    public UserNotFoundException(){
        super("User not found exception");
    }
}
