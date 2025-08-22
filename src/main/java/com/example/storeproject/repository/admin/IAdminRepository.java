package com.example.storeproject.repository.admin;

import com.example.storeproject.entity.admin.Admin;

public interface IAdminRepository {
    boolean register(Admin admin);
    Admin login(String username, String password);
}
