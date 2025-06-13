package model.repository;

import model.entity.Order;
import model.repository.OrderRepository;
import java.sql.*;


public class OrderRepositoryImpl implements OrderRepository {

    @Override
    public int createOrder(Connection conn, Order order) throws SQLException {
        String sql = """
            INSERT INTO orders (user_id, order_date, total_price, order_code)
            VALUES (?, CURRENT_DATE, ?, ?)
            RETURNING id
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, order.getUserId());
            stmt.setDouble(2, order.getTotalPrice());
            stmt.setString(3, order.getOrderCode());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
            throw new SQLException("Order creation failed");
        }
    }

    @Override
    public double calculateCartTotal(Connection conn, int userId) throws SQLException {
        String sql = """
            SELECT SUM(p.price * c.qty)
            FROM carts c
            JOIN products p ON c.product_id = p.id
            WHERE c.user_id = ?
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
            return 0;
        }
    }

    @Override
    public void insertOrderProducts(Connection conn, int orderId, int userId) throws SQLException {
        String sql = """
            INSERT INTO order_products (order_id, product_id, qty)
            SELECT ?, product_id, qty FROM carts WHERE user_id = ?
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        }
    }

    @Override
    public void clearCart(Connection conn, int userId) throws SQLException {
        String sql = "DELETE FROM carts WHERE user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }
}
