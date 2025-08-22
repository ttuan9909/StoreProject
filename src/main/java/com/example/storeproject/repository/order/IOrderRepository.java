package com.example.storeproject.repository.order;

import com.example.storeproject.entity.Order;
import com.example.storeproject.entity.OrderDetail;

import java.util.List;

public interface IOrderRepository {
    List<Order> findAll();

    List<Order> search(String keyword);

    List<OrderDetail> getOrderDetail(int orderId);

    boolean updateStatus(int orderId, int status);

    boolean deleteProductFromOrder(int orderId, int productId);
}
