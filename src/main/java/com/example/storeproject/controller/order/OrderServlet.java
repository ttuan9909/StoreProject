package com.example.storeproject.controller.order;

import com.example.storeproject.entity.order.Order;
import com.example.storeproject.entity.order.OrderDetail;
import com.example.storeproject.service.order.IOrderService;
import com.example.storeproject.service.order.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/order")
public class OrderServlet extends HttpServlet {
    private final IOrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("detail".equals(action)) {
            int orderId = Integer.parseInt(req.getParameter("orderId"));
            List<OrderDetail> detailList = orderService.getDetails(orderId);
            req.setAttribute("detailList", detailList);
            req.setAttribute("orderId", orderId);
            req.getRequestDispatcher("/admin/order-detail.jsp").forward(req, resp);
        } else {
            String keyword = req.getParameter("keyword");
            List<Order> orders = keyword == null || keyword.isEmpty() ? orderService.getAll() : orderService.search(keyword);
            req.setAttribute("orders", orders);
            req.getRequestDispatcher("/admin/order-list.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("approve".equals(action)) {
            int orderId = Integer.parseInt(req.getParameter("id"));
            orderService.approve(orderId);
            resp.sendRedirect("order");
        } else if ("removeProduct".equals(action)) {
            int orderId = Integer.parseInt(req.getParameter("orderId"));
            int productId = Integer.parseInt(req.getParameter("productId"));
            orderService.removeProduct(orderId, productId);
            resp.sendRedirect("order?action=detail&id=" + orderId);
        }
    }
}
