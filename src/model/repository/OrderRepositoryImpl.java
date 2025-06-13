package model.repository;

import db.DBConnection;
import model.entity.Order;
import model.entity.OrderProduct;
import model.repository.OrderRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


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

    @Override
    public List<OrderProduct> getOrderProducts(Connection conn, int orderId) throws SQLException {
        String sql = """
        SELECT op.product_id, p.p_name, p.price, op.qty
        FROM order_products op
        JOIN products p ON op.product_id = p.id
        WHERE op.order_id = ?
    """;

        List<OrderProduct> products = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OrderProduct product = new OrderProduct();
                product.setProductId(rs.getInt("product_id"));
                product.setProductName(rs.getString("p_name"));
                product.setProductPrice(rs.getDouble("price"));
                product.setQty(rs.getInt("qty"));
                products.add(product);
            }
        }
        return products;
    }

    @Override
    public List<Order> getOrdersByUserId(Connection conn, int userId) throws SQLException {
        String sql = """
        SELECT id, order_date, total_price, order_code
        FROM orders
        WHERE user_id = ?
        ORDER BY order_date DESC
    """;

        List<Order> orders = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setUserId(userId);
                order.setOrderDate(rs.getDate("order_date"));
                order.setTotalPrice(rs.getDouble("total_price"));
                order.setOrderCode(rs.getString("order_code"));

                // Fetch products for this order
                List<OrderProduct> products = getOrderProducts(conn, order.getId());
                order.setOrderProducts(products);

                orders.add(order);
            }
        }
        return orders;
    }

    @Override
    public List<Order> getOrderHistory(int userId) {
        try (Connection conn = DBConnection.getConnection()) {
            return getOrdersByUserId(conn, userId);
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
    }



}
