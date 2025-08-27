package com.example.storeproject.service.order;


import com.example.storeproject.dto.OrderDTO;
import com.example.storeproject.dto.OrderDetailDTO;
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
    private final IOrderRepository orderRepository;
    private final ICartService cartService;

    public OrderService() {
        this.orderRepository = new OrderRepository();
        this.cartService = new CartService();
    }

    @Override
    public Order createOrderFromCart(int userId, double totalPrice, Integer discountId) {
        // Tạo đơn hàng mới
        Order order = new Order();
        order.setUserId(userId);
        order.setOrderStatus("cho_xu_ly");
        order.setTotalPrice(totalPrice);
        order.setDiscountId(discountId);
        order.setOrderDate(LocalDateTime.now());

        Order createdOrder = orderRepository.createOrder(order);
        if (createdOrder != null) {
            // Lấy các sản phẩm từ giỏ hàng
            List<CartDetail> cartItems = cartService.getCartItems(userId);

            // Tạo chi tiết đơn hàng
            for (CartDetail cartItem : cartItems) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderId(createdOrder.getOrderId());
                orderDetail.setProductId(cartItem.getProductId());
                orderDetail.setQuantity(cartItem.getQuantity());
                orderDetail.setPrice(cartItem.getPrice());

                orderRepository.createOrderDetail(orderDetail);
            }

            // Xóa giỏ hàng sau khi tạo đơn hàng thành công
            cartService.clearCart(userId);

            return createdOrder;
        }
        return null;
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

    @Override
    public List<OrderDTO> findOrders(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return orderRepository.findOrdersAll();
        }
        return orderRepository.findOrders(keyword.trim());
    }

    @Override
    public List<OrderDTO> findOrdersAll() {
        return orderRepository.findOrdersAll();
    }

    @Override
    public List<OrderDetailDTO> findOrderDetailsWithProductName(int orderId) {
        return orderRepository.findOrderDetailsWithProductName(orderId);
    }

    @Override
    public boolean deleteOrderItem(int orderId, int productId) {
        return orderRepository.deleteOrderItem(orderId, productId);
    }
}
