package com.example.storeproject.repository.category;

import com.example.storeproject.entity.Category;
import java.util.List;

public interface ICategoryRepository {
    List<Category> getAllCategories();
    Category getCategoryById(int categoryId);
}
