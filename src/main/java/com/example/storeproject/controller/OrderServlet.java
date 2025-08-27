package com.example.storeproject.controller;

import com.example.storeproject.entity.Cart;
import com.example.storeproject.entity.CartDetail;
import com.example.storeproject.entity.Order;
import com.example.storeproject.entity.OrderDetail;
import com.example.storeproject.service.order.IOrderService;
import com.example.storeproject.service.order.OrderService;
import com.example.storeproject.service.cart.ICartService;
import com.example.storeproject.service.cart.CartService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "OrderServlet", urlPatterns = {"/order", "/order/create", "/order/*"})
public class OrderServlet extends HttpServlet {
    private static final IOrderService orderService = new OrderService();
    private static final ICartService cartService = new CartService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        System.out.println("OrderServlet doGet: Session ID: " + session.getId());
        System.out.println("OrderServlet doGet: userId from session: " + userId);

        if (userId == null) {
            // Lưu URL gốc (bao gồm query string nếu có)
            String currentUrl = request.getRequestURI();
            if (request.getQueryString() != null) {
                currentUrl += "?" + request.getQueryString();
            }
            System.out.println("OrderServlet doGet: No userId, saving redirect: " + currentUrl);
            session.setAttribute("redirectAfterLogin", currentUrl);

            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || "/".equals(pathInfo)) {
            showOrderHistory(request, response, userId);
        } else if (pathInfo.startsWith("/detail/")) {
            showOrderDetail(request, response, userId);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        System.out.println("OrderServlet doPost: Session ID: " + session.getId());
        System.out.println("OrderServlet doPost: userId from session: " + userId);

        if (userId == null) {
            // Lưu URL gốc (bao gồm query string nếu có)
            String currentUrl = request.getRequestURI();
            if (request.getQueryString() != null) {
                currentUrl += "?" + request.getQueryString();
            }
            System.out.println("OrderServlet doPost: No userId, saving redirect: " + currentUrl);
            session.setAttribute("redirectAfterLogin", currentUrl);

            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String action = request.getParameter("action");
        
        if ("create".equals(action)) {
            createOrder(request, response, userId);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    
    private void showOrderHistory(HttpServletRequest request, HttpServletResponse response, int userId) 
            throws ServletException, IOException {
        
        List<Order> orders = orderService.getOrdersByUserId(userId);
        request.setAttribute("orders", orders);
        
        request.getRequestDispatcher("/WEB-INF/page/order/order-history.jsp").forward(request, response);
    }
    
    private void showOrderDetail(HttpServletRequest request, HttpServletResponse response, int userId) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        String orderIdStr = pathInfo.substring("/detail/".length());
        
        try {
            int orderId = Integer.parseInt(orderIdStr);
            Order order = orderService.getOrderById(orderId);
            
            if (order != null && order.getUserId() == userId) {
                List<OrderDetail> orderDetails = orderService.getOrderDetails(orderId);
                
                request.setAttribute("order", order);
                request.setAttribute("orderDetails", orderDetails);
                
                request.getRequestDispatcher("/WEB-INF/page/order/order-detail.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void createOrder(HttpServletRequest request, HttpServletResponse response, int userId)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            Cart cart = cartService.getCartByUserId(userId);
            List<CartDetail> cartDetails = cartService.getCartDetails(userId);
            System.out.println("OrderServlet: cart = " + (cart != null ? cart.getCartId() : "null"));
            System.out.println("OrderServlet: cartDetails = " + (cartDetails != null ? cartDetails.size() : "null"));
            if (cart != null && cartDetails != null && !cartDetails.isEmpty()) {
                Order order = orderService.createOrderFromCart(userId, cart, cartDetails);
                if (order != null) {
                    boolean cleared = cartService.clearCart(userId);
                    if (!cleared) {
                        System.out.println("OrderServlet: Failed to clear cart for userId=" + userId);
                        response.setStatus(HttpServletResponse.SC_OK);
                        response.getWriter().write(
                                "{\"success\": true, \"message\": \"Đặt hàng thành công nhưng không thể xóa giỏ hàng\", \"orderId\": " + order.getOrderId() + "}"
                        );
                    } else {
                        response.setStatus(HttpServletResponse.SC_OK);
                        response.getWriter().write(
                                "{\"success\": true, \"message\": \"Đặt hàng thành công\", \"orderId\": " + order.getOrderId() + "}"
                        );
                    }
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"success\": false, \"message\": \"Không thể tạo đơn hàng: Lỗi dữ liệu hoặc cơ sở dữ liệu.\"}");
                }
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"success\": false, \"message\": \"Giỏ hàng trống.\"}");
            }
        } catch (Exception e) {
            System.out.println("OrderServlet: Exception - " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"success\": false, \"message\": \"Có lỗi xảy ra khi đặt hàng: " + e.getMessage() + "\"}");
        }
    }
}
