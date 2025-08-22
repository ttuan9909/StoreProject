package com.example.storeproject.repository.admin;

import com.example.storeproject.database.DatabaseConnection;
import com.example.storeproject.entity.admin.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminRepository implements IAdminRepository {

    @Override
    public boolean register(Admin admin) {
        String sql = "insert into nguoi_dung (ten_dang_nhap, mat_khau, vai_tro) values (?, ?, 'admin')";
        try (Connection connection = DatabaseConnection.getConnectDB();
        PreparedStatement pre = connection.prepareStatement(sql)) {
        pre.setString(1, admin.getAdmin_username());
        pre.setString(2, admin.getAdmin_password());
        return pre.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Tên đăng nhập đã tồn tại hoặc lỗi!");
            return false;
        }
    }

    @Override
    public Admin login(String username, String password) {
        String sql = "select * from nguoi_dung where ten_dang_nhap = ? and mat_khau = ? and vai_tro = 'admin'";
        try (Connection conn = DatabaseConnection.getConnectDB();
             PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setString(1, username);
            pre.setString(2, password);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                return new Admin(
                        rs.getInt("ma_nguoi_dung"),
                        rs.getString("ten_dang_nhap"),
                        rs.getString("mat_khau")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
