package com.example.domain.repository;

import com.example.domain.User;
import io.reactivex.Observable;
import java.util.List;

/**
 * Interface that represents a Repository for getting {@link User} related data
 */
public interface UserRepository {

    /**
     * Get an {@link Observable} which will emit a list of {@link User}.
     */
    Observable<List<User>> users();

    /**
     * Get an {@link Observable} which will emit a {@link User}
     */
    Observable<User> user(final int userId);
}
