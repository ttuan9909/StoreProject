package com.example.storeproject.repository.order;

import com.example.storeproject.database.DatabaseConnection;
import com.example.storeproject.dto.OrderDTO;
import com.example.storeproject.dto.OrderDetailDTO;
import com.example.storeproject.entity.Order;
import com.example.storeproject.entity.OrderDetail;
import com.example.storeproject.repository.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository implements IOrderRepository {


    @Override
    public Order createOrder(Order order) {
        String sql = "INSERT INTO don_hang (ma_nguoi_dung, trang_thai, tong_tien, ma_khuyen_mai) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, order.getUserId());
            ps.setString(2, order.getOrderStatus());
            ps.setDouble(3, order.getTotalPrice());

            if (order.getDiscountId() != null) {
                ps.setInt(4, order.getDiscountId());
            } else {
                ps.setNull(4, Types.INTEGER);
            }

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int orderId = rs.getInt(1);
                        order.setOrderId(orderId);
                        return order;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean createOrderDetail(OrderDetail orderDetail) {
        String sql = "INSERT INTO chi_tiet_don_hang (ma_don_hang, ma_san_pham, so_luong, gia) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderDetail.getOrderId());
            ps.setInt(2, orderDetail.getProductId());
            ps.setInt(3, orderDetail.getQuantity());
            ps.setDouble(4, orderDetail.getPrice());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Order> getOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM don_hang WHERE ma_nguoi_dung = ? ORDER BY ngay_dat DESC";

        try (Connection conn = DBConnection.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order order = mapResultSetToOrder(rs);
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public Order getOrderById(int orderId) {
        String sql = "SELECT * FROM don_hang WHERE ma_don_hang = ?";

        try (Connection conn = DBConnection.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToOrder(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<OrderDetail> getOrderDetails(int orderId) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        String sql = "SELECT * FROM chi_tiet_don_hang WHERE ma_don_hang = ?";

        try (Connection conn = DBConnection.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderDetail orderDetail = mapResultSetToOrderDetail(rs);
                    orderDetails.add(orderDetail);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderDetails;
    }

    @Override
    public boolean updateOrderStatus(int orderId, String status) {
        String sql = "UPDATE don_hang SET trang_thai = ? WHERE ma_don_hang = ?";

        try (Connection conn = DBConnection.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, orderId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Order mapResultSetToOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setOrderId(rs.getInt("ma_don_hang"));
        order.setUserId(rs.getInt("ma_nguoi_dung"));
        order.setOrderStatus(rs.getString("trang_thai"));
        order.setTotalPrice(rs.getDouble("tong_tien"));

        int discountId = rs.getInt("ma_khuyen_mai");
        if (!rs.wasNull()) {
            order.setDiscountId(discountId);
        }

        Timestamp orderDate = rs.getTimestamp("ngay_dat");
        if (orderDate != null) {
            order.setOrderDate(orderDate.toLocalDateTime());
        }

        return order;
    }

    private OrderDetail mapResultSetToOrderDetail(ResultSet rs) throws SQLException {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(rs.getInt("ma_don_hang"));
        orderDetail.setProductId(rs.getInt("ma_san_pham"));
        orderDetail.setQuantity(rs.getInt("so_luong"));
        orderDetail.setPrice(rs.getDouble("gia"));
        return orderDetail;
    }

    @Override
    public List<OrderDTO> findOrders(String keyword) {
        List<OrderDTO> orderDTOList = new ArrayList<>();
        String sql = "SELECT dh.ma_don_hang, dh.ma_nguoi_dung, nd.ho_ten, dh.ngay_dat, dh.trang_thai, dh.tong_tien " +
                "FROM don_hang dh JOIN nguoi_dung nd ON dh.ma_nguoi_dung = nd.ma_nguoi_dung " +
                "WHERE nd.ho_ten LIKE ? OR dh.ma_don_hang = ? " +
                "ORDER BY dh.ngay_dat DESC";
        Integer exactId = null;
        try {
            if (keyword != null && !keyword.trim().isEmpty()) {
                exactId = Integer.parseInt(keyword.trim());
            }
        } catch (Exception ignored) {
        }
        try (Connection connection = DatabaseConnection.getConnectDB();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            String pattern = "%" + (keyword == null ? "" : keyword.trim()) + "%";
            preparedStatement.setString(1, pattern);
            if (exactId == null) {
                preparedStatement.setInt(2, -1);
            } else {
                preparedStatement.setInt(2, exactId);
            }
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    OrderDTO orderDTO = new OrderDTO(
                            resultSet.getInt("ma_don_hang"),
                            resultSet.getInt("ma_nguoi_dung"),
                            resultSet.getString("ho_ten"),
                            resultSet.getTimestamp("ngay_dat") == null ? null : resultSet.getTimestamp("ngay_dat").toLocalDateTime(),
                            resultSet.getString("trang_thai"),
                            resultSet.getDouble("tong_tien")
                    );
                    orderDTOList.add(orderDTO);
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return orderDTOList;
    }

    @Override
    public List<OrderDTO> findOrdersAll() {
        List<OrderDTO> orderDTOList = new ArrayList<>();
        String sql = "SELECT dh.ma_don_hang, dh.ma_nguoi_dung, nd.ho_ten, dh.ngay_dat, dh.trang_thai, dh.tong_tien " +
                "FROM don_hang dh JOIN nguoi_dung nd ON dh.ma_nguoi_dung = nd.ma_nguoi_dung " +
                "ORDER BY dh.ngay_dat DESC";
        try (Connection connection = DatabaseConnection.getConnectDB();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                OrderDTO orderDTO = new OrderDTO(
                        resultSet.getInt("ma_don_hang"),
                        resultSet.getInt("ma_nguoi_dung"),
                        resultSet.getString("ho_ten"),
                        resultSet.getTimestamp("ngay_dat") == null ? null : resultSet.getTimestamp("ngay_dat").toLocalDateTime(),
                        resultSet.getString("trang_thai"),
                        resultSet.getDouble("tong_tien")
                );
                orderDTOList.add(orderDTO);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return orderDTOList;
    }

    @Override
    public List<OrderDetailDTO> findOrderDetailsWithProductName(int orderId) {
        List<OrderDetailDTO> orderDetailDTOList = new ArrayList<>();
        String sql = "SELECT ctdh.ma_don_hang, ctdh.ma_san_pham, sp.ten_san_pham, ctdh.so_luong, ctdh.gia " +
                "FROM chi_tiet_don_hang ctdh JOIN san_pham sp ON ctdh.ma_san_pham = sp.ma_san_pham " +
                "WHERE ctdh.ma_don_hang = ?";
        try (Connection connection = DatabaseConnection.getConnectDB();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, orderId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    OrderDetailDTO orderDetailDTO = new OrderDetailDTO(
                            resultSet.getInt("ma_don_hang"),
                            resultSet.getInt("ma_san_pham"),
                            resultSet.getString("ten_san_pham"),
                            resultSet.getInt("so_luong"),
                            resultSet.getDouble("gia")
                    );
                    orderDetailDTOList.add(orderDetailDTO);
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return orderDetailDTOList;
    }

    @Override
    public boolean deleteOrderItem(int orderId, int productId) {
        String sql = "DELETE FROM chi_tiet_don_hang WHERE ma_don_hang = ? AND ma_san_pham = ?";
        try (Connection connection = DatabaseConnection.getConnectDB();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, productId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    private Order mapToOrder(ResultSet resultSet) throws SQLException {
        Order order = new Order();
        order.setOrderId(resultSet.getInt("ma_don_hang"));
        order.setUserId(resultSet.getInt("ma_nguoi_dung"));
        order.setOrderStatus(resultSet.getString("trang_thai"));
        order.setTotalPrice(resultSet.getDouble("tong_tien"));
        Timestamp timestamp = resultSet.getTimestamp("ngay_dat");
        if (timestamp != null) {
            order.setOrderDate(timestamp.toLocalDateTime());
        }
        int discountId = resultSet.getInt("ma_khuyen_mai");
        if (!resultSet.wasNull()) {
            order.setDiscountId(discountId);
        }
        return order;
    }

    private OrderDetail mapToOrderDetail(ResultSet resultSet) throws SQLException {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(resultSet.getInt("ma_don_hang"));
        orderDetail.setProductId(resultSet.getInt("ma_san_pham"));
        orderDetail.setQuantity(resultSet.getInt("so_luong"));
        orderDetail.setPrice(resultSet.getDouble("gia"));
        return orderDetail;
    }

    @Override
    public List<OrderDTO> findOrdersByStatus(String keyword, String status) {
        List<OrderDTO> list = new ArrayList<>();

        String sql = "SELECT dh.ma_don_hang, dh.ma_nguoi_dung, nd.ho_ten, dh.ngay_dat, dh.trang_thai, dh.tong_tien " +
                "FROM don_hang dh JOIN nguoi_dung nd ON dh.ma_nguoi_dung = nd.ma_nguoi_dung " +
                "WHERE dh.trang_thai = ? AND (nd.ho_ten LIKE ? OR dh.ma_don_hang = ?) " +
                "ORDER BY dh.ngay_dat DESC";

        Integer exactId = null;
        try {
            if (keyword != null && !keyword.trim().isEmpty()) {
                exactId = Integer.parseInt(keyword.trim());
            }
        } catch (Exception ignored) {}

        try (Connection connection = DatabaseConnection.getConnectDB();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, status);
            String pattern = "%" + (keyword == null ? "" : keyword.trim()) + "%";
            ps.setString(2, pattern);
            ps.setInt(3, exactId == null ? -1 : exactId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderDTO dto = new OrderDTO(
                            rs.getInt("ma_don_hang"),
                            rs.getInt("ma_nguoi_dung"),
                            rs.getString("ho_ten"),
                            rs.getTimestamp("ngay_dat") == null ? null : rs.getTimestamp("ngay_dat").toLocalDateTime(),
                            rs.getString("trang_thai"),
                            rs.getDouble("tong_tien")
                    );
                    list.add(dto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}

