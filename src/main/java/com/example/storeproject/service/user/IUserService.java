package com.example.storeproject.service.user;

import com.example.storeproject.entity.User;

import java.util.List;

public interface IUserService {
    class PagedResult<T> {
        public final List<T> items;
        public final int page, size, total;
        public PagedResult(List<T> items, int page, int size, int total) {
            this.items = items; this.page = page; this.size = size; this.total = total;
        }
    }

    PagedResult<User> listUsers(int page, int size, String keyword, String role);
    int countUsers(String keyword, String role);
    int deleteInactiveByDays(int days);
    int deleteOlderThanDaysByCreatedAt(int days);
    int deactivateInactiveByDays(int days);
    int setActive(int userId, boolean active);

}
