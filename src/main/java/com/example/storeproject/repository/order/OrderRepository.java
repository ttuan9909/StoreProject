package com.example.storeproject.repository.order;

import com.example.storeproject.database.DatabaseConnection;
import com.example.storeproject.entity.Order;
import com.example.storeproject.entity.OrderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository implements IOrderRepository {

    @Override
    public List<Order> findAll() {
        List<Order> list = new ArrayList<>();
        String sql =
                "select dh.ma_don_hang, dh.ma_nguoi_dung, dh.ngay_dat, dh.trang_thai, dh.tong_tien " +
                        "from don_hang dh " +
                        "join nguoi_dung nd on dh.ma_nguoi_dung = nd.ma_nguoi_dung";
        try (Connection connection = DatabaseConnection.getConnectDB();
             PreparedStatement pre = connection.prepareStatement(sql);
             ResultSet rs = pre.executeQuery()) {

            while (rs.next()) {
                Order o = new Order();
                o.setOrderId(rs.getInt(1));
                o.setUserId(rs.getInt(2)); // entity dùng Integer, autobox OK
                o.setOrderDate(rs.getTimestamp(3).toLocalDateTime());
                o.setOrderStatus(rs.getString(4));
                o.setTotalPrice(rs.getDouble(5));
                list.add(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Order> search(String keyword) {
        List<Order> list = new ArrayList<>();
        String sql = "select dh.ma_don_hang, dh.ma_nguoi_dung, dh.ngay_dat, dh.trang_thai, dh.tong_tien" +
        " from don_hang dh" + "join nguoi_dung nd on dh.ma_nguoi_dung = nd.ma_nguoi_dung" + "where cast(dh.ma_don_hang as char) like ? or nd.ho_ten like ?";
        try (Connection connection = DatabaseConnection.getConnectDB();
             PreparedStatement pre = connection.prepareStatement(sql)) {

            pre.setString(1, "%" + keyword + "%");
            pre.setString(2, "%" + keyword + "%");

            try (ResultSet rs = pre.executeQuery()) {
                while (rs.next()) {
                    Order o = new Order();
                    o.setOrderId(rs.getInt(1));
                    o.setUserId(rs.getInt(2));
                    o.setOrderDate(rs.getTimestamp(3).toLocalDateTime());
                    o.setOrderStatus(rs.getString(4));
                    o.setTotalPrice(rs.getDouble(5));
                    list.add(o);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<OrderDetail> getOrderDetail(int orderId) {
        List<OrderDetail> list = new ArrayList<>();
        String sql = " select ct.ma_san_pham, sp.ten_san_pham, ct.so_luong, ct.gia"+
                " from chi_tiet_don_hang ct"+
                "join san_pham sp on ct.ma_san_pham = sp.ma_san_pham"+
                "where ct.ma_don_hang = ?";
        try (Connection connection = DatabaseConnection.getConnectDB();
             PreparedStatement pre = connection.prepareStatement(sql)) {

            pre.setInt(1, orderId);
            try (ResultSet rs = pre.executeQuery()) {
                while (rs.next()) {
                    OrderDetail od = new OrderDetail();
                    od.setOrderId(orderId);
                    od.setProductId(rs.getInt(1));
                    od.setQuantity(rs.getInt(3));
                    od.setPrice(rs.getDouble(4));
                    list.add(od);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean updateStatus(int orderId, String status) {
        String sql = "update don_hang set trang_thai = ? where ma_don_hang = ?";
        try (Connection connection = DatabaseConnection.getConnectDB();
             PreparedStatement pre = connection.prepareStatement(sql)) {

            pre.setString(1, status);
            pre.setInt(2, orderId);
            return pre.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
            return false;
        }
    }
}