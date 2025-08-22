package com.example.storeproject.repository.admin;

import com.example.storeproject.entity.User;

public interface IAdminRepository {
    boolean register(User user);
    User login(String username, String password);
}
