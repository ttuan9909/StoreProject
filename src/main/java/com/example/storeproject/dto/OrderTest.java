package com.example.storeproject.dto;

import com.example.storeproject.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderTest {
    public static void main(String[] args) {
        List<OrderDTO> orders = new ArrayList<>();
        String sql = "select dh.ma_don_hang, dh.ma_nguoi_dung, nd.ho_ten, dh.ngay_dat, dh.trang_thai, dh.tong_tien " +
                "from don_hang dh " +
                "join nguoi_dung nd on dh.ma_nguoi_dung = nd.ma_nguoi_dung";

        try (Connection conn = DatabaseConnection.getConnectDB();
             PreparedStatement pre = conn.prepareStatement(sql);
             ResultSet rs = pre.executeQuery()) {

            while (rs.next()) {
                orders.add(new OrderDTO(
                        rs.getInt("ma_don_hang"),
                        rs.getInt("ma_nguoi_dung"),
                        rs.getString("ho_ten"),
                        rs.getTimestamp("ngay_dat").toLocalDateTime(),
                        rs.getString("trang_thai"),
                        rs.getDouble("tong_tien")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        orders.forEach(System.out::println);
    }
}