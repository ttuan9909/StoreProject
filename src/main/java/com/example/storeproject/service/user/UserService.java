package com.example.storeproject.service.user;

import com.example.storeproject.entity.User;
import com.example.storeproject.repository.user.IUserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class UserService implements IUserService{
    private final IUserRepository repo;

    public UserService(IUserRepository repo) {
        this.repo = repo;
    }

    @Override
    public PagedResult<User> listUsers(int page, int size, String keyword, String role) {
        List<User> items = repo.findAll(page, size, keyword, role);
        int total = repo.countAll(keyword, role);
        return new PagedResult<>(items, page, size, total);
    }

    @Override
    public int countUsers(String keyword, String role) {
        return repo.countAll(keyword, role);
    }


    @Override
    public int deleteInactiveByDays(int days) {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(days);
        return repo.deleteInactiveSince(cutoff);
    }

    @Override
    public int deleteOlderThanDaysByCreatedAt(int days) {
        LocalDate cutoff = LocalDate.now().minusDays(days);
        return repo.deleteCreatedBefore(cutoff);
    }
    @Override
    public int deactivateInactiveByDays(int days) {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(days);
        return repo.deactivateInactiveSince(cutoff);
    }

    @Override
    public int setActive(int userId, boolean active) {
        return repo.updateIsActive(userId, active);
    }
}
