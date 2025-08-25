package com.example.storeproject.repository.category;

import com.example.storeproject.entity.Category;
import com.example.storeproject.repository.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CategoryRepository implements ICategoryRepository {
    private static final String SELECT_ALL     = "SELECT ma_danh_muc, ten_danh_muc FROM danh_muc;";
    private static final String SELECT_BY_ID   = "SELECT ma_danh_muc, ten_danh_muc FROM danh_muc WHERE ma_danh_muc = ?;";
    private static final String INSERT_INTO    = "INSERT INTO danh_muc(ten_danh_muc) VALUES (?);";
    private static final String UPDATE_BY_ID   = "UPDATE danh_muc SET ten_danh_muc = ? WHERE ma_danh_muc = ?;";
    private static final String DELETE_BY_ID   = "DELETE FROM danh_muc WHERE ma_danh_muc = ?;";
    private static final String SEARCH_BY_NAME = "SELECT ma_danh_muc, ten_danh_muc FROM danh_muc WHERE ten_danh_muc LIKE ?;";


    @Override
    public List<Category> findAll() {
        List<Category> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnectDB();
             PreparedStatement ps = con.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Category(rs.getInt("ma_danh_muc"), rs.getString("ten_danh_muc")));
            }
        } catch (Exception e) {
            System.out.println("Lỗi query Category.findAll");
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Category findById(int id) {
        try (Connection con = DBConnection.getConnectDB();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return new Category(rs.getInt("ma_danh_muc"), rs.getString("ten_danh_muc"));
            }
        } catch (Exception e) {
            System.out.println("Lỗi query Category.findById");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean save(Category c) {
        try (Connection con = DBConnection.getConnectDB();
             PreparedStatement ps = con.prepareStatement(INSERT_INTO)) {
            ps.setString(1, c.getCategoryName());
            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            System.out.println("Lỗi query Category.save");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void update(Category c) {
        try (Connection con = DBConnection.getConnectDB();
             PreparedStatement ps = con.prepareStatement(UPDATE_BY_ID)) {
            ps.setString(1, c.getCategoryName());
            ps.setInt(2, c.getCategoryId());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi query Category.update");
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteById(int id) {
        try (Connection con = DBConnection.getConnectDB();
             PreparedStatement ps = con.prepareStatement(DELETE_BY_ID)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            System.out.println("Lỗi query Category.deleteById");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Category> searchByName(String keyword) {
        String k = (keyword == null) ? "" : keyword.trim().toLowerCase();
        if (k.isEmpty()) return findAll();

        List<Category> results = new ArrayList<>();
        try (Connection con = DBConnection.getConnectDB();
             PreparedStatement ps = con.prepareStatement(SEARCH_BY_NAME)) {
            ps.setString(1, "%" + k + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    results.add(new Category(rs.getInt("ma_danh_muc"), rs.getString("ten_danh_muc")));
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi query Category.searchByName");
            e.printStackTrace();
        }
        return results.isEmpty() ? Collections.emptyList() : results;
    }
}
