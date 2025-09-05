package com.example.storeproject.service.product;

import com.example.storeproject.dto.ProductDto;
import com.example.storeproject.entity.Product;
import com.example.storeproject.repository.product.IProductRepository;
import com.example.storeproject.repository.product.ProductRepository;

import java.util.List;

public class ProductService implements IProductService {


    private final IProductRepository productRepository = new ProductRepository();

    @Override
    public List<ProductDto> findAll() { return productRepository.findAll(); }

    @Override
    public ProductDto getById(int id) { return productRepository.findById(id); }

    @Override
    public List<ProductDto> search(String keyword) { return productRepository.searchByName(keyword); }

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

    @Override
    public boolean save(String name, double price, String description, int quantity,
                       String image, int categoryId) {
        // nếu bạn muốn trả boolean thì đổi interface IProductService tương ứng
        boolean ok = productRepository.save(new Product(0, name, price, description, quantity, image, categoryId));
        if (!ok) throw new RuntimeException("Tạo sản phẩm thất bại");
        return ok;
    }

    @Override
    public boolean update(int id, String name, double price, String description, int quantity,
                          String image, int categoryId) {
        if (productRepository.findById(id) == null) return false;
        Product toUpdate = new Product(id, name, price, description, quantity, image, categoryId);
        productRepository.update(toUpdate);
        return true;
    }

    @Override
    public boolean delete(int id) { return productRepository.deleteById(id); }

}
