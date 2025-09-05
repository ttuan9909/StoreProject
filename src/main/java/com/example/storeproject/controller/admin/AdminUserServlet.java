package com.example.storeproject.controller.admin;

import com.example.storeproject.entity.User;
import com.example.storeproject.repository.user.IUserRepository;
import com.example.storeproject.repository.user.UserRepository;
import com.example.storeproject.service.user.IUserService;
import com.example.storeproject.service.user.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/users")
public class AdminUserServlet extends HttpServlet {
    private IUserService service;

    @Override
    public void init() {
        IUserRepository repo = new UserRepository();        // repo hiện có của bạn
        service = new UserService(repo);                // dùng qua interface
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int page = parseInt(req.getParameter("page"), 1);
        int size = parseInt(req.getParameter("size"), 10);
        String q = safe(req.getParameter("q"));
        String role = safe(req.getParameter("role"));

        IUserService.PagedResult<User> result = service.listUsers(page, size, q, role);
        req.setAttribute("users", result.items);
        req.setAttribute("page", result.page);
        req.setAttribute("size", result.size);
        req.setAttribute("total", result.total);
        req.setAttribute("q", q);
        req.setAttribute("role", role);

        req.getRequestDispatcher("/admin/adminUser.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = safe(req.getParameter("action"));

        if ("toggle-active".equals(action)) {
            int id = parseInt(req.getParameter("id"), 0);
            int activeTo = parseInt(req.getParameter("activeTo"), 1); // 1: kích hoạt, 0: vô hiệu hoá
            if (id > 0) {
                int affected = service.setActive(id, activeTo == 1);
                req.getSession().setAttribute("flash",
                        (activeTo == 1 ? "Đã kích hoạt " : "Đã vô hiệu hoá ") + affected + " tài khoản (ID=" + id + ").");
            }
            resp.sendRedirect(req.getContextPath() + "/admin/users");
            return;
        }

        if ("deactivate-inactive".equals(action)) {
            int days = parseInt(req.getParameter("days"), 180);
            int affected = service.deactivateInactiveByDays(days);
            req.getSession().setAttribute("flash",
                    "Đã vô hiệu hoá " + affected + " tài khoản không hoạt động trong " + days + " ngày.");
            resp.sendRedirect(req.getContextPath() + "/admin/users");
            return;
        }

        if ("delete-inactive".equals(action)) {
            int days = parseInt(req.getParameter("days"), 180);
            int affected = service.deleteInactiveByDays(days);
            req.getSession().setAttribute("flash",
                    "Đã xoá " + affected + " tài khoản không hoạt động trong " + days + " ngày.");
            resp.sendRedirect(req.getContextPath() + "/admin/users");
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/admin/users");
    }

    private int parseInt(String s, int def) { try { return Integer.parseInt(s); } catch (Exception e) { return def; } }
    private String safe(String s) { return s == null ? "" : s.trim(); }
}
