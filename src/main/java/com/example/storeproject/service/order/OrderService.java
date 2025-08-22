package com.example.storeproject.service.order;

import com.example.storeproject.entity.Order;
import com.example.storeproject.entity.OrderDetail;
import com.example.storeproject.repository.order.IOrderRepository;
import com.example.storeproject.repository.order.OrderRepository;

import java.util.List;

public class OrderService implements IOrderService {
    private final IOrderRepository orderRepository = new OrderRepository();

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> search(String keyword) {
        return orderRepository.search(keyword);
    }

    @Override
    public List<OrderDetail> getDetails(int orderId) {
        return orderRepository.getOrderDetail(orderId); // sửa lỗi đệ quy
    }

    @Override
    public boolean approve(int orderId) {
        return orderRepository.updateStatus(orderId, 1); // trạng thái duyệt = 1 (int)
    }

    @Override
    public boolean removeProduct(int orderId, int productId) {
        return orderRepository.deleteProductFromOrder(orderId, productId);
    }
}
