package com.example.storeproject.repository.order;

import com.example.storeproject.entity.Order;
import com.example.storeproject.entity.OrderDetail;
import com.example.storeproject.repository.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository implements IOrderRepository {
    
    @Override
    public Order createOrder(Order order) {
        String sql = "INSERT INTO don_hang (ma_nguoi_dung, trang_thai, tong_tien, ma_khuyen_mai) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setInt(1, order.getUserId());
            ps.setString(2, order.getOrderStatus());
            ps.setDouble(3, order.getTotalPrice());
            
            if (order.getDiscountId() != null) {
                ps.setInt(4, order.getDiscountId());
            } else {
                ps.setNull(4, Types.INTEGER);
            }
            
            int affectedRows = ps.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int orderId = rs.getInt(1);
                        order.setOrderId(orderId);
                        return order;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public boolean createOrderDetail(OrderDetail orderDetail) {
        String sql = "INSERT INTO chi_tiet_don_hang (ma_don_hang, ma_san_pham, so_luong, gia) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, orderDetail.getOrderId());
            ps.setInt(2, orderDetail.getProductId());
            ps.setInt(3, orderDetail.getQuantity());
            ps.setDouble(4, orderDetail.getPrice());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public List<Order> getOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM don_hang WHERE ma_nguoi_dung = ? ORDER BY ngay_dat DESC";
        
        try (Connection conn = DBConnection.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, userId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order order = mapResultSetToOrder(rs);
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
    
    @Override
    public Order getOrderById(int orderId) {
        String sql = "SELECT * FROM don_hang WHERE ma_don_hang = ?";
        
        try (Connection conn = DBConnection.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, orderId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToOrder(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public List<OrderDetail> getOrderDetails(int orderId) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        String sql = "SELECT * FROM chi_tiet_don_hang WHERE ma_don_hang = ?";
        
        try (Connection conn = DBConnection.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, orderId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderDetail orderDetail = mapResultSetToOrderDetail(rs);
                    orderDetails.add(orderDetail);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderDetails;
    }
    
    @Override
    public boolean updateOrderStatus(int orderId, String status) {
        String sql = "UPDATE don_hang SET trang_thai = ? WHERE ma_don_hang = ?";
        
        try (Connection conn = DBConnection.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, status);
            ps.setInt(2, orderId);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private Order mapResultSetToOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setOrderId(rs.getInt("ma_don_hang"));
        order.setUserId(rs.getInt("ma_nguoi_dung"));
        order.setOrderStatus(rs.getString("trang_thai"));
        order.setTotalPrice(rs.getDouble("tong_tien"));
        
        int discountId = rs.getInt("ma_khuyen_mai");
        if (!rs.wasNull()) {
            order.setDiscountId(discountId);
        }
        
        Timestamp orderDate = rs.getTimestamp("ngay_dat");
        if (orderDate != null) {
            order.setOrderDate(orderDate.toLocalDateTime());
        }
        
        return order;
    }
    
    private OrderDetail mapResultSetToOrderDetail(ResultSet rs) throws SQLException {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(rs.getInt("ma_don_hang"));
        orderDetail.setProductId(rs.getInt("ma_san_pham"));
        orderDetail.setQuantity(rs.getInt("so_luong"));
        orderDetail.setPrice(rs.getDouble("gia"));
        return orderDetail;
    }
}
