package model.service;

import db.DBConnection;
import model.entity.Order;
import model.entity.OrderProduct;
import model.repository.OrderRepository;
import model.repository.OrderRepositoryImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository = new OrderRepositoryImpl();

    @Override
    public Order placeOrderAndReturnFullInfo(int userId) {
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            double totalPrice = orderRepository.calculateCartTotal(conn, userId);
            if (totalPrice <= 0) {
                System.out.println("❌ Cart is empty.");
                return null;
            }

            Order order = new Order();
            order.setUserId(userId);
            order.setTotalPrice(totalPrice);
            order.setOrderCode(UUID.randomUUID().toString());

            int orderId = orderRepository.createOrder(conn, order);
            order.setId(orderId);

            orderRepository.insertOrderProducts(conn, orderId, userId);

            orderRepository.clearCart(conn, userId);

            List<OrderProduct> orderItems = orderRepository.getOrderProducts(conn, orderId);
            order.setOrderProducts(orderItems);

            conn.commit();
            return order;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Order> getOrderHistory(int userId) {
        try (Connection conn = DBConnection.getConnection()) {
            return orderRepository.getOrdersByUserId(conn, userId);
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
    }

}
