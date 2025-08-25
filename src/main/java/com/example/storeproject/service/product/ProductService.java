package com.example.storeproject.service.product;

import com.example.storeproject.entity.Product;
import com.example.storeproject.repository.product.IProductRepository;
import com.example.storeproject.repository.product.ProductRepository;

import java.util.List;

public class ProductService implements IProductService {
    private final IProductRepository productRepository;
    
    public ProductService() {
        this.productRepository = new ProductRepository();
    }
    
    @Override
    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }
    
    @Override
    public List<Product> searchProducts(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllProducts();
        }
        return productRepository.searchProducts(keyword.trim());
    }
    
    @Override
    public List<Product> getProductsByCategory(int categoryId) {
        return productRepository.getProductsByCategory(categoryId);
    }
    
    @Override
    public Product getProductById(int productId) {
        return productRepository.getProductById(productId);
    }
    
    @Override
    public List<Product> getProductsWithPagination(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return productRepository.getProductsWithPagination(offset, pageSize);
    }
    
    @Override
    public int getTotalPages(int pageSize) {
        int totalProducts = productRepository.getTotalProductCount();
        return (int) Math.ceil((double) totalProducts / pageSize);
    }
}
