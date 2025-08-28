package com.example.storeproject.repository.order;

import com.example.storeproject.dto.OrderDTO;
import com.example.storeproject.dto.OrderDetailDTO;
import com.example.storeproject.entity.Order;
import com.example.storeproject.entity.OrderDetail;
import java.util.List;

public interface IOrderRepository {
    Order createOrder(Order order);
    boolean createOrderDetail(OrderDetail orderDetail);
    List<OrderDTO> findOrdersByStatus(String keyword, String status);
    List<Order> getOrdersByUserId(int userId);
    Order getOrderById(int orderId);
    List<OrderDetail> getOrderDetails(int orderId);
    boolean updateOrderStatus(int orderId, String status);
    List<OrderDTO> findOrders(String keyword);
    List<OrderDTO> findOrdersAll();
    List<OrderDetailDTO> findOrderDetailsWithProductName(int orderId);
    boolean deleteOrderItem(int orderId, int productId);
}
