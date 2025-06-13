package model.service;

import model.entity.Cart;
import model.repository.CartRepositoryImpl;

import java.util.List;

public class CartServiceImpl implements CartService{
    private final CartRepositoryImpl cartRepository = new CartRepositoryImpl();

    @Override
    public void addToCart(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    public List<Cart> getUserCart(int userId) {
        return cartRepository.findByUserId(userId);
    }

    public void deleteFromCart(int userId) {
        cartRepository.clearCart(userId);
    }
}
