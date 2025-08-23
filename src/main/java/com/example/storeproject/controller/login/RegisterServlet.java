package com.example.storeproject.controller.login;

import com.example.storeproject.entity.User;
import com.example.storeproject.service.login.ILoginService;
import com.example.storeproject.service.login.LoginService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/register")
public class RegisterServlet extends HttpServlet {
    private final ILoginService loginService = new LoginService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String step = req.getParameter("step");
        HttpSession session = req.getSession(false);

        if ("2".equals(step)) {
            if (session != null) {
                req.setAttribute("pending_username", session.getAttribute("pending_username"));
            }
            req.getRequestDispatcher("/login/RegisterStep2.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/login/RegisterStep1.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String step = req.getParameter("step");
        if ("1".equals(step)) {
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            if (username == null || username.isBlank() ||
                    password == null || password.isBlank()) {
                req.setAttribute("error", "Vui lòng nhập đầy đủ username/password");
                req.getRequestDispatcher("/login/RegisterStep1.jsp").forward(req, resp);
                return;
            }

            HttpSession session = req.getSession();
            session.setAttribute("pending_username", username.trim());
            session.setAttribute("pending_password", password);

            resp.sendRedirect(req.getContextPath() + "/admin/register?step=2");
            return;
        }

        if ("2".equals(step)) {
            HttpSession session = req.getSession(false);
            if (session == null) {
                resp.sendRedirect(req.getContextPath() + "/admin/register");
                return;
            }

            String username = (String) session.getAttribute("pending_username");
            String password = (String) session.getAttribute("pending_password");
            if (username == null || password == null) {
                resp.sendRedirect(req.getContextPath() + "/admin/register");
                return;
            }

            String email  = req.getParameter("email");
            String hoTen  = req.getParameter("ho_ten");
            String sdt    = req.getParameter("so_dien_thoai");
            String diaChi = req.getParameter("dia_chi");
            String vaiTro = req.getParameter("vai_tro"); // admin/staff/customer

            String normalizedRole = (vaiTro == null || vaiTro.isBlank())
                    ? "customer" : vaiTro.trim().toLowerCase();
            if (!normalizedRole.equals("admin") && !normalizedRole.equals("staff")) {
                normalizedRole = "customer";
            }

            User u = new User(username, password);
            u.setEmail(email);
            u.setFullName(hoTen);
            u.setPhone(sdt);
            u.setAddress(diaChi);
            u.setRole(normalizedRole);

            boolean ok = loginService.register(u);

            session.removeAttribute("pending_username");
            session.removeAttribute("pending_password");

            if (ok) {
                req.setAttribute("username", username);
                req.setAttribute("role", u.getRole());
                req.getRequestDispatcher("/login/RegisterSuccess.jsp").forward(req, resp);
            } else {
                req.setAttribute("pending_username", username);
                req.setAttribute("error", "Register failed (username có thể đã tồn tại)!");
                req.getRequestDispatcher("/login/RegisterStep2.jsp").forward(req, resp);
            }
        }
    }
}
