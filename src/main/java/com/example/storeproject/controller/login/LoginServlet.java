package com.example.storeproject.controller.login;

import com.example.storeproject.entity.User;
import com.example.storeproject.service.login.ILoginService;
import com.example.storeproject.service.login.LoginService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final ILoginService loginService = new LoginService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/login/Login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            req.setAttribute("message", "Vui lòng nhập đầy đủ username/password.");
            req.getRequestDispatcher("/login/Login.jsp").forward(req, resp);
            return;
        }

        User user = loginService.login(username.trim(), password);
        if (user == null) {
            req.setAttribute("message", "Login failed! Sai username/password.");
            req.getRequestDispatcher("/login/Login.jsp").forward(req, resp);
            return;
        }

        HttpSession session = req.getSession();
        session.setAttribute("currentUser", user);

        String role = user.getRole() == null ? "" : user.getRole().toLowerCase();

        if ("admin".equals(role)) {
            resp.sendRedirect(req.getContextPath() + "/admin/home.jsp");
        } else {
            resp.sendRedirect(req.getContextPath() + "/customer/home.jsp");
        }
    }
}
