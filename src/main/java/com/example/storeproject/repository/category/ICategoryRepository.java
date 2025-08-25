package com.example.storeproject.repository.category;

import com.example.storeproject.entity.Category;

import java.util.List;

public interface ICategoryRepository {
    List<Category> findAll();
    Category findById(int id);
    boolean save(Category c);
    void update(Category c);
    boolean deleteById(int id);
    List<Category> searchByName(String keyword);
}
