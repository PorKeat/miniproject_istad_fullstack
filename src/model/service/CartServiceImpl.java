package model.service;

import model.entity.Cart;
import model.repository.CartRepositoryImpl;

import java.util.List;

public class CartServiceImpl implements CartService{
    private final CartRepositoryImpl cartRepository = new CartRepositoryImpl();

    @Override
    public void addToCart(Cart cart) {
        if (cart == null) {
            throw new IllegalArgumentException("Cart cannot be null.");
        }
        if (cart.getUserId() <= 0) {
            throw new IllegalArgumentException("Invalid user ID.");
        }
        if (cart.getProductId() <= 0) {
            throw new IllegalArgumentException("Invalid product ID.");
        }
        if (cart.getQty() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        cartRepository.save(cart);
    }

    @Override
    public List<Cart> getUserCart(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID.");
        }

        try {
            return cartRepository.findByUserId(userId);
        } catch (Exception e) {
            System.out.println("[!] Failed to fetch cart: " + e.getMessage());
            return null; // Return safe empty list
        }
    }

}
