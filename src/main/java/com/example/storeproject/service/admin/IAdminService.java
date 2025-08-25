package com.example.storeproject.service.admin;

import com.example.storeproject.entity.User;

public interface IAdminService {
    boolean registerAdmin(User user);
    User loginAdmin(String username, String password);
}
