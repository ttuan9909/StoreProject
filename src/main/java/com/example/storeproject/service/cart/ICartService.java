package com.example.storeproject.service.cart;

import com.example.storeproject.entity.Cart;
import com.example.storeproject.entity.CartDetail;
import java.util.List;

public interface ICartService {
    Cart getOrCreateCart(int userId);
    boolean addProductToCart(int userId, int productId, int quantity, double price);
    boolean updateProductQuantity(int userId, int productId, int quantity);
    boolean removeProductFromCart(int userId, int productId);
    List<CartDetail> getCartItems(int userId);
    boolean clearCart(int userId);
    double getCartTotal(int userId);
    Cart getCartByUserId(int userId);
    List<CartDetail> getCartDetails(int userId);
}
