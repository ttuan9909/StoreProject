package com.example.storeproject.repository.login;

import com.example.storeproject.entity.User;

public interface ILoginRepository {
    User findByUsernameAndPassword(String username, String password);
    boolean insertUser(User user);
}

