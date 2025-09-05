package com.example.storeproject.repository.user;

import com.example.storeproject.entity.User;
import com.example.storeproject.repository.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserRepository implements IUserRepository{
    private static final String BASE_SELECT =
            "SELECT ma_nguoi_dung, ten_dang_nhap, mat_khau, ho_ten, email, so_dien_thoai, dia_chi, vai_tro, DATE(ngay_tao) AS created_at"
                    + ", last_login, is_active, ma_vi_tri "
                    + "FROM nguoi_dung WHERE 1=1 ";
    @Override
    public List<User> findAll(int page, int size, String keyword, String role) {
        List<User> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(BASE_SELECT);

        List<Object> params = new ArrayList<>();
        if (keyword != null && !keyword.isBlank()) {
            sql.append(" AND (LOWER(ten_dang_nhap) LIKE ? OR LOWER(ho_ten) LIKE ? OR LOWER(email) LIKE ?) ");
            String like = "%" + keyword.toLowerCase().trim() + "%";
            params.add(like); params.add(like); params.add(like);
        }
        if (role != null && !role.isBlank()) {
            sql.append(" AND vai_tro = ? ");
            params.add(role);
        }
        sql.append(" ORDER BY ma_nguoi_dung DESC LIMIT ? OFFSET ? ");
        params.add(size);
        params.add((page - 1) * size);

        try (Connection con = DBConnection.getConnectDB();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User u = new User();
                    u.setUserId(rs.getInt("ma_nguoi_dung"));
                    u.setUserName(rs.getString("ten_dang_nhap"));
                    u.setPassword(rs.getString("mat_khau"));
                    u.setFullName(rs.getString("ho_ten"));
                    u.setEmail(rs.getString("email"));
                    u.setPhone(rs.getString("so_dien_thoai"));
                    u.setAddress(rs.getString("dia_chi"));
                    u.setRole(rs.getString("vai_tro"));
                    Date created = rs.getDate("created_at");
                    if (created != null) u.setCreatedAt(((java.sql.Date) created).toLocalDate());
                    Timestamp last = rs.getTimestamp("last_login");
                    if (last != null) u.setLastLogin(last.toLocalDateTime());
                    u.setActive(rs.getBoolean("is_active"));
                    u.setPositionId((Integer) rs.getObject("ma_vi_tri"));
                    list.add(u);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int countAll(String keyword, String role) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM nguoi_dung WHERE 1=1 ");
        List<Object> params = new ArrayList<>();
        if (keyword != null && !keyword.isBlank()) {
            sql.append(" AND (LOWER(ten_dang_nhap) LIKE ? OR LOWER(ho_ten) LIKE ? OR LOWER(email) LIKE ?) ");
            String like = "%" + keyword.toLowerCase().trim() + "%";
            params.add(like); params.add(like); params.add(like);
        }
        if (role != null && !role.isBlank()) {
            sql.append(" AND vai_tro = ? ");
            params.add(role);
        }

        try (Connection con = DBConnection.getConnectDB();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    @Override
    public int deleteInactiveSince(LocalDateTime cutoff) {
        String sql =
                "DELETE FROM nguoi_dung " +
                        "WHERE (" +
                        " (last_login IS NOT NULL AND last_login < ?) " +
                        " OR (last_login IS NULL AND ngay_tao < ?)" +
                        ") AND vai_tro <> 'admin'"; // tránh tự xoá admin

        try (Connection con = DBConnection.getConnectDB();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(cutoff));
            ps.setTimestamp(2, Timestamp.valueOf(cutoff));
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deleteCreatedBefore(LocalDate cutoffCreated) {
        String sql = "DELETE FROM nguoi_dung WHERE DATE(ngay_tao) < ? AND vai_tro <> 'admin'";
        try (Connection con = DBConnection.getConnectDB();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(cutoffCreated));
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int updateIsActive(int userId, boolean active) {
        String sql = "UPDATE nguoi_dung SET is_active = ? WHERE ma_nguoi_dung = ? AND vai_tro <> 'admin'";
        try (Connection con = DBConnection.getConnectDB();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setBoolean(1, active);
            ps.setInt(2, userId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deactivateInactiveSince(LocalDateTime cutoff) {
        String sql =
                "UPDATE nguoi_dung " +
                        "SET is_active = 0 " +
                        "WHERE ( (last_login IS NOT NULL AND last_login < ?) " +
                        "     OR (last_login IS NULL AND ngay_tao < ?) ) " +
                        "  AND vai_tro <> 'admin'";
        try (Connection con = DBConnection.getConnectDB();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(cutoff));
            ps.setTimestamp(2, Timestamp.valueOf(cutoff));
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public User findByUsername(String username) {
        String sql = "SELECT ma_nguoi_dung, ten_dang_nhap, mat_khau, ho_ten, email, vai_tro, ngay_tao, active " +
                "FROM nguoi_dung WHERE ten_dang_nhap = ?";

        try (Connection cn = DBConnection.getConnectDB();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = new User();
                    u.setUserId(rs.getInt("ma_nguoi_dung"));
                    u.setUserName(rs.getString("ten_dang_nhap"));
                    u.setPassword(rs.getString("mat_khau")); // hash
                    u.setFullName(rs.getString("ho_ten"));
                    u.setEmail(rs.getString("email"));
                    u.setRole(rs.getString("vai_tro"));
                    u.setCreatedAt(rs.getTimestamp("ngay_tao").toLocalDateTime().toLocalDate());
                    u.setActive(rs.getBoolean("active"));
                    return u;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
