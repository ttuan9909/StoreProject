package com.example.storeproject.service.login;

import com.example.storeproject.entity.User;

public interface ILoginService {
    User login(String username, String password);
    boolean register(User user);
}
