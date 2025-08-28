package com.example.storeproject.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

@WebFilter(urlPatterns = {"/admin/*"})
public class AdminAuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request  = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession(false);

        String role = (session != null) ? (String) session.getAttribute("role") : null;
        if ("admin".equalsIgnoreCase(role)) {
            chain.doFilter(req, resp);
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
