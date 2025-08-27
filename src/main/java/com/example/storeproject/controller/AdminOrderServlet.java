package com.example.storeproject.controller;

import com.example.storeproject.dto.OrderDTO;
import com.example.storeproject.dto.OrderDetailDTO;
import com.example.storeproject.entity.Order;
import com.example.storeproject.service.order.IOrderService;
import com.example.storeproject.service.order.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminOrderServlet", urlPatterns = {"/admin/order", "/admin/order/*"})
public class AdminOrderServlet extends HttpServlet {
    private static final IOrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(false);
        String role = httpSession != null ? (String) httpSession.getAttribute("role") : null;
        if (role == null || !role.equalsIgnoreCase("admin")) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || "/".equals(pathInfo)) {
            showOrderList(request, response);
            return;
        }
        if (pathInfo.startsWith("/detail/")) {
            showOrderDetail(request, response);
            return;
        }
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(false);
        String role = httpSession != null ? (String) httpSession.getAttribute("role") : null;
        if (role == null || !role.equalsIgnoreCase("admin")) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        String action = request.getParameter("action");
        if (action == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (action.equals("approve")) {
            String orderIdParam = request.getParameter("orderId");
            int orderId = Integer.parseInt(orderIdParam);
            boolean updated = orderService.updateOrderStatus(orderId, "dang_xu_ly");
            if (updated) {
                response.sendRedirect(request.getContextPath() + "/admin/order/detail/" + orderId);
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/order");
            }
            return;
        }
        if (action.equals("deleteItem")) {
            String orderIdParam = request.getParameter("orderId");
            String productIdParam = request.getParameter("productId");
            int orderId = Integer.parseInt(orderIdParam);
            int productId = Integer.parseInt(productIdParam);
            orderService.deleteOrderItem(orderId, productId);
            response.sendRedirect(request.getContextPath() + "/admin/order/detail/" + orderId);
            return;
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void showOrderList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("q");
        List<OrderDTO> orderDTOList = orderService.findOrders(keyword);
        request.setAttribute("orders", orderDTOList);
        request.setAttribute("q", keyword);
        request.getRequestDispatcher("/Order-list.jsp").forward(request, response);
    }

    private void showOrderDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String orderIdString = pathInfo.substring("/detail/".length());
        int orderId = Integer.parseInt(orderIdString);
        Order order = orderService.getOrderById(orderId);
        List<OrderDetailDTO> orderDetailDTOList = orderService.findOrderDetailsWithProductName(orderId);
        request.setAttribute("order", order);
        request.setAttribute("details", orderDetailDTOList);
        request.getRequestDispatcher("/Order-detail.jsp").forward(request, response);
    }
}
