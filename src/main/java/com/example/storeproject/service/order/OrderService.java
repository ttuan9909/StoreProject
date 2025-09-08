package com.example.storeproject.service.order;


import com.example.storeproject.dto.OrderDTO;
import com.example.storeproject.dto.OrderDetailDTO;
import com.example.storeproject.entity.*;
import com.example.storeproject.repository.order.IOrderRepository;
import com.example.storeproject.repository.order.OrderRepository;

import com.example.storeproject.service.cart.ICartService;
import com.example.storeproject.service.cart.CartService;
import com.example.storeproject.service.product.IProductService;
import com.example.storeproject.service.product.ProductService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderService implements IOrderService {
    private final IOrderRepository orderRepository;
    private final ICartService cartService;
    private final IProductService productService = new ProductService();
    
    public OrderService() {
        this.orderRepository = new OrderRepository();
        this.cartService = new CartService();
    }
    
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

    @Override
    public List<OrderDetailDTO> getOrderDetailsByOrderId(int orderId) {
        List<OrderDetail> orderDetails = orderRepository.getOrderDetails(orderId);
        List<OrderDetailDTO> orderDetailDTOs = new ArrayList<>();

        for (OrderDetail detail : orderDetails) {
            Product product = productService.getProductById(detail.getProductId());
            String productName = product != null ? product.getProductName() : "Sản phẩm #" + detail.getProductId();
            String imageUrl = product != null ? product.getImage() : "https://via.placeholder.com/60x60?text=Product";

            OrderDetailDTO dto = new OrderDetailDTO(
                    detail.getOrderId(),
                    detail.getProductId(),
                    productName,
                    detail.getQuantity(),
                    detail.getPrice(),
                    imageUrl
            );
            orderDetailDTOs.add(dto);
            System.out.println("OrderService: getOrderDetails - orderId=" + orderId + ", productId=" + detail.getProductId() + ", imageUrl=" + imageUrl);
        }

        System.out.println("OrderService: getOrderDetails - orderId=" + orderId + ", totalItems=" + orderDetailDTOs.size());
        return orderDetailDTOs;
    }

    @Override
    public List<OrderDTO> findOrdersByStatus(String keyword, String status) {
        // Lấy danh sách theo keyword (có thể rỗng)
        List<OrderDTO> base = findOrders(keyword);

        // Nếu không truyền trạng thái thì trả luôn danh sách gốc
        if (status == null || status.trim().isEmpty()) {
            return base;
        }

        String s = status.trim().toLowerCase();

        // Lọc theo trạng thái (so sánh không phân biệt hoa thường)
        return base.stream()
                .filter(o -> {
                    String st = null;
                    try {
                        st = o.getStatus();
                    } catch (Exception ignore) {}
                    return st != null && st.toLowerCase().equals(s);
                })
                .collect(Collectors.toList());
    }

}
