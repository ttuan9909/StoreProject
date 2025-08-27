package com.example.storeproject.repository.order;

import com.example.storeproject.entity.CartDetail;
import com.example.storeproject.entity.Order;
import com.example.storeproject.entity.OrderDetail;
import java.util.List;

public interface IOrderRepository {
    Order createOrder(Order order);
    boolean createOrderDetail(OrderDetail orderDetail);
    List<Order> getOrdersByUserId(int userId);
    Order getOrderById(int orderId);
    List<OrderDetail> getOrderDetails(int orderId);
    boolean updateOrderStatus(int orderId, String status);
    Order createOrderFromCart(Order order, List<CartDetail> cartDetails);
}
