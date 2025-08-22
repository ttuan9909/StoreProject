package com.example.storeproject.service.admin;

import com.example.storeproject.entity.admin.Admin;

public interface IAdminService {
    boolean registerAdmin(Admin admin);
    Admin loginAdmin(String email, String password);
}
