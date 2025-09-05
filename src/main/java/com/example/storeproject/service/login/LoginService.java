package com.example.storeproject.service.login;

import com.example.storeproject.entity.User;
import com.example.storeproject.repository.login.LoginRepository;
import com.example.storeproject.repository.login.ILoginRepository;

import java.sql.Timestamp;

public class LoginService implements ILoginService {
    private final ILoginRepository repo = new LoginRepository();

    @Override
    public User login(String username, String password) {
        return repo.findByUsernameAndPassword(username, password);
    }

    @Override
    public boolean register(User user) {
        return repo.insertUser(user);
    }

    @Override
    public boolean updateLastLogin(int userId, Timestamp ts) {
        if (ts == null) ts = new Timestamp(System.currentTimeMillis());
        try {
            return repo.updateLastLogin(userId, ts);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
