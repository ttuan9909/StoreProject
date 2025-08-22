package com.example.storeproject.service.admin;

import com.example.storeproject.entity.admin.Admin;
import com.example.storeproject.repository.admin.AdminRepository;
import com.example.storeproject.repository.admin.IAdminRepository;

public class AdminService implements IAdminService {
    private final IAdminRepository adminRepository = new AdminRepository();

    @Override
    public boolean registerAdmin(Admin admin) {
        return adminRepository.register(admin);
    }

    @Override
    public Admin loginAdmin(String username, String password) {
        return adminRepository.login(username, password);
    }
}
