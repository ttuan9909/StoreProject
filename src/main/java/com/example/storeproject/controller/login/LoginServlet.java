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

        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
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
        session.setAttribute("userId", user.getUserId());
        System.out.println("Stored userId in session: " + user.getUserId());

        // Lấy redirect từ session hoặc parameter (hỗ trợ từ modal trong JSP)
        String redirect = (String) session.getAttribute("redirectAfterLogin");
        if (redirect == null) {
            // Nếu không có từ session, kiểm tra parameter (từ modal)
            redirect = req.getParameter("redirect");
        }

        System.out.println("LoginServlet: Redirect URL: " + redirect);
        session.removeAttribute("redirectAfterLogin");  // Xóa sau khi dùng

        if (redirect != null && !redirect.trim().isEmpty()) {
            // Redirect về URL gốc (an toàn: chỉ redirect nếu là URL nội bộ)
            if (redirect.startsWith("/")) {
                resp.sendRedirect(req.getContextPath() + redirect);
            } else {
                // Nếu không an toàn, redirect mặc định
                handleDefaultRedirect(req, resp, user);
            }
        } else {
            // Redirect mặc định
            handleDefaultRedirect(req, resp, user);
        }

//        String role = user.getRole() == null ? "" : user.getRole().toLowerCase();
//
//        if ("admin".equals(role)) {
//            resp.sendRedirect(req.getContextPath() + "/admin/home.jsp");
//        } else {
//            resp.sendRedirect(req.getContextPath() + "/customer/home.jsp");
//        }
    }

    private void handleDefaultRedirect(HttpServletRequest req, HttpServletResponse resp, User user)
            throws IOException {
        String role = user.getRole() == null ? "" : user.getRole().toLowerCase();
        if ("admin".equals(role)) {
            resp.sendRedirect(req.getContextPath() + "/admin/home.jsp");
        } else {
            resp.sendRedirect(req.getContextPath() + "/products");  // Về trang sản phẩm cho customer
        }
    }
}
