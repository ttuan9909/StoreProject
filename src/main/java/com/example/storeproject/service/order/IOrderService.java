package com.example.storeproject.service.order;

import com.example.storeproject.entity.order.Order;
import com.example.storeproject.entity.order.OrderDetail;

import java.util.List;

public interface IOrderService {
    List<Order> getAll();

    List<Order> search(String keyword);

    List<OrderDetail> getDetails(int orderId);

    boolean approve(int orderId);

    boolean removeProduct(int orderId, int productId);
}
