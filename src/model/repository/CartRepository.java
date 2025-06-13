package model.repository;
import model.entity.Cart;

import java.util.List;

public interface CartRepository {
    void save(Cart cart);
    List<Cart> findByUserId(int userId);
}
