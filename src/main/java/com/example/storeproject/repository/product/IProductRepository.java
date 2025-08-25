package com.example.storeproject.repository.product;

import com.example.storeproject.dto.ProductDto;
import com.example.storeproject.entity.Product;

import java.util.List;

public interface IProductRepository {
    List<ProductDto> findAll();
    ProductDto findById(int id);
    boolean update(Product product);       // UPDATE theo productId

    boolean save(Product product);

    boolean deleteById(int id);      // DELETE theo productId (bắt lỗi FK)
    List<ProductDto> searchByName(String keyword);

}
