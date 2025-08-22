package com.example.storeproject.controller.admin;

import com.example.storeproject.entity.User;
import com.example.storeproject.service.admin.AdminService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/register")
public class AdminRegisterServlet extends HttpServlet {
    private final AdminService adminService = new AdminService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/admin/Register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        boolean success = adminService.registerAdmin(new User(username, password));
        if (success) {
            req.setAttribute("success", "Register successfully!");
        } else {
            req.setAttribute("error", "Register failed (username may exist)!");
        }
        req.getRequestDispatcher("/admin/Register.jsp").forward(req, resp);
    }
}
