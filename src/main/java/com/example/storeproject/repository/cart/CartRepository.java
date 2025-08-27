package com.example.storeproject.repository.cart;

import com.example.storeproject.entity.Cart;
import com.example.storeproject.entity.CartDetail;
import com.example.storeproject.repository.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CartRepository implements ICartRepository {
    
    @Override
    public Cart getCartByUserId(int userId) {
        String sql = "SELECT * FROM gio_hang WHERE ma_nguoi_dung = ?";
        
        try (Connection conn = DBConnection.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, userId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCart(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public Cart createCart(int userId) {
        String sql = "INSERT INTO gio_hang (ma_nguoi_dung) VALUES (?)";
        
        try (Connection conn = DBConnection.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setInt(1, userId);
            int affectedRows = ps.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int cartId = rs.getInt(1);
                        return getCartByUserId(userId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public boolean addProductToCart(int cartId, int productId, int quantity, double price) {
        // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        String checkSql = "SELECT * FROM chi_tiet_gio_hang WHERE ma_gio_hang = ? AND ma_san_pham = ?";
        
        try (Connection conn = DBConnection.getConnectDB();
             PreparedStatement checkPs = conn.prepareStatement(checkSql)) {
            
            checkPs.setInt(1, cartId);
            checkPs.setInt(2, productId);
            
            try (ResultSet rs = checkPs.executeQuery()) {
                if (rs.next()) {
                    // Sản phẩm đã có, cập nhật số lượng
                    return updateProductQuantity(cartId, productId, quantity);
                } else {
                    // Sản phẩm chưa có, thêm mới
                    String insertSql = "INSERT INTO chi_tiet_gio_hang (ma_gio_hang, ma_san_pham, so_luong, gia) VALUES (?, ?, ?, ?)";
                    
                    try (PreparedStatement insertPs = conn.prepareStatement(insertSql)) {
                        insertPs.setInt(1, cartId);
                        insertPs.setInt(2, productId);
                        insertPs.setInt(3, quantity);
                        insertPs.setDouble(4, price);
                        
                        return insertPs.executeUpdate() > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public boolean updateProductQuantity(int cartId, int productId, int quantity) {
        if (quantity <= 0) {
            return removeProductFromCart(cartId, productId);
        }
        
        String sql = "UPDATE chi_tiet_gio_hang SET so_luong = ? WHERE ma_gio_hang = ? AND ma_san_pham = ?";
        
        try (Connection conn = DBConnection.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, quantity);
            ps.setInt(2, cartId);
            ps.setInt(3, productId);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public boolean removeProductFromCart(int cartId, int productId) {
        String sql = "DELETE FROM chi_tiet_gio_hang WHERE ma_gio_hang = ? AND ma_san_pham = ?";
        
        try (Connection conn = DBConnection.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, cartId);
            ps.setInt(2, productId);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public List<CartDetail> getCartItems(int cartId) {
        List<CartDetail> cartItems = new ArrayList<>();
        String sql = "SELECT * FROM chi_tiet_gio_hang WHERE ma_gio_hang = ?";
        
        try (Connection conn = DBConnection.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, cartId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CartDetail cartItem = mapResultSetToCartDetail(rs);
                    cartItems.add(cartItem);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItems;
    }
    
    @Override
    public boolean clearCart(int cartId) {
        String sql = "DELETE FROM chi_tiet_gio_hang WHERE ma_gio_hang = ?";
        try (Connection conn = DBConnection.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            int affectedRows = ps.executeUpdate();
            System.out.println("CartRepository: clearCart - cartId=" + cartId + ", affectedRows=" + affectedRows);
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("CartRepository: SQLException in clearCart - " + e.getMessage() + ", SQLState: " + e.getSQLState() + ", ErrorCode: " + e.getErrorCode());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public double getCartTotal(int cartId) {
        String sql = "SELECT SUM(so_luong * gia) as total FROM chi_tiet_gio_hang WHERE ma_gio_hang = ?";
        
        try (Connection conn = DBConnection.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, cartId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
    
    private Cart mapResultSetToCart(ResultSet rs) throws SQLException {
        Cart cart = new Cart();
        cart.setCartId(rs.getInt("ma_gio_hang"));
        cart.setUserId(rs.getInt("ma_nguoi_dung"));
        
        Timestamp createdAt = rs.getTimestamp("ngay_tao");
        if (createdAt != null) {
            cart.setCreatedAt(createdAt.toLocalDateTime());
        }
        
        Timestamp updatedAt = rs.getTimestamp("ngay_cap_nhat");
        if (updatedAt != null) {
            cart.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        
        return cart;
    }
    
    private CartDetail mapResultSetToCartDetail(ResultSet rs) throws SQLException {
        CartDetail cartDetail = new CartDetail();
        cartDetail.setCartId(rs.getInt("ma_gio_hang"));
        cartDetail.setProductId(rs.getInt("ma_san_pham"));
        cartDetail.setQuantity(rs.getInt("so_luong"));
        cartDetail.setPrice(rs.getDouble("gia"));
        return cartDetail;
    }
}
