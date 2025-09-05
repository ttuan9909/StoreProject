//package com.example.storeproject.controller.login;
//
//import com.example.storeproject.entity.User;
//import com.example.storeproject.service.login.ILoginService;
//import com.example.storeproject.service.login.LoginService;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.*;
//import java.io.IOException;
//
//@WebServlet("/login")
//public class LoginServlet extends HttpServlet {
//    private final ILoginService loginService = new LoginService();
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
//            throws ServletException, IOException {
//        req.getRequestDispatcher("/login/Login.jsp").forward(req, resp);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
//            throws ServletException, IOException {
//        req.setCharacterEncoding("UTF-8");
//
//        String username = req.getParameter("username");
//        String password = req.getParameter("password");
//
//        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
//            req.setAttribute("message", "Vui lòng nhập đầy đủ username/password.");
//            req.getRequestDispatcher("/login/Login.jsp").forward(req, resp);
//            return;
//        }
//
//        User user = loginService.login(username.trim(), password);
//        if (user == null) {
//            req.setAttribute("message", "Login failed! Sai username/password.");
//            req.getRequestDispatcher("/login/Login.jsp").forward(req, resp);
//            return;
//        }
//
//        HttpSession session = req.getSession();
//        session.setAttribute("currentUser", user);
//        session.setAttribute("userId", user.getUserId());
//        System.out.println("Stored userId in session: " + user.getUserId());
//
//        // Lấy redirect từ session hoặc parameter (hỗ trợ từ modal trong JSP)
//        String redirect = (String) session.getAttribute("redirectAfterLogin");
//        if (redirect == null) {
//            // Nếu không có từ session, kiểm tra parameter (từ modal)
//            redirect = req.getParameter("redirect");
//        }
//
//        System.out.println("LoginServlet: Redirect URL: " + redirect);
//        session.removeAttribute("redirectAfterLogin");  // Xóa sau khi dùng
//
//        if (redirect != null && !redirect.trim().isEmpty()) {
//            // Redirect về URL gốc (an toàn: chỉ redirect nếu là URL nội bộ)
//            if (redirect.startsWith("/")) {
//                resp.sendRedirect(req.getContextPath() + redirect);
//            } else {
//                // Nếu không an toàn, redirect mặc định
//                handleDefaultRedirect(req, resp, user);
//            }
//        } else {
//            // Redirect mặc định
//            handleDefaultRedirect(req, resp, user);
//        }
//
////        String role = user.getRole() == null ? "" : user.getRole().toLowerCase();
////
////        if ("admin".equals(role)) {
////            resp.sendRedirect(req.getContextPath() + "/admin/home.jsp");
////        } else {
////            resp.sendRedirect(req.getContextPath() + "/customer/home.jsp");
////        }
//    }
//
//    private void handleDefaultRedirect(HttpServletRequest req, HttpServletResponse resp, User user)
//            throws IOException {
//        String role = user.getRole() == null ? "" : user.getRole().toLowerCase();
//        if ("admin".equals(role)) {
//            resp.sendRedirect(req.getContextPath() + "/admin/home.jsp");
//        } else {
//            resp.sendRedirect(req.getContextPath() + "/products");  // Về trang sản phẩm cho customer
//        }
//    }
//}

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

        // Nếu đã đăng nhập thì chuyển theo role
        HttpSession s = req.getSession(false);
        if (s != null && s.getAttribute("currentUser") != null) {
            User u = (User) s.getAttribute("currentUser");
            handleDefaultRedirect(req, resp, u);
            return;
        }
        req.getRequestDispatcher("/login/Login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String username = trim(req.getParameter("username"));
        String password = trim(req.getParameter("password"));

        if (isBlank(username) || isBlank(password)) {
            req.setAttribute("message", "Vui lòng nhập đầy đủ username/password.");
            req.getRequestDispatcher("/login/Login.jsp").forward(req, resp);
            return;
        }

        // 1) Xác thực qua service (service tự cập nhật last_login nếu thành công)
        User user = loginService.login(username, password);
        if (user == null) {
            req.setAttribute("message", "Login failed! Sai username/password.");
            req.getRequestDispatcher("/login/Login.jsp").forward(req, resp);
            return;
        }
        loginService.updateLastLogin(user.getUserId(), new java.sql.Timestamp(System.currentTimeMillis()));


        // 2) LẤY redirect từ session cũ TRƯỚC khi invalidate
        HttpSession old = req.getSession(false);
        String redirectBefore = null;
        if (old != null) {
            Object r = old.getAttribute("redirectAfterLogin");
            if (r != null) redirectBefore = r.toString();
            old.invalidate(); // chống session fixation
        }

        // 3) Tạo session mới & lưu user
        HttpSession session = req.getSession(true);
        session.setAttribute("currentUser", user);
        session.setAttribute("userId", user.getUserId());
        session.setAttribute("role", user.getRole() == null ? "" : user.getRole());
        session.setMaxInactiveInterval(60 * 60 * 2); // 2 giờ

        // 4) Quyết định redirect: ưu tiên redirect của session cũ, sau đó param
        String redirect = redirectBefore;
        if (redirect == null || redirect.trim().isEmpty()) {
            redirect = req.getParameter("redirect");
        }

        // 5) Redirect an toàn
        String safe = normalizeInternalRedirect(req, redirect);
        if (safe != null) {
            resp.sendRedirect(req.getContextPath() + safe);
        } else {
            handleDefaultRedirect(req, resp, user);
        }
    }

    /* ===================== Helpers ===================== */

    private void handleDefaultRedirect(HttpServletRequest req, HttpServletResponse resp, User user)
            throws IOException {
        String role = user.getRole() == null ? "" : user.getRole().toLowerCase();
        if ("admin".equals(role)) {
            resp.sendRedirect(req.getContextPath() + "/admin/products");
        } else {
            resp.sendRedirect(req.getContextPath() + "/products");
        }
    }

    /** Chỉ chấp nhận path nội bộ bắt đầu bằng "/" */
    private String normalizeInternalRedirect(HttpServletRequest req, String redirect) {
        if (redirect == null) return null;
        String r = redirect.trim();
        if (r.isEmpty()) return null;

        // Trường hợp path nội bộ sẵn
        if (r.startsWith("/")) return r;

        // Cho phép URL tuyệt đối cùng origin (localhost cũng ok)
        try {
            java.net.URI uri = new java.net.URI(r);
            String scheme = uri.getScheme();
            if (!"http".equalsIgnoreCase(scheme) && !"https".equalsIgnoreCase(scheme)) return null;

            String reqHost = req.getServerName();
            int reqPort = req.getServerPort();
            String uriHost = uri.getHost();
            int uriPort = (uri.getPort() == -1 ? ("https".equalsIgnoreCase(scheme) ? 443 : 80) : uri.getPort());

            // Phải cùng host & port với request hiện tại
            if (!reqHost.equalsIgnoreCase(uriHost) || reqPort != uriPort) return null;

            // Lấy path trong app
            String ctx = req.getContextPath();            // ví dụ: /store
            String path = uri.getPath();                  // ví dụ: /store/admin/order
            if (path == null || path.isEmpty()) return null;

            // Cắt bỏ context nếu có, để còn lại path nội bộ chuẩn của app
            String internal = path.startsWith(ctx) ? path.substring(ctx.length()) : path;

            // Giữ query string nếu có
            String q = uri.getQuery();
            if (q != null && !q.isEmpty()) internal = internal + "?" + q;

            // Đảm bảo luôn bắt đầu bằng "/"
            if (!internal.startsWith("/")) internal = "/" + internal;
            return internal;
        } catch (Exception e) {
            return null;
        }
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
    private static String trim(String s) {
        return s == null ? null : s.trim();
    }
}

