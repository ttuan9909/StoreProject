package com.example.storeproject.repository.product;

import com.example.storeproject.entity.Product;
import java.util.List;

public interface IProductRepository {
    List<Product> getAllProducts();
    List<Product> searchProducts(String keyword);
    List<Product> getProductsByCategory(int categoryId);
    Product getProductById(int productId);
    List<Product> getProductsWithPagination(int offset, int limit);
    int getTotalProductCount();
}
