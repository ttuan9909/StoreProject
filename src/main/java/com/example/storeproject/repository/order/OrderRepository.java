package com.example.storeproject.repository.order;

import com.example.storeproject.database.DatabaseConnection;
import com.example.storeproject.entity.order.Order;
import com.example.storeproject.entity.order.OrderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository implements IOrderRepository {


    @Override
    public List<Order> findAll() {
        List<Order> list = new ArrayList<>();
        String sql = """
                select dh.ma_don_hang, nd.ho_ten, dh.ngay_dat, dh.trang_thai, dh.tong_tien
                from don_hang dh
                join nguoi_dung nd on dh.ma_nguoi_dung = nd.ma_nguoi_dung
                """;
        try (Connection connection = DatabaseConnection.getConnectDB();
             PreparedStatement pre = connection.prepareStatement(sql);
             ResultSet resultSet = pre.executeQuery()) {
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getInt(1));
                order.setCustomerName(resultSet.getString(2));
                order.setCreatedAt(resultSet.getTimestamp(3).toLocalDateTime());
                order.setStatus(resultSet.getString(4));
                order.setTotal(resultSet.getDouble(5));
                list.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Order> search(String keyword) {
        List<Order> list = new ArrayList<>();
        String sql = """
                select dh.ma_don_hang, nd.ho_ten, dh.ngay_dat, dh.trang_thai, dh.tong_tien
                from don_hang dh
                join nguoi_dung nd on dh.ma_nguoi_dung = nd.ma_nguoi_dung
                where dh.ma_don_hang like ? or nd.ho_ten like ?
                """;
        try (Connection connection = DatabaseConnection.getConnectDB();
             PreparedStatement pre = connection.prepareStatement(sql)) {
            pre.setString(1, "%" + keyword + "%");
            pre.setString(2, "%" + keyword + "%");
            ResultSet resultSet = pre.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getInt(1));
                order.setCustomerName(resultSet.getString(2));
                order.setCreatedAt(resultSet.getTimestamp(3).toLocalDateTime());
                order.setStatus(resultSet.getString(4));
                order.setTotal(resultSet.getDouble(5));
                list.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<OrderDetail> getOrderDetail(int orderId) {
        List<OrderDetail> list = new ArrayList<>();
        String sql = """
                select ct.ma_san_pham, sp.ten_san_pham, ct.so_luong, ct.gia
                from chi_tiet_don_hang ct
                join san_pham sp on ct.ma_san_pham = sp.ma_san_pham
                where ct.ma_don_hang = ?
                """;
        try (Connection connection = DatabaseConnection.getConnectDB();
             PreparedStatement pre = connection.prepareStatement(sql)) {
            pre.setInt(1, orderId);
            ResultSet resultSet = pre.executeQuery();
            while (resultSet.next()) {
                OrderDetail orderDetailrd = new OrderDetail();
                orderDetailrd.setOrderId(orderId);
                orderDetailrd.setProductId(resultSet.getInt(1));
                orderDetailrd.setProductName(resultSet.getString(2));
                orderDetailrd.setQuantity(resultSet.getInt(3));
                orderDetailrd.setPrice(resultSet.getDouble(4));
                list.add(orderDetailrd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean updateStatus(int orderId, int status) {
        String sql = "update don_hang set trang_thai = ? where ma_don_hang = ?";
        try (Connection connection = DatabaseConnection.getConnectDB();
             PreparedStatement pre = connection.prepareStatement(sql)) {
            pre.setString(1, String.valueOf(status));
            pre.setInt(2, orderId);
            return pre.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteProductFromOrder(int orderId, int productId) {
        String sql = "delete from chi_tiet_don_hang where ma_don_hang = ? and ma_san_pham = ?";
        try (Connection connection = DatabaseConnection.getConnectDB();
             PreparedStatement pre = connection.prepareStatement(sql)) {
            pre.setInt(1, orderId);
            pre.setInt(2, productId);
            return pre.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
