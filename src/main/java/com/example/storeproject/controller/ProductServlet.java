package com.example.storeproject.controller;

import com.example.storeproject.entity.Product;
import com.example.storeproject.entity.Category;
import com.example.storeproject.service.product.IProductService;
import com.example.storeproject.service.product.ProductService;
import com.example.storeproject.repository.category.ICategoryRepository;
import com.example.storeproject.repository.category.CategoryRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductServlet", urlPatterns = {"/products", "/product/*", "/search"})
public class ProductServlet extends HttpServlet {
    private static final IProductService productService = new ProductService();
    private static final ICategoryRepository categoryRepository = new CategoryRepository();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        String servletPath = request.getServletPath();
        
        if ("/search".equals(servletPath)) {
            handleSearch(request, response);
        } else if (pathInfo == null || "/".equals(pathInfo)) {
            handleProductList(request, response);
        } else if (pathInfo.startsWith("/detail/")) {
            handleProductDetail(request, response);
        } else if (pathInfo.startsWith("/category/")) {
            handleProductByCategory(request, response);
        } else {
            handleProductList(request, response);
        }
    }
    
    private void handleProductList(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pageParam = request.getParameter("page");
        int page = 1;
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1;
            }
        }
        
        int pageSize = 12;
        List<Product> products = productService.getProductsWithPagination(page, pageSize);
        int totalPages = productService.getTotalPages(pageSize);
        List<Category> categories = categoryRepository.getAllCategories();
        
        request.setAttribute("products", products);
        request.setAttribute("categories", categories);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("pageSize", pageSize);
        
        request.getRequestDispatcher("/WEB-INF/page/product/product-list.jsp").forward(request, response);
    }
    
    private void handleProductDetail(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        String productIdStr = pathInfo.substring("/detail/".length());
        
        try {
            int productId = Integer.parseInt(productIdStr);
            Product product = productService.getProductById(productId);
            
            if (product != null) {
                request.setAttribute("product", product);
                request.getRequestDispatcher("/WEB-INF/page/product/product-detail.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    
    private void handleProductByCategory(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        String categoryIdStr = pathInfo.substring("/category/".length());
        
        try {
            int categoryId = Integer.parseInt(categoryIdStr);
            List<Product> products = productService.getProductsByCategory(categoryId);
            Category category = categoryRepository.getCategoryById(categoryId);
            List<Category> categories = categoryRepository.getAllCategories();
            
            request.setAttribute("products", products);
            request.setAttribute("selectedCategory", category);
            request.setAttribute("categories", categories);
            
            request.getRequestDispatcher("/WEB-INF/page/product/product-list.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    
    private void handleSearch(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String keyword = request.getParameter("keyword");
        List<Product> products = productService.searchProducts(keyword);
        List<Category> categories = categoryRepository.getAllCategories();
        
        request.setAttribute("products", products);
        request.setAttribute("categories", categories);
        request.setAttribute("searchKeyword", keyword);
        
        request.getRequestDispatcher("/WEB-INF/page/product/product-list.jsp").forward(request, response);
    }
}
