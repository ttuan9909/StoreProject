package com.example.storeproject.controller;

import com.example.storeproject.dto.ProductDto;
import com.example.storeproject.entity.Category;
import com.example.storeproject.service.category.CategoryService;
import com.example.storeproject.service.category.ICategoryService;
import com.example.storeproject.service.product.IProductService;
import com.example.storeproject.service.product.ProductService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet(name = "ProductController", urlPatterns = {"/products"})
public class ProductController extends HttpServlet {

    private final IProductService productService = new ProductService();
    private final ICategoryService categoryService = new CategoryService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String q = req.getParameter("q");

        List<ProductDto> products = (q == null || q.trim().isEmpty())
                ? productService.findAll()
                : productService.search(q.trim());

        // Nạp categories để hiển thị trong <select> ở modal
        List<Category> categories = categoryService.getAll();

        req.setAttribute("products", products);
        req.setAttribute("categories", categories);
        req.setAttribute("q", q == null ? "" : q);

        req.getRequestDispatcher("/products.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        String msg;

        try {
            if ("create".equalsIgnoreCase(action)) {
                String name        = req.getParameter("name");
                String description = req.getParameter("description");
                String image       = req.getParameter("image");
                double price       = Double.parseDouble(req.getParameter("price"));
                int quantity       = Integer.parseInt(req.getParameter("quantity"));
                int categoryId     = Integer.parseInt(req.getParameter("categoryId"));

                // Theo form bạn yêu cầu: create(...) không cần check boolean
                productService.save(name, price, description, quantity, image, categoryId);
                msg = "Thêm sản phẩm thành công.";

            } else if ("edit".equalsIgnoreCase(action) || "update".equalsIgnoreCase(action)) {
                int id             = Integer.parseInt(req.getParameter("id"));
                String name        = req.getParameter("name");
                String description = req.getParameter("description");
                String image       = req.getParameter("image");
                double price       = Double.parseDouble(req.getParameter("price"));
                int quantity       = Integer.parseInt(req.getParameter("quantity"));
                int categoryId     = Integer.parseInt(req.getParameter("categoryId"));

                boolean ok = productService.update(id, name, price, description, quantity, image, categoryId);
                msg = ok ? "Cập nhật sản phẩm thành công."
                        : "Cập nhật thất bại (không tìm thấy ID hoặc dữ liệu không hợp lệ).";

            } else if ("delete".equalsIgnoreCase(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                boolean ok = productService.delete(id);
                msg = ok ? "Xóa sản phẩm thành công."
                        : "Không thể xóa sản phẩm (có thể đang được tham chiếu).";

            } else {
                msg = "Hành động không hợp lệ.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Có lỗi xảy ra: " + e.getMessage();
        }

        // Giữ lại từ khoá tìm kiếm nếu có
        String q = req.getParameter("q");
        if (q == null) q = "";

        // Flash message
        req.getSession().setAttribute("flash", msg);
        resp.sendRedirect(req.getContextPath() + "/products?q=" + q);
    }
}
