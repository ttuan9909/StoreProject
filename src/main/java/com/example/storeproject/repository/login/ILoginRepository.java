package com.example.storeproject.repository.login;

import com.example.storeproject.entity.User;

import java.sql.Timestamp;

public interface ILoginRepository {
    User findByUsernameAndPassword(String username, String password);

    User findByUsername(String username);
    boolean usernameExists(String username);

    boolean insertUser(User user);
    boolean updateLastLogin(int userId, Timestamp ts);
}

