package com.example.storeproject.repository.product;

import com.example.storeproject.dto.ProductDto;
import com.example.storeproject.entity.Product;
import com.example.storeproject.repository.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductRepository implements IProductRepository {

    // READ (JOIN để có categoryName)
    private static final String SELECT_ALL_VIEW =
            "SELECT " +
                    "  sp.ma_san_pham   AS product_id, " +
                    "  sp.ten_san_pham  AS product_name, " +
                    "  sp.gia           AS price, " +
                    "  sp.mo_ta         AS description, " +
                    "  sp.so_luong      AS quantity, " +
                    "  sp.hinh_anh      AS image, " +
                    "  dm.ten_danh_muc  AS category_name, " +   // <— nếu cột của bạn tên khác, đổi alias tại đây
                    " sp.ma_danh_muc   AS category_id,  " +
                    "  sp.ngay_tao      AS date_created " +
                    "FROM san_pham sp " +
                    "LEFT JOIN danh_muc dm ON sp.ma_danh_muc = dm.ma_danh_muc " +
                    "ORDER BY sp.ma_san_pham;";

    private static final String SELECT_VIEW_BY_ID =
            "SELECT " +
                    "  sp.ma_san_pham   AS product_id, " +
                    "  sp.ten_san_pham  AS product_name, " +
                    "  sp.gia           AS price, " +
                    "  sp.mo_ta         AS description, " +
                    "  sp.so_luong      AS quantity, " +
                    "  sp.hinh_anh      AS image, " +
                    "  dm.ten_danh_muc  AS category_name, " +
                    " sp.ma_danh_muc   AS category_id,  " +
                    "  sp.ngay_tao      AS date_created " +
                    "FROM san_pham sp " +
                    "LEFT JOIN danh_muc dm ON sp.ma_danh_muc = dm.ma_danh_muc " +
                    "WHERE sp.ma_san_pham = ?;";

    private static final String SEARCH_VIEW_BY_NAME =
            "SELECT " +
                    "  sp.ma_san_pham   AS product_id, " +
                    "  sp.ten_san_pham  AS product_name, " +
                    "  sp.gia           AS price, " +
                    "  sp.mo_ta         AS description, " +
                    "  sp.so_luong      AS quantity, " +
                    "  sp.hinh_anh      AS image, " +
                    "  dm.ten_danh_muc  AS category_name, " +
                    " sp.ma_danh_muc   AS category_id,  " +
                    "  sp.ngay_tao      AS date_created " +
                    "FROM san_pham sp " +
                    "LEFT JOIN danh_muc dm ON sp.ma_danh_muc = dm.ma_danh_muc " +
                    "WHERE LOWER(sp.ten_san_pham) LIKE ? " +
                    "ORDER BY sp.ma_san_pham;";

    // ===== WRITE (ENTITY) =====
    private static final String INSERT_INTO =
            "INSERT INTO san_pham(ten_san_pham, gia, mo_ta, so_luong, hinh_anh, ma_danh_muc) " +
                    "VALUES (?, ?, ?, ?, ?, ?);"; // ngay_tao có default CURRENT_TIMESTAMP

    private static final String UPDATE_BY_ID =
            "UPDATE san_pham " +
                    "SET ten_san_pham = ?, gia = ?, mo_ta = ?, so_luong = ?, hinh_anh = ?, ma_danh_muc = ? " +
                    "WHERE ma_san_pham = ?;";

    private static final String DELETE_BY_ID =
            "DELETE FROM san_pham WHERE ma_san_pham = ?;";

    @Override
    public List<ProductDto> findAll() {
        List<ProductDto> list = new ArrayList<>();
        try (Connection cn = DBConnection.getConnectDB();
             PreparedStatement ps = cn.prepareStatement(SELECT_ALL_VIEW);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ProductDto dto = new ProductDto();
                dto.setProductId(rs.getInt("product_id"));
                dto.setProductName(rs.getString("product_name"));
                dto.setPrice(rs.getDouble("price"));
                dto.setDescription(rs.getString("description"));
                dto.setQuantity(rs.getInt("quantity"));
                dto.setImage(rs.getString("image"));
                dto.setCategoryName(rs.getString("category_name"));
                Integer catId = (Integer) rs.getObject("category_id"); // null-safe
                dto.setCategoryId(catId);

                java.sql.Timestamp ts = rs.getTimestamp("date_created");
                dto.setDateCreated(ts != null ? ts.toLocalDateTime().toLocalDate() : null);

                list.add(dto);
            }
        } catch (Exception e) {
            System.out.println("Lỗi query findAll (DTO view)");
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ProductDto findById(int id) {
        try (Connection cn = DBConnection.getConnectDB();
             PreparedStatement ps = cn.prepareStatement(SELECT_VIEW_BY_ID)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ProductDto dto = new ProductDto();
                    dto.setProductId(rs.getInt("product_id"));
                    dto.setProductName(rs.getString("product_name"));
                    dto.setPrice(rs.getDouble("price"));
                    dto.setDescription(rs.getString("description"));
                    dto.setQuantity(rs.getInt("quantity"));
                    dto.setImage(rs.getString("image"));
                    dto.setCategoryName(rs.getString("category_name"));
                    Integer catId = (Integer) rs.getObject("category_id");
                    dto.setCategoryId(catId);

                    java.sql.Timestamp ts = rs.getTimestamp("date_created");
                    dto.setDateCreated(ts != null ? ts.toLocalDateTime().toLocalDate() : null);
                    return dto;
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi query findById (DTO view)");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ProductDto> searchByName(String keyword) {
        String k = (keyword == null) ? "" : keyword.trim().toLowerCase();
        if (k.isEmpty()) return findAll();

        List<ProductDto> list = new ArrayList<>();
        try (Connection cn = DBConnection.getConnectDB();
             PreparedStatement ps = cn.prepareStatement(SEARCH_VIEW_BY_NAME)) {

            ps.setString(1, "%" + k + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProductDto dto = new ProductDto();
                    dto.setProductId(rs.getInt("product_id"));
                    dto.setProductName(rs.getString("product_name"));
                    dto.setPrice(rs.getDouble("price"));
                    dto.setDescription(rs.getString("description"));
                    dto.setQuantity(rs.getInt("quantity"));
                    dto.setImage(rs.getString("image"));
                    dto.setCategoryName(rs.getString("category_name"));
                    Integer catId = (Integer) rs.getObject("category_id");
                    dto.setCategoryId(catId);

                    java.sql.Timestamp ts = rs.getTimestamp("date_created");
                    dto.setDateCreated(ts != null ? ts.toLocalDateTime().toLocalDate() : null);

                    list.add(dto);
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi query searchByName (DTO view)");
            e.printStackTrace();
        }
        return list.isEmpty() ? Collections.emptyList() : list;
    }

    @Override
    public boolean save(Product product) {
        try (Connection cn = DBConnection.getConnectDB();
             PreparedStatement ps = cn.prepareStatement(INSERT_INTO)) {

            ps.setString(1, product.getProductName());
            ps.setDouble(2, product.getPrice());
            ps.setString(3, product.getDescription());
            ps.setInt(4, product.getQuantity());
            ps.setString(5, product.getImage());
            ps.setInt(6, product.getCategoryId());

            int effectRow = ps.executeUpdate();
            return effectRow == 1;

        } catch (Exception e) {
            System.out.println("Lỗi query create (product)");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Product product) {
        try (Connection cn = DBConnection.getConnectDB();
             PreparedStatement ps = cn.prepareStatement(UPDATE_BY_ID)) {

            ps.setString(1, product.getProductName());
            ps.setDouble(2, product.getPrice());
            ps.setString(3, product.getDescription());
            ps.setInt(4, product.getQuantity());
            ps.setString(5, product.getImage());
            ps.setInt(6, product.getCategoryId());
            ps.setInt(7, product.getProductId());
            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            System.out.println("Lỗi query update (product)");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        try (Connection cn = DBConnection.getConnectDB();
             PreparedStatement ps = cn.prepareStatement(DELETE_BY_ID)) {

            ps.setInt(1, id);
            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            System.out.println("Lỗi query deleteById (product)");
            e.printStackTrace();
        }
        return false;
    }
}
