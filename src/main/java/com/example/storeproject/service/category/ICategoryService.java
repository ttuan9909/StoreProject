package com.example.storeproject.service.category;

import com.example.storeproject.entity.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> getAll();
    Category getById(int categoryId);
    void create(String categoryName);
    boolean update(int categoryId, String categoryName);
    boolean delete(int categoryId);
    List<Category> search(String keyword);
}
