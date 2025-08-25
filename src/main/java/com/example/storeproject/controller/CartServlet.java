package com.example.storeproject.controller;

import com.example.storeproject.entity.CartDetail;
import com.example.storeproject.entity.Product;
import com.example.storeproject.service.cart.ICartService;
import com.example.storeproject.service.cart.CartService;
import com.example.storeproject.service.product.IProductService;
import com.example.storeproject.service.product.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CartServlet", urlPatterns = {"/cart", "/cart/*"})
public class CartServlet extends HttpServlet {
    private final ICartService cartService;
    private final IProductService productService;
    
    public CartServlet() {
        this.cartService = new CartService();
        this.productService = new ProductService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || "/".equals(pathInfo)) {
            showCart(request, response, userId);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        
        String action = request.getParameter("action");
        
        if ("add".equals(action)) {
            addToCart(request, response, userId);
        } else if ("update".equals(action)) {
            updateCartItem(request, response, userId);
        } else if ("remove".equals(action)) {
            removeFromCart(request, response, userId);
        } else if ("clear".equals(action)) {
            clearCart(request, response, userId);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    
    private void showCart(HttpServletRequest request, HttpServletResponse response, int userId) 
            throws ServletException, IOException {
        
        List<CartDetail> cartItems = cartService.getCartItems(userId);
        double cartTotal = cartService.getCartTotal(userId);
        
        // Lấy thông tin sản phẩm cho mỗi item trong giỏ hàng
        for (CartDetail cartItem : cartItems) {
            Product product = productService.getProductById(cartItem.getProductId());
            if (product != null) {
                // Tạo một object mới để chứa thông tin sản phẩm và số lượng
                request.setAttribute("cartItems", cartItems);
                request.setAttribute("cartTotal", cartTotal);
            }
        }
        
        request.getRequestDispatcher("/WEB-INF/page/cart/cart.jsp").forward(request, response);
    }
    
    private void addToCart(HttpServletRequest request, HttpServletResponse response, int userId) 
            throws ServletException, IOException {
        
        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            
            Product product = productService.getProductById(productId);
            if (product != null && quantity > 0) {
                boolean success = cartService.addProductToCart(userId, productId, quantity, product.getPrice());
                
                if (success) {
                    response.setContentType("application/json");
                    response.getWriter().write("{\"success\": true, \"message\": \"Sản phẩm đã được thêm vào giỏ hàng\"}");
                } else {
                    response.setContentType("application/json");
                    response.getWriter().write("{\"success\": false, \"message\": \"Không thể thêm sản phẩm vào giỏ hàng\"}");
                }
            } else {
                response.setContentType("application/json");
                response.getWriter().write("{\"success\": false, \"message\": \"Thông tin sản phẩm không hợp lệ\"}");
            }
        } catch (NumberFormatException e) {
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"message\": \"Dữ liệu không hợp lệ\"}");
        }
    }
    
    private void updateCartItem(HttpServletRequest request, HttpServletResponse response, int userId) 
            throws ServletException, IOException {
        
        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            
            if (quantity > 0) {
                boolean success = cartService.updateProductQuantity(userId, productId, quantity);
                
                if (success) {
                    response.setContentType("application/json");
                    response.getWriter().write("{\"success\": true, \"message\": \"Cập nhật giỏ hàng thành công\"}");
                } else {
                    response.setContentType("application/json");
                    response.getWriter().write("{\"success\": false, \"message\": \"Không thể cập nhật giỏ hàng\"}");
                }
            } else {
                response.setContentType("application/json");
                response.getWriter().write("{\"success\": false, \"message\": \"Số lượng phải lớn hơn 0\"}");
            }
        } catch (NumberFormatException e) {
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"message\": \"Dữ liệu không hợp lệ\"}");
        }
    }
    
    private void removeFromCart(HttpServletRequest request, HttpServletResponse response, int userId) 
            throws ServletException, IOException {
        
        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            
            boolean success = cartService.removeProductFromCart(userId, productId);
            
            if (success) {
                response.setContentType("application/json");
                response.getWriter().write("{\"success\": true, \"message\": \"Đã xóa sản phẩm khỏi giỏ hàng\"}");
            } else {
                response.setContentType("application/json");
                response.getWriter().write("{\"success\": false, \"message\": \"Không thể xóa sản phẩm khỏi giỏ hàng\"}");
            }
        } catch (NumberFormatException e) {
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"message\": \"Dữ liệu không hợp lệ\"}");
        }
    }
    
    private void clearCart(HttpServletRequest request, HttpServletResponse response, int userId) 
            throws ServletException, IOException {
        
        boolean success = cartService.clearCart(userId);
        
        if (success) {
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": true, \"message\": \"Đã xóa toàn bộ giỏ hàng\"}");
        } else {
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"message\": \"Không thể xóa giỏ hàng\"}");
        }
    }
}
