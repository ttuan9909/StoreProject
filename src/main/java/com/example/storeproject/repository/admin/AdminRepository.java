package com.example.storeproject.repository.admin;

import com.example.storeproject.database.DatabaseConnection;
import com.example.storeproject.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminRepository implements IAdminRepository {

    @Override
    public boolean register(User user) {
        String sql = "insert into nguoi_dung (ten_dang_nhap, mat_khau, vai_tro) values (?, ?, 'admin')";
        try (Connection connection = DatabaseConnection.getConnectDB();
             PreparedStatement pre = connection.prepareStatement(sql)) {
            pre.setString(1, user.getUserName());
            pre.setString(2, user.getPassword());
            return pre.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Tên đăng nhập đã tồn tại hoặc lỗi!");
            return false;
        }
    }

    @Override
    public User login(String username, String password) {
        String sql = "select * from nguoi_dung where ten_dang_nhap = ? and mat_khau = ? and vai_tro = 'admin'";
        try (Connection conn = DatabaseConnection.getConnectDB();
             PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setString(1, username);
            pre.setString(2, password);
            try (ResultSet rs = pre.executeQuery()) {
                if (rs.next()) {
                    User u = new User();
                    u.setUserId(rs.getInt("ma_nguoi_dung"));
                    u.setUserName(rs.getString("ten_dang_nhap"));
                    u.setPassword(rs.getString("mat_khau"));
                    u.setRole(rs.getString("vai_tro"));
                    return u;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
