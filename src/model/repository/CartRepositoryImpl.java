package model.repository;

import db.DBConnection;
import model.entity.Cart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartRepositoryImpl implements CartRepository {

    @Override
    public void save(Cart cart) {
        String sql = """
            INSERT INTO carts (user_id, product_id, qty, added_at)
            VALUES (?, ?, ?, CURRENT_DATE)
            ON CONFLICT (product_id, user_id)
            DO UPDATE SET qty = carts.qty + EXCLUDED.qty, added_at = CURRENT_DATE
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cart.getUserId());
            ps.setInt(2, cart.getProductId());
            ps.setInt(3, cart.getQty());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error saving cart: " + e.getMessage());
        }
    }

    @Override
    public List<Cart> findByUserId(int userId) {
        List<Cart> cartItems = new ArrayList<>();
        String sql = """
                    SELECT c.user_id, c.product_id, c.qty, c.added_at, p.p_name, p.price, u.user_name
                    FROM carts c
                    JOIN products p ON c.product_id = p.id
                    JOIN users u ON c.user_id = u.id
                    WHERE c.user_id = ?
                    """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                cartItems.add(new Cart(
                        rs.getInt("user_id"),
                        rs.getInt("product_id"),
                        rs.getInt("qty"),
                        rs.getDate("added_at"),
                        rs.getString("p_name"),
                        rs.getString("user_name"),
                        rs.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving cart items: " + e.getMessage());
        }

        return cartItems;
    }


}
