package com.example.storeproject.service.order;


import com.example.storeproject.entity.Cart;
import com.example.storeproject.entity.CartDetail;
import com.example.storeproject.entity.Order;
import com.example.storeproject.entity.OrderDetail;
import com.example.storeproject.repository.order.IOrderRepository;
import com.example.storeproject.repository.order.OrderRepository;

import com.example.storeproject.service.cart.ICartService;
import com.example.storeproject.service.cart.CartService;

import java.time.LocalDateTime;
import java.util.List;

public class OrderService implements IOrderService {
    private final IOrderRepository orderRepository = new OrderRepository();
    private final ICartService cartService = new CartService();
    
    @Override
    public Order createOrderFromCart(int userId, Cart cart, List<CartDetail> cartDetails) {
        if (cart == null || cartDetails == null || cartDetails.isEmpty()) {
            return null;
        }

        // Tính tổng tiền
        double totalPrice = cartDetails.stream()
                .mapToDouble(cd -> cd.getPrice() * cd.getQuantity())
                .sum();

        Order order = new Order();
        order.setUserId(userId);
        order.setOrderStatus("cho_xu_ly");
        order.setTotalPrice(totalPrice);
        order.setDiscountId(null);

        return orderRepository.createOrderFromCart(order, cartDetails);
    }
    
    @Override
    public List<Order> getOrdersByUserId(int userId) {
        return orderRepository.getOrdersByUserId(userId);
    }
    
    @Override
    public Order getOrderById(int orderId) {
        return orderRepository.getOrderById(orderId);
    }
    
    @Override
    public List<OrderDetail> getOrderDetails(int orderId) {
        return orderRepository.getOrderDetails(orderId);
    }
    
    @Override
    public boolean updateOrderStatus(int orderId, String status) {
        return orderRepository.updateOrderStatus(orderId, status);
    }
}
