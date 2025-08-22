package com.example.storeproject.controller.admin;

import com.example.storeproject.entity.admin.Admin;
import com.example.storeproject.service.admin.AdminService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/login")
public class AdminLoginServlet extends HttpServlet {
    private final AdminService adminService = new AdminService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("admin/Login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        Admin admin = adminService.loginAdmin(username, password);
        if (admin != null) {
            req.getSession().setAttribute("admin_username", admin.getAdmin_username());
            resp.sendRedirect("Welcome.jsp");
        } else {
            req.setAttribute("error", "Login failed!");
            req.getRequestDispatcher("/admin/Login.jsp").forward(req, resp);
        }
    }
}
