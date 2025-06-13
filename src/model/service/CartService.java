package model.service;

import model.entity.Cart;

import java.util.List;

public interface CartService {
    void addToCart(Cart cart);
    List<Cart> getUserCart(int userId);
}
