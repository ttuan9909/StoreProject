package com.example.storeproject.service.order;

import com.example.storeproject.entity.Order;
import com.example.storeproject.entity.OrderDetail;
import java.util.List;

public interface IOrderService {
    Order createOrderFromCart(int userId, double totalPrice, Integer discountId);
    List<Order> getOrdersByUserId(int userId);
    Order getOrderById(int orderId);
    List<OrderDetail> getOrderDetails(int orderId);
    boolean updateOrderStatus(int orderId, String status);


}
