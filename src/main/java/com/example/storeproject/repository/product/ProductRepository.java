package com.example.storeproject.repository.product;

import com.example.storeproject.entity.Product;
import com.example.storeproject.repository.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository implements IProductRepository {
    
    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM san_pham ORDER BY ngay_tao DESC";
        
        try (Connection conn = DBConnection.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Product product = mapResultSetToProduct(rs);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    
    @Override
    public List<Product> searchProducts(String keyword) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM san_pham WHERE ten_san_pham LIKE ? OR mo_ta LIKE ? ORDER BY ngay_tao DESC";
        
        try (Connection conn = DBConnection.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product product = mapResultSetToProduct(rs);
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    
    @Override
    public List<Product> getProductsByCategory(int categoryId) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM san_pham WHERE ma_danh_muc = ? ORDER BY ngay_tao DESC";
        
        try (Connection conn = DBConnection.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, categoryId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product product = mapResultSetToProduct(rs);
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    
    @Override
    public Product getProductById(int productId) {
        String sql = "SELECT * FROM san_pham WHERE ma_san_pham = ?";
        
        try (Connection conn = DBConnection.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, productId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToProduct(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public List<Product> getProductsWithPagination(int offset, int limit) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM san_pham ORDER BY ngay_tao DESC LIMIT ? OFFSET ?";
        
        try (Connection conn = DBConnection.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product product = mapResultSetToProduct(rs);
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    
    @Override
    public int getTotalProductCount() {
        String sql = "SELECT COUNT(*) FROM san_pham";
        
        try (Connection conn = DBConnection.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    private Product mapResultSetToProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setProductId(rs.getInt("ma_san_pham"));
        product.setProductName(rs.getString("ten_san_pham"));
        product.setPrice(rs.getDouble("gia"));
        product.setDescription(rs.getString("mo_ta"));
        product.setQuantity(rs.getInt("so_luong"));
        product.setImage(rs.getString("hinh_anh"));
        product.setCategoryId(rs.getInt("ma_danh_muc"));
        
        Date dateCreated = rs.getDate("ngay_tao");
        if (dateCreated != null) {
            product.setDateCreated(dateCreated.toLocalDate());
        }
        
        return product;
    }
}
