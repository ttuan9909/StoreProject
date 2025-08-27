package com.example.storeproject.service.cart;

import com.example.storeproject.entity.Cart;
import com.example.storeproject.entity.CartDetail;
import com.example.storeproject.repository.cart.ICartRepository;
import com.example.storeproject.repository.cart.CartRepository;

import java.util.ArrayList;
import java.util.List;

public class CartService implements ICartService {
    private final ICartRepository cartRepository;
    
    public CartService() {
        this.cartRepository = new CartRepository();
    }
    
    @Override
    public Cart getOrCreateCart(int userId) {
        Cart cart = cartRepository.getCartByUserId(userId);
        if (cart == null) {
            cart = cartRepository.createCart(userId);
        }
        return cart;
    }
    
    @Override
    public boolean addProductToCart(int userId, int productId, int quantity, double price) {
        Cart cart = getOrCreateCart(userId);
        if (cart != null) {
            return cartRepository.addProductToCart(cart.getCartId(), productId, quantity, price);
        }
        return false;
    }
    
    @Override
    public boolean updateProductQuantity(int userId, int productId, int quantity) {
        Cart cart = cartRepository.getCartByUserId(userId);
        if (cart != null) {
            return cartRepository.updateProductQuantity(cart.getCartId(), productId, quantity);
        }
        return false;
    }
    
    @Override
    public boolean removeProductFromCart(int userId, int productId) {
        Cart cart = cartRepository.getCartByUserId(userId);
        if (cart != null) {
            return cartRepository.removeProductFromCart(cart.getCartId(), productId);
        }
        return false;
    }
    
    @Override
    public List<CartDetail> getCartItems(int userId) {
        Cart cart = cartRepository.getCartByUserId(userId);
        if (cart != null) {
            return cartRepository.getCartItems(cart.getCartId());
        }
        return new ArrayList<>();
    }
    
    @Override
    public boolean clearCart(int userId) {
        Cart cart = cartRepository.getCartByUserId(userId);
        System.out.println("CartService: clearCart - userId=" + userId + ", cart=" + (cart != null ? cart.getCartId() : "null"));
        if (cart != null) {
            boolean result = cartRepository.clearCart(cart.getCartId());
            System.out.println("CartService: clearCart - result=" + result);
            return result;
        }
        System.out.println("CartService: clearCart - no cart found for userId=" + userId);
        return false;
    }
    
    @Override
    public double getCartTotal(int userId) {
        Cart cart = cartRepository.getCartByUserId(userId);
        if (cart != null) {
            return cartRepository.getCartTotal(cart.getCartId());
        }
        return 0.0;
    }

    @Override
    public Cart getCartByUserId(int userId) {
        return cartRepository.getCartByUserId(userId);
    }

    @Override
    public List<CartDetail> getCartDetails(int userId) {
        Cart cart = cartRepository.getCartByUserId(userId);
        if (cart != null) {
            return cartRepository.getCartItems(cart.getCartId());
        }
        return new ArrayList<>();
    }
}
