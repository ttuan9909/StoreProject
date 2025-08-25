package com.example.storeproject.service.product;

import com.example.storeproject.dto.ProductDto;
import com.example.storeproject.entity.Product;

import com.example.storeproject.repository.product.IProductRepository;
import com.example.storeproject.repository.product.ProductRepository;

import java.util.List;

public class ProductService implements IProductService {

    private final IProductRepository repository = new ProductRepository();

    @Override
    public List<ProductDto> findAll() { return repository.findAll(); }

    @Override
    public ProductDto getById(int id) { return repository.findById(id); }

    @Override
    public List<ProductDto> search(String keyword) { return repository.searchByName(keyword); }

    @Override
    public boolean save(String name, double price, String description, int quantity,
                       String image, int categoryId) {
        // nếu bạn muốn trả boolean thì đổi interface IProductService tương ứng
        boolean ok = repository.save(new Product(0, name, price, description, quantity, image, categoryId));
        if (!ok) throw new RuntimeException("Tạo sản phẩm thất bại");
        return ok;
    }

    @Override
    public boolean update(int id, String name, double price, String description, int quantity,
                          String image, int categoryId) {
        if (repository.findById(id) == null) return false;
        Product toUpdate = new Product(id, name, price, description, quantity, image, categoryId);
        repository.update(toUpdate);
        return true;
    }

    @Override
    public boolean delete(int id) { return repository.deleteById(id); }
}
