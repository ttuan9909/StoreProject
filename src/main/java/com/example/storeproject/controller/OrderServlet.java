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

@WebServlet(name = "OrderServlet", urlPatterns = {
    "/orders", 
    "/orders/*",
    "/order/list",
    "/order/detail/*"
})
public class OrderServlet extends HttpServlet {
    private static final IOrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = requestURI.substring(contextPath.length());

        if (path.equals("/orders") || path.equals("/order/list")) {
            showOrderList(request, response);
        } else if (path.startsWith("/order/detail/")) {
            showOrderDetail(request, response);
        } else if (path.startsWith("/orders/detail/")) {
            showOrderDetail(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
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

        String statusDisplayName = getStatusDisplayName(status);
        request.setAttribute("statusDisplayName", statusDisplayName);
        request.setAttribute("orders", orderDTOList);
        request.setAttribute("q", keyword);
        request.setAttribute("status", status);
        request.getRequestDispatcher("/order/Order-list.jsp").forward(request, response);
    }

    private String getStatusDisplayName(String status) {
        if (status == null || status.trim().isEmpty()) {
            return "Tất cả đơn hàng";
        }
        switch (status.trim()) {
            case "cho_xu_ly":
                return "Đơn hàng chờ xử lý";
            case "dang_xu_ly":
                return "Đơn hàng đang xử lý";
            case "cho_duyet":
                return "Đơn hàng chờ duyệt";
            case "hoan_thanh":
                return "Đơn hàng đã hoàn thành";
            case "huy":
                return "Đơn hàng đã huỷ";
            default:
                return "Đơn hàng";
        }
    }

    private String getStatusDisplayNameForOrder(String status) {
        if (status == null || status.trim().isEmpty()) {
            return "Không xác định";
        }
        switch (status.trim()) {
            case "cho_xu_ly":
                return "Chờ xử lý";
            case "dang_xu_ly":
                return "Đang xử lý";
            case "cho_duyet":
                return "Chờ duyệt";
            case "hoan_thanh":
                return "Hoàn thành";
            case "huy":
                return "Đã huỷ";
            default:
                return status;
        }
    }

    private void showOrderDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String[] pathParts = pathInfo.split("/");
        if (pathParts.length < 3) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            int orderId = Integer.parseInt(pathParts[2]);
            Order order = orderService.getOrderById(orderId);
            
            if (order == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            List<OrderDetailDTO> orderDetails = orderService.findOrderDetailsWithProductName(orderId);
            
            String statusDisplayName = getStatusDisplayNameForOrder(order.getOrderStatus());
            
            request.setAttribute("order", order);
            request.setAttribute("orderDetails", orderDetails);
            request.setAttribute("statusDisplayName", statusDisplayName);
            request.getRequestDispatcher("/order/Order-detail.jsp").forward(request, response);
        } catch (NumberFormatException ex) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
                        response.sendRedirect(request.getContextPath() + "/order/detail/" + orderId + "?msg=approved");
                    } else {
                        response.sendRedirect(request.getContextPath() + "/order/detail/" + orderId + "?msg=approve_failed");
                    }
                } catch (NumberFormatException ex) {
                    response.sendRedirect(request.getContextPath() + "/orders?msg=invalid_order");
                }
                return;
            }
            case "process": {
                String orderIdParam = request.getParameter("orderId");
                try {
                    int orderId = Integer.parseInt(orderIdParam);
                    boolean updated = orderService.updateOrderStatus(orderId, "dang_xu_ly");
                    if (updated) {
                        response.sendRedirect(request.getContextPath() + "/orders?status=cho_xu_ly&msg=processing_started");
                    } else {
                        response.sendRedirect(request.getContextPath() + "/orders?status=cho_xu_ly&msg=processing_failed");
                    }
                } catch (NumberFormatException ex) {
                    response.sendRedirect(request.getContextPath() + "/orders?msg=invalid_order");
                }
                return;
            }
            case "readyForApproval": {
                String orderIdParam = request.getParameter("orderId");
                try {
                    int orderId = Integer.parseInt(orderIdParam);
                    boolean updated = orderService.updateOrderStatus(orderId, "cho_duyet");
                    if (updated) {
                        response.sendRedirect(request.getContextPath() + "/orders?status=dang_xu_ly&msg=ready_for_approval");
                    } else {
                        response.sendRedirect(request.getContextPath() + "/orders?status=dang_xu_ly&msg=status_update_failed");
                    }
                } catch (NumberFormatException ex) {
                    response.sendRedirect(request.getContextPath() + "/orders?msg=invalid_order");
                }
                return;
            }
            case "cancel": {
                String orderIdParam = request.getParameter("orderId");
                try {
                    int orderId = Integer.parseInt(orderIdParam);
                    boolean updated = orderService.updateOrderStatus(orderId, "huy");
                    if (updated) {
                        response.sendRedirect(request.getContextPath() + "/orders?msg=order_cancelled");
                    } else {
                        response.sendRedirect(request.getContextPath() + "/orders?msg=cancel_failed");
                    }
                } catch (NumberFormatException ex) {
                    response.sendRedirect(request.getContextPath() + "/orders?msg=invalid_order");
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
                    response.sendRedirect(request.getContextPath() + "/order/detail/" + orderId + "?msg=item_deleted");
                } catch (NumberFormatException ex) {
                    response.sendRedirect(request.getContextPath() + "/orders?msg=invalid_params");
                }
                return;
            }
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
