package com.example.storeproject.controller.login;

import com.example.storeproject.entity.User;
import com.example.storeproject.service.login.ILoginService;
import com.example.storeproject.service.login.LoginService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    private final ILoginService loginService = new LoginService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/login/Login.jsp").forward(req, resp); // giữ nguyên JSP bạn đang dùng
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
            req.setAttribute("message", "Sai tài khoản hoặc mật khẩu.");
            req.getRequestDispatcher("/login/Login.jsp").forward(req, resp);
            return;
        }

        // Lưu session cho navbar + filter
        HttpSession session = req.getSession(true);
        String name =
                user.getUserName()    != null ? user.getUserName() :
                        user.getUserName()    != null ? user.getUserName() :
                                user.getUserName() != null ? user.getUserName() : username;

        String role =
                user.getRole()   != null ? user.getRole().toLowerCase() :
                        user.getRole() != null ? user.getRole().toLowerCase() : "";

        session.setAttribute("username", name);
        session.setAttribute("role", role);
        try { session.setAttribute("userId", user.getUserId()); } catch (Exception ignored) {}
        session.setMaxInactiveInterval(30 * 60);

        // Nếu trước đó bị chặn khi vào /admin/* → quay về đúng URL đó
        String redirect = (String) session.getAttribute("redirectAfterLogin");
        session.removeAttribute("redirectAfterLogin");
        if (redirect != null && redirect.startsWith("/")) {
            resp.sendRedirect(req.getContextPath() + redirect);
            return;
        }

        // Admin → /admin/home, user thường → trang chủ
        if ("admin".equals(role)) {
            resp.sendRedirect(req.getContextPath() + "/admin/home");
        } else {
            resp.sendRedirect(req.getContextPath() + "/");
        }
    }
}
