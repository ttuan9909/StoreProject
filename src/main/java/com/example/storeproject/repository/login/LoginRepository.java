package com.example.storeproject.repository.login;

import com.example.storeproject.database.DatabaseConnection;
import com.example.storeproject.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class LoginRepository implements ILoginRepository {

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        String sql = "select ma_nguoi_dung, ten_dang_nhap, mat_khau, ho_ten, email, so_dien_thoai, dia_chi, vai_tro, ngay_tao, ma_vi_tri " +
                "from nguoi_dung " +
                "where ten_dang_nhap = ? and mat_khau = ?";

        try (Connection conn = DatabaseConnection.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = new User();
                    // map đúng với User của bạn
                    u.setUserId(rs.getInt("ma_nguoi_dung"));
                    u.setUserName(rs.getString("ten_dang_nhap"));
                    u.setPassword(rs.getString("mat_khau"));
                    u.setFullName(rs.getString("ho_ten"));
                    u.setEmail(rs.getString("email"));
                    u.setPhone(rs.getString("so_dien_thoai"));
                    u.setAddress(rs.getString("dia_chi"));
                    u.setRole(rs.getString("vai_tro"));

                    LocalDate created = null;
                    java.sql.Timestamp ts = rs.getTimestamp("ngay_tao");
                    if (ts != null) {
                        created = ts.toLocalDateTime().toLocalDate();
                    }
                    u.setCreatedAt(created);

                    // ma_vi_tri → positionId
                    int pos = rs.getInt("ma_vi_tri");
                    if (!rs.wasNull()) {
                        u.setPositionId(pos);
                    }

                    return u;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insertUser(User u) {
        String sql = "INSERT INTO nguoi_dung " +
                "(ten_dang_nhap, mat_khau, email, ho_ten, so_dien_thoai, dia_chi, vai_tro, ma_vi_tri) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getUserName());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getFullName());
            ps.setString(5, u.getPhone());
            ps.setString(6, u.getAddress());
            ps.setString(7, u.getRole() == null ? "customer" : u.getRole().toLowerCase());

            if (u.getPositionId() == null) {
                ps.setNull(8, java.sql.Types.INTEGER);
            } else {
                ps.setInt(8, u.getPositionId());
            }

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}