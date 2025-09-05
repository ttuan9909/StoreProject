package com.example.storeproject.repository.user;

import com.example.storeproject.entity.User;

import java.util.List;

public interface IUserRepository {
    List<User> findAll(int page, int size, String keyword, String role);
    int countAll(String keyword, String role);
    int deleteInactiveSince(java.time.LocalDateTime cutoff);
    int deleteCreatedBefore(java.time.LocalDate cutoffCreated);
    int updateIsActive(int userId, boolean active);
    int deactivateInactiveSince(java.time.LocalDateTime cutoff);
    User findByUsername(String username);
}