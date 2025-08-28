package com.example.storeproject.controller;

import com.example.storeproject.entity.Category;
import com.example.storeproject.service.category.CategoryService;
import com.example.storeproject.service.category.ICategoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CategoryController", urlPatterns = {"/categories"})
public class CategoryController extends HttpServlet {

    private final ICategoryService service = new CategoryService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String q = req.getParameter("q");

        List<Category> list = (q == null || q.trim().isEmpty())
                ? service.getAll()
                : service.search(q.trim());

        req.setAttribute("categories", list);
        req.setAttribute("q", q == null ? "" : q);
        req.getRequestDispatcher("/categories.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        String msg;

        try {
            if ("create".equalsIgnoreCase(action)) {
                String name = req.getParameter("name");
                if (name == null || name.trim().isEmpty()) {
                    msg = "Tên danh mục không được để trống.";
                } else {
                    service.create(name.trim());
                    msg = "Thêm danh mục thành công.";
                }

            } else if ("update".equalsIgnoreCase(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                String name = req.getParameter("name");
                boolean ok = (name != null && !name.trim().isEmpty()) && service.update(id, name.trim());
                msg = ok ? "Cập nhật danh mục thành công." : "Cập nhật thất bại (không tìm thấy ID hoặc tên trống).";

            } else if ("delete".equalsIgnoreCase(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                boolean ok = service.delete(id);
                msg = ok ? "Xóa danh mục thành công." :
                        "Không thể xóa danh mục (có thể đang được tham chiếu bởi sản phẩm).";

            } else {
                msg = "Hành động không hợp lệ.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Có lỗi xảy ra: " + e.getMessage();
        }

        // Giữ lại từ khóa tìm kiếm nếu có
        String q = req.getParameter("q");
        if (q == null) q = "";

        // Lưu message hiển thị 1 lần
        req.getSession().setAttribute("flash", msg);
        resp.sendRedirect(req.getContextPath() + "/categories?q=" + q);
    }
}