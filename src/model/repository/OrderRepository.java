package model.repository;

import model.entity.Order;

import java.sql.Connection;
import java.sql.SQLException;

public interface OrderRepository {
    int createOrder(Connection conn, Order order) throws SQLException;
    double calculateCartTotal(Connection conn, int userId) throws SQLException;
    void insertOrderProducts(Connection conn, int orderId, int userId) throws SQLException;
    void clearCart(Connection conn, int userId) throws SQLException;
}
