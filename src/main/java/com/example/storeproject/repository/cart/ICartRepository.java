package com.example.storeproject.repository.cart;

import com.example.storeproject.entity.Cart;
import com.example.storeproject.entity.CartDetail;
import java.util.List;

public interface ICartRepository {
    Cart getCartByUserId(int userId);
    Cart createCart(int userId);
    boolean addProductToCart(int cartId, int productId, int quantity, double price);
    boolean updateProductQuantity(int cartId, int productId, int quantity);
    boolean removeProductFromCart(int cartId, int productId);
    List<CartDetail> getCartItems(int cartId);
    boolean clearCart(int cartId);
    double getCartTotal(int cartId);
}
