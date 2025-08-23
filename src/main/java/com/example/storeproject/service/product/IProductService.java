package com.example.storeproject.service.product;

import com.example.storeproject.entity.Product;
import java.util.List;

public interface IProductService {
    List<Product> getAllProducts();
    List<Product> searchProducts(String keyword);
    List<Product> getProductsByCategory(int categoryId);
    Product getProductById(int productId);
    List<Product> getProductsWithPagination(int page, int pageSize);
    int getTotalPages(int pageSize);
}
