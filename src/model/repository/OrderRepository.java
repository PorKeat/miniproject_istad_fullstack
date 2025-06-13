package model.repository;

import model.entity.Order;
import model.entity.OrderProduct;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderRepository {
    int createOrder(Connection conn, Order order) throws SQLException;
    double calculateCartTotal(Connection conn, int userId) throws SQLException;
    void insertOrderProducts(Connection conn, int orderId, int userId) throws SQLException;
    void clearCart(Connection conn, int userId) throws SQLException;
    List<Order> getOrdersByUserId(Connection conn, int userId) throws SQLException;
    List<OrderProduct> getOrderProducts(Connection conn, int orderId) throws SQLException;
    List<Order> getOrderHistory(int userId);
}
