package com.example.storeproject.service.product;

import com.example.storeproject.dto.ProductDto;

import java.util.List;

public interface IProductService {
    List<ProductDto> findAll();

    ProductDto getById(int id);                         // trả về DTO

    boolean save(String name, double price, String description, int quantity,
                 String image, int categoryId);

    boolean update(int id, String name, double price, String description, int quantity,
                   String image, int categoryId) ;

    boolean delete(int id);

    List<ProductDto> search(String keyword);
}