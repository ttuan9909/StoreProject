package com.example.storeproject.service.admin;

import com.example.storeproject.entity.User;
import com.example.storeproject.repository.admin.AdminRepository;
import com.example.storeproject.repository.admin.IAdminRepository;

public class AdminService implements IAdminService {
    private final IAdminRepository adminRepository = new AdminRepository();

    @Override
    public boolean registerAdmin(User user) {
        return adminRepository.register(user);
    }

    @Override
    public User loginAdmin(String username, String password) {
        return adminRepository.login(username, password);
    }
}
