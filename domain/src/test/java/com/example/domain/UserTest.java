package com.example.domain;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

    private static final int FAKE_USER_ID = 0;
    private User user;

    @Before
    public void setup(){
        user = new User(FAKE_USER_ID);
    }

    @Test
    public void testUserConstructorHappyCase(){
        final int userId = user.getUserId();
        assertThat(userId).isEqualTo(FAKE_USER_ID);
    }
}
