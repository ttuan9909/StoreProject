package com.example.storeproject.service.login;

import com.example.storeproject.entity.User;
import com.example.storeproject.repository.login.LoginRepository;
import com.example.storeproject.repository.login.ILoginRepository;

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
}
