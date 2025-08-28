package com.example.storeproject.controller;

import com.example.storeproject.dto.OrderDTO;
import com.example.storeproject.dto.OrderDetailDTO;
import com.example.storeproject.entity.Order;
import com.example.storeproject.service.order.IOrderService;
import com.example.storeproject.service.order.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminServlet", urlPatterns = {
    "/admin", 
    "/admin/home", 
    "/admin/order", 
    "/admin/order/*",
    "/admin/products",
    "/admin/user",
    "/admin/discount",
    "/admin/payment",
    "/admin/report",
    "/admin/setting"
})
public class AdminServlet extends HttpServlet {
    private static final IOrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Check admin authentication
        HttpSession session = request.getSession(false);
        String role = session != null ? (String) session.getAttribute("role") : null;
        if (role == null || !role.equalsIgnoreCase("admin")) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = requestURI.substring(contextPath.length());

        // Route to appropriate handler
        if (path.equals("/admin") || path.equals("/admin/home")) {
            showAdminHome(request, response);
        } else if (path.equals("/admin/order")) {
            showOrderList(request, response);
        } else if (path.startsWith("/admin/order/detail/")) {
            showOrderDetail(request, response);
        } else if (path.equals("/admin/products")) {
            showProducts(request, response);
        } else if (path.equals("/admin/user")) {
            showUsers(request, response);
        } else if (path.equals("/admin/discount")) {
            showDiscounts(request, response);
        } else if (path.equals("/admin/payment")) {
            showPayments(request, response);
        } else if (path.equals("/admin/report")) {
            showReports(request, response);
        } else if (path.equals("/admin/setting")) {
            showSettings(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void showAdminHome(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Add some sample data for dashboard
        request.setAttribute("tong_don_hom_nay", 5);
        request.setAttribute("tong_cho_xu_ly", 3);
        request.setAttribute("tong_doanh_thu", "15,000,000 VND");
        request.setAttribute("tong_san_pham", 25);
        
        // Sample recent orders
        List<OrderDTO> recentOrders = orderService.findOrdersAll();
        if (recentOrders.size() > 3) {
            recentOrders = recentOrders.subList(0, 3);
        }
        request.setAttribute("don_moi", recentOrders);
        
        request.getRequestDispatcher("/admin/home.jsp").forward(request, response);
    }

    private void showOrderList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("q");
        String status = request.getParameter("status");

        List<OrderDTO> orderDTOList;
        if (status == null || status.trim().isEmpty()) {
            orderDTOList = orderService.findOrders(keyword);
        } else {
            orderDTOList = orderService.findOrdersByStatus(keyword, status.trim());
        }

        request.setAttribute("orders", orderDTOList);
        request.setAttribute("q", keyword);
        request.setAttribute("status", status);
        request.getRequestDispatcher("/order/Order-list.jsp").forward(request, response);
    }

    private void showOrderDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String orderIdString = pathInfo.substring("/detail/".length());
        
        try {
            int orderId = Integer.parseInt(orderIdString);
            Order order = orderService.getOrderById(orderId);
            List<OrderDetailDTO> details = orderService.findOrderDetailsWithProductName(orderId);

            request.setAttribute("order", order);
            request.setAttribute("details", details);
            request.getRequestDispatcher("/order/Order-detail.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/order");
        }
    }

    private void showProducts(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<h1>Quản lý sản phẩm</h1><p>Chức năng đang phát triển...</p>");
    }

    private void showUsers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<h1>Quản lý người dùng</h1><p>Chức năng đang phát triển...</p>");
    }

    private void showDiscounts(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<h1>Quản lý khuyến mãi</h1><p>Chức năng đang phát triển...</p>");
    }

    private void showPayments(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<h1>Quản lý thanh toán</h1><p>Chức năng đang phát triển...</p>");
    }

    private void showReports(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<h1>Báo cáo</h1><p>Chức năng đang phát triển...</p>");
    }

    private void showSettings(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<h1>Cấu hình hệ thống</h1><p>Chức năng đang phát triển...</p>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Handle POST requests for admin actions
        HttpSession session = request.getSession(false);
        String role = session != null ? (String) session.getAttribute("role") : null;
        if (role == null || !role.equalsIgnoreCase("admin")) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        switch (action) {
            case "approve": {
                String orderIdParam = request.getParameter("orderId");
                try {
                    int orderId = Integer.parseInt(orderIdParam);
                    boolean updated = orderService.updateOrderStatus(orderId, "hoan_thanh");
                    if (updated) {
                        response.sendRedirect(request.getContextPath() + "/admin/order/detail/" + orderId + "?msg=approved");
                    } else {
                        response.sendRedirect(request.getContextPath() + "/admin/order/detail/" + orderId + "?msg=approve_failed");
                    }
                } catch (NumberFormatException ex) {
                    response.sendRedirect(request.getContextPath() + "/admin/order?msg=invalid_order");
                }
                return;
            }
            case "deleteItem": {
                String orderIdParam = request.getParameter("orderId");
                String productIdParam = request.getParameter("productId");
                try {
                    int orderId = Integer.parseInt(orderIdParam);
                    int productId = Integer.parseInt(productIdParam);
                    orderService.deleteOrderItem(orderId, productId);
                    response.sendRedirect(request.getContextPath() + "/admin/order/detail/" + orderId + "?msg=item_deleted");
                } catch (NumberFormatException ex) {
                    response.sendRedirect(request.getContextPath() + "/admin/order?msg=invalid_params");
                }
                return;
            }
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
