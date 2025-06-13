package model.service;

import db.DBConnection;
import model.entity.Order;
import model.repository.OrderRepository;
import model.repository.OrderRepositoryImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository = new OrderRepositoryImpl();

    public Order placeOrder(int userId) {
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            double total = orderRepository.calculateCartTotal(conn, userId);
            if (total == 0) {
                System.out.println("❌ Cart is empty.");
                return null;
            }

            Order order = new Order();
            order.setUserId(userId);
            order.setTotalPrice(total);
            order.setOrderCode(UUID.randomUUID().toString());

            int orderId = orderRepository.createOrder(conn, order);
            order.setId(orderId);

            orderRepository.insertOrderProducts(conn, orderId, userId);
            orderRepository.clearCart(conn, userId);

            conn.commit();
            System.out.println("✅ Order placed successfully.");
            return order;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
