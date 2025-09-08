//package com.example.storeproject.controller;
//
//import com.example.storeproject.dto.OrderDetailDTO;
//import com.example.storeproject.entity.Cart;
//import com.example.storeproject.entity.CartDetail;
//import com.example.storeproject.entity.Order;
//import com.example.storeproject.dto.OrderDTO;
//import com.example.storeproject.entity.OrderDetail;
//import com.example.storeproject.service.order.IOrderService;
//import com.example.storeproject.service.order.OrderService;
//import com.example.storeproject.service.cart.ICartService;
//import com.example.storeproject.service.cart.CartService;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//import java.util.List;
//
//@WebServlet(name = "OrderServlet", urlPatterns = {"/order", "/order/create", "/order/*"})
//public class OrderServlet extends HttpServlet {
//    private static final IOrderService orderService = new OrderService();
//    private static final ICartService cartService = new CartService();
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        HttpSession session = request.getSession();
//        Integer userId = (Integer) session.getAttribute("userId");
//        System.out.println("OrderServlet doGet: Session ID: " + session.getId());
//        System.out.println("OrderServlet doGet: userId from session: " + userId);
//
//        if (userId == null) {
//            // Lưu URL gốc (bao gồm query string nếu có)
//            String currentUrl = request.getRequestURI();
//            if (request.getQueryString() != null) {
//                currentUrl += "?" + request.getQueryString();
//            }
//            System.out.println("OrderServlet doGet: No userId, saving redirect: " + currentUrl);
//            session.setAttribute("redirectAfterLogin", currentUrl);
//
//            response.sendRedirect(request.getContextPath() + "/login");
//            return;
//        }
//
//        String pathInfo = request.getPathInfo();
//        if (pathInfo == null || "/".equals(pathInfo)) {
//            showOrderHistory(request, response, userId);
//        } else if (pathInfo.startsWith("/detail/")) {
//            showOrderDetail(request, response, userId);
//        } else {
//            response.sendError(HttpServletResponse.SC_NOT_FOUND);
//        }
//    }
//
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        HttpSession session = request.getSession();
//        Integer userId = (Integer) session.getAttribute("userId");
//        System.out.println("OrderServlet doPost: Session ID: " + session.getId());
//        System.out.println("OrderServlet doPost: userId from session: " + userId);
//
//        if (userId == null) {
//            // Lưu URL gốc (bao gồm query string nếu có)
//            String currentUrl = request.getRequestURI();
//            if (request.getQueryString() != null) {
//                currentUrl += "?" + request.getQueryString();
//            }
//            System.out.println("OrderServlet doPost: No userId, saving redirect: " + currentUrl);
//            session.setAttribute("redirectAfterLogin", currentUrl);
//
//            response.sendRedirect(request.getContextPath() + "/login");
//            return;
//        }
//
//        String action = request.getParameter("action");
//
//        if ("create".equals(action)) {
//            createOrder(request, response, userId);
//        } else {
//            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
//        }
//    }
//
//    private void showOrderHistory(HttpServletRequest request, HttpServletResponse response, int userId)
//            throws ServletException, IOException {
//
//        List<Order> orders = orderService.getOrdersByUserId(userId);
//        request.setAttribute("orders", orders);
//
//        request.getRequestDispatcher("/WEB-INF/page/order/order-history.jsp").forward(request, response);
//    }
//
//    private void showOrderDetail(HttpServletRequest request, HttpServletResponse response, int userId)
//            throws ServletException, IOException {
//
//        String pathInfo = request.getPathInfo();
//        String orderIdStr = pathInfo.substring("/detail/".length());
//
//        try {
//            int orderId = Integer.parseInt(orderIdStr);
//            Order order = orderService.getOrderById(orderId);
//
//            if (order != null && order.getUserId() == userId) {
//                List<OrderDetailDTO> orderDetails = orderService.getOrderDetailsByOrderId(orderId);
//
//                request.setAttribute("order", order);
//                request.setAttribute("orderDetails", orderDetails);
//
//                request.getRequestDispatcher("/WEB-INF/page/order/order-detail.jsp").forward(request, response);
//            } else {
//                response.sendError(HttpServletResponse.SC_NOT_FOUND);
//            }
//        } catch (NumberFormatException e) {
//            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
//        }
//    }
//
//    private void createOrder(HttpServletRequest request, HttpServletResponse response, int userId)
//            throws ServletException, IOException {
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        try {
//            Cart cart = cartService.getCartByUserId(userId);
//            List<CartDetail> cartDetails = cartService.getCartDetails(userId);
//            System.out.println("OrderServlet: cart = " + (cart != null ? cart.getCartId() : "null"));
//            System.out.println("OrderServlet: cartDetails = " + (cartDetails != null ? cartDetails.size() : "null"));
//            if (cart != null && cartDetails != null && !cartDetails.isEmpty()) {
//                Order order = orderService.createOrderFromCart(userId, cart, cartDetails);
//                if (order != null) {
//                    boolean cleared = cartService.clearCart(userId);
//                    if (!cleared) {
//                        System.out.println("OrderServlet: Failed to clear cart for userId=" + userId);
//                        response.setStatus(HttpServletResponse.SC_OK);
//                        response.getWriter().write(
//                                "{\"success\": true, \"message\": \"Đặt hàng thành công nhưng không thể xóa giỏ hàng\", \"orderId\": " + order.getOrderId() + "}"
//                        );
//                    } else {
//                        response.setStatus(HttpServletResponse.SC_OK);
//                        response.getWriter().write(
//                                "{\"success\": true, \"message\": \"Đặt hàng thành công\", \"orderId\": " + order.getOrderId() + "}"
//                        );
//                    }
//                } else {
//                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//                    response.getWriter().write("{\"success\": false, \"message\": \"Không thể tạo đơn hàng: Lỗi dữ liệu hoặc cơ sở dữ liệu.\"}");
//                }
//            } else {
//                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//                response.getWriter().write("{\"success\": false, \"message\": \"Giỏ hàng trống.\"}");
//            }
//        } catch (Exception e) {
//            System.out.println("OrderServlet: Exception - " + e.getMessage());
//            e.printStackTrace();
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            response.getWriter().write("{\"success\": false, \"message\": \"Có lỗi xảy ra khi đặt hàng: " + e.getMessage() + "\"}");
//        }
//    }
//}

package com.example.storeproject.controller;

import com.example.storeproject.dto.OrderDTO;
import com.example.storeproject.dto.OrderDetailDTO;
import com.example.storeproject.entity.Cart;
import com.example.storeproject.entity.CartDetail;
import com.example.storeproject.entity.Order;
import com.example.storeproject.service.cart.CartService;
import com.example.storeproject.service.cart.ICartService;
import com.example.storeproject.service.order.IOrderService;
import com.example.storeproject.service.order.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(
        name = "OrderServlet",
        urlPatterns = {
                // Admin zone
                "/orders", "/orders/*", "/order/list", "/order/detail/*",
                // User zone
                "/order", "/order/", "/order/create"
        }
)
public class OrderServlet extends HttpServlet {

    private static final IOrderService orderService = new OrderService();
    private static final ICartService cartService = new CartService();

    // ========== ROUTER ==========
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        final String requestURI = request.getRequestURI();
        final String ctx = request.getContextPath();
        final String path = requestURI.substring(ctx.length()); // includes leading '/'

        // ---- Admin routes (/orders..., /order/list, /order/detail/{id}) ----
        if (path.equals("/orders") || path.equals("/order/list")) {
            showOrderListAdmin(request, response);
            return;
        }
        if (path.startsWith("/orders/detail/")) {
            showOrderDetailAdmin(request, response, extractIdFromTail(path));
            return;
        }

        // ---- User routes (/order, /order/, /order/detail/{id}) ----
        if (path.equals("/order") || path.equals("/order/")) {
            Integer userId = requireLoginThenGetUserId(request, response);
            if (userId == null) return; // redirected to /login
            showOrderHistoryUser(request, response, userId);
            return;
        }
        if (path.startsWith("/order/detail/")) {
            Integer userId = requireLoginThenGetUserId(request, response);
            if (userId == null) return;
            showOrderDetailUser(request, response, userId, extractIdFromTail(path));
            return;
        }

        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        final String requestURI = request.getRequestURI();
        final String ctx = request.getContextPath();
        final String path = requestURI.substring(ctx.length());

        final String action = request.getParameter("action");

        // ---- Admin actions (status transitions, delete item) ----
        if (path.startsWith("/orders") || path.startsWith("/order/list") || path.startsWith("/order/detail/")) {
            if (action == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            switch (action) {
                case "approve":  handleAdminUpdateStatus(request, response, "hoan_thanh", true); return;
                case "process":  handleAdminUpdateStatus(request, response, "dang_xu_ly", false); return;
                case "readyForApproval": handleAdminUpdateStatus(request, response, "cho_duyet", false); return;
                case "cancel":   handleAdminUpdateStatus(request, response, "huy", false); return;
                case "deleteItem": handleAdminDeleteItem(request, response); return;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
            }
        }

        // ---- User actions (create order from cart) ----
        if (path.equals("/order") || path.equals("/order/") || path.equals("/order/create")) {
            Integer userId = requireLoginThenGetUserId(request, response);
            if (userId == null) return;

            if ("create".equals(action) || path.equals("/order/create")) {
                createOrderFromCart(request, response, userId);
                return;
            }
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    // ========== ADMIN HANDLERS ==========
    private void showOrderListAdmin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("q");
        String status  = request.getParameter("status");

        List<OrderDTO> orderDTOList = (status == null || status.trim().isEmpty())
                ? orderService.findOrders(keyword)
                : orderService.findOrdersByStatus(keyword, status.trim());

        request.setAttribute("statusDisplayName", getStatusDisplayName(status));
        request.setAttribute("orders", orderDTOList);
        request.setAttribute("q", keyword);
        request.setAttribute("status", status);

        request.getRequestDispatcher("/order/Order-list.jsp").forward(request, response);
    }

    private void showOrderDetailAdmin(HttpServletRequest request, HttpServletResponse response, Integer orderId)
            throws ServletException, IOException {
        if (orderId == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
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
    }

    private void handleAdminUpdateStatus(HttpServletRequest request, HttpServletResponse response,
                                         String newStatus, boolean redirectToDetail) throws IOException {
        String orderIdParam = request.getParameter("orderId");
        try {
            int orderId = Integer.parseInt(orderIdParam);
            boolean updated = orderService.updateOrderStatus(orderId, newStatus);
            if (redirectToDetail) {
                response.sendRedirect(request.getContextPath() + "/order/detail/" + orderId + (updated ? "?msg=approved" : "?msg=approve_failed"));
            } else {
                String sourceStatus =
                        "dang_xu_ly".equals(newStatus) ? "cho_xu_ly"
                                : "cho_duyet".equals(newStatus) ? "dang_xu_ly"
                                : null;
                String redirect = request.getContextPath() + "/orders";
                if (sourceStatus != null) redirect += "?status=" + sourceStatus + "&";
                else redirect += "?";
                redirect += updated ? "msg=updated" : "msg=update_failed";
                response.sendRedirect(redirect);
            }
        } catch (NumberFormatException ex) {
            response.sendRedirect(request.getContextPath() + "/orders?msg=invalid_order");
        }
    }

    private void handleAdminDeleteItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
    }

    // ========== USER HANDLERS ==========
    private void showOrderHistoryUser(HttpServletRequest request, HttpServletResponse response, int userId)
            throws ServletException, IOException {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/WEB-INF/page/order/order-history.jsp").forward(request, response);
    }

    private void showOrderDetailUser(HttpServletRequest request, HttpServletResponse response, int userId, Integer orderId)
            throws ServletException, IOException {
        if (orderId == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        Order order = orderService.getOrderById(orderId);
        if (order != null && order.getUserId() == userId) {
            List<OrderDetailDTO> orderDetails = orderService.getOrderDetailsByOrderId(orderId);
            request.setAttribute("order", order);
            request.setAttribute("orderDetails", orderDetails);
            request.getRequestDispatcher("/WEB-INF/page/order/order-detail.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void createOrderFromCart(HttpServletRequest request, HttpServletResponse response, int userId)
            throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            Cart cart = cartService.getCartByUserId(userId);
            List<CartDetail> cartDetails = cartService.getCartDetails(userId);

            if (cart != null && cartDetails != null && !cartDetails.isEmpty()) {
                Order order = orderService.createOrderFromCart(userId, cart, cartDetails);
                if (order != null) {
                    boolean cleared = cartService.clearCart(userId);
                    if (!cleared) {
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
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"success\": false, \"message\": \"Có lỗi xảy ra khi đặt hàng: " + e.getMessage() + "\"}");
        }
    }

    // ========== HELPERS ==========
    private Integer requireLoginThenGetUserId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            // Lưu URL hiện tại để quay lại sau đăng nhập
            String currentUrl = request.getRequestURI();
            if (request.getQueryString() != null) currentUrl += "?" + request.getQueryString();
            session.setAttribute("redirectAfterLogin", currentUrl);
            response.sendRedirect(request.getContextPath() + "/login");
            return null;
        }
        return userId;
    }

    private Integer extractIdFromTail(String fullPath) {
        // Nhận id ở cuối path ví dụ: /order/detail/123  hoặc  /orders/detail/123
        try {
            int lastSlash = fullPath.lastIndexOf('/');
            if (lastSlash >= 0 && lastSlash + 1 < fullPath.length()) {
                return Integer.parseInt(fullPath.substring(lastSlash + 1));
            }
        } catch (NumberFormatException ignored) {}
        return null;
    }

    private String getStatusDisplayName(String status) {
        if (status == null || status.trim().isEmpty()) {
            return "Tất cả đơn hàng";
        }
        switch (status.trim()) {
            case "cho_xu_ly": return "Đơn hàng chờ xử lý";
            case "dang_xu_ly": return "Đơn hàng đang xử lý";
            case "cho_duyet": return "Đơn hàng chờ duyệt";
            case "hoan_thanh": return "Đơn hàng đã hoàn thành";
            case "huy":        return "Đơn hàng đã huỷ";
            default:           return "Đơn hàng";
        }
    }

    private String getStatusDisplayNameForOrder(String status) {
        if (status == null || status.trim().isEmpty()) return "Không xác định";
        switch (status.trim()) {
            case "cho_xu_ly":  return "Chờ xử lý";
            case "dang_xu_ly": return "Đang xử lý";
            case "cho_duyet":  return "Chờ duyệt";
            case "hoan_thanh": return "Hoàn thành";
            case "huy":        return "Đã huỷ";
            default:           return status;
        }
    }
}
