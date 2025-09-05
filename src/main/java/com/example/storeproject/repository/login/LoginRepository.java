package com.example.storeproject.repository.login;

import com.example.storeproject.database.DatabaseConnection;
import com.example.storeproject.entity.User;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class LoginRepository implements ILoginRepository {

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        if (username == null || password == null) return null;
        final String u = username.trim();
        final String p = password.trim();
        if (u.isEmpty() || p.isEmpty()) return null;

        final String sql =
                "SELECT ma_nguoi_dung, ten_dang_nhap, mat_khau, ho_ten, email, so_dien_thoai, dia_chi, " +
                        "       vai_tro, ngay_tao, last_login, is_active, ma_vi_tri " +
                        "FROM nguoi_dung " +
                        "WHERE LOWER(ten_dang_nhap) = LOWER(?) AND mat_khau = ? " +
                        "LIMIT 1";

        try (Connection conn = DatabaseConnection.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u);
            ps.setString(2, p);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToUser(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findByUsername(String username) {
        if (username == null) return null;
        final String u = username.trim();
        if (u.isEmpty()) return null;

        final String sql =
                "SELECT ma_nguoi_dung, ten_dang_nhap, mat_khau, ho_ten, email, so_dien_thoai, dia_chi, " +
                        "       vai_tro, ngay_tao, last_login, is_active, ma_vi_tri " +
                        "FROM nguoi_dung " +
                        "WHERE LOWER(ten_dang_nhap) = LOWER(?) " +
                        "LIMIT 1";

        try (Connection conn = DatabaseConnection.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToUser(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean usernameExists(String username) {
        if (username == null) return false;
        final String u = username.trim();
        if (u.isEmpty()) return false;

        final String sql = "SELECT 1 FROM nguoi_dung WHERE LOWER(ten_dang_nhap) = LOWER(?) LIMIT 1";
        try (Connection conn = DatabaseConnection.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean insertUser(User u) {
        final String sql = "INSERT INTO nguoi_dung " +
                "(ten_dang_nhap, mat_khau, email, ho_ten, so_dien_thoai, dia_chi, vai_tro, ma_vi_tri) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, safe(u.getUserName()));
            ps.setString(2, safe(u.getPassword())); // plain theo yêu cầu của bạn
            ps.setString(3, safe(u.getEmail()));
            ps.setString(4, safe(u.getFullName()));
            ps.setString(5, safe(u.getPhone()));
            ps.setString(6, safe(u.getAddress()));
            ps.setString(7, (u.getRole() == null ? "customer" : u.getRole().toLowerCase()));

            if (u.getPositionId() == null) {
                ps.setNull(8, Types.INTEGER);
            } else {
                ps.setInt(8, u.getPositionId());
            }

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateLastLogin(int userId, Timestamp ts) {
        final String sql = "UPDATE nguoi_dung SET last_login=? WHERE ma_nguoi_dung=?";
        try (Connection cn = DatabaseConnection.getConnectDB();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setTimestamp(1, ts);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /* ===================== Helpers ===================== */

    private static String safe(String s) {
        return s == null ? null : s.trim();
    }

    // Map ResultSet → User (gom lại 1 chỗ để tái sử dụng)
    private static User mapRowToUser(ResultSet rs) throws SQLException {
        User u = new User();
        u.setUserId(rs.getInt("ma_nguoi_dung"));
        u.setUserName(rs.getString("ten_dang_nhap"));
        u.setPassword(rs.getString("mat_khau"));
        u.setFullName(rs.getString("ho_ten"));
        u.setEmail(rs.getString("email"));
        u.setPhone(rs.getString("so_dien_thoai"));
        u.setAddress(rs.getString("dia_chi"));
        u.setRole(rs.getString("vai_tro"));

        // createdAt
        Timestamp createdTs = rs.getTimestamp("ngay_tao");
        if (createdTs != null) {
            LocalDate created = createdTs.toLocalDateTime().toLocalDate();
            u.setCreatedAt(created);
        }

        // last_login (nếu entity có LocalDateTime)
        Timestamp lastTs = rs.getTimestamp("last_login");
        if (lastTs != null) {
            LocalDateTime last = lastTs.toLocalDateTime();
            u.setLastLogin(last); // đảm bảo User có field phù hợp (LocalDateTime)
        }


        try {
            boolean active = rs.getBoolean("is_active");
            if (!rs.wasNull()) {
                u.setActive(active);
            }
        } catch (SQLException ignore) {
        }


        int pos = rs.getInt("ma_vi_tri");
        if (!rs.wasNull()) {
            u.setPositionId(pos);
        }
        return u;
    }
}