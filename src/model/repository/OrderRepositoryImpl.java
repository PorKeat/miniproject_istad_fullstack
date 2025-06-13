package model.repository;

import db.DBConnection;
import model.dto.OrderResponseDto;
import model.entity.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderRepositoryImpl implements OrderRepository {
    @Override
        public Order createOrder(Order order){
            String sql = "INSERT INTO orders (user_id, order_date, total_price, order_code) " +
                    "VALUES (?, ?, ?, ?)";

            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                stmt.setInt(1, order.getUserId());
                stmt.setDate(2, order.getOrderDate());
                stmt.setDouble(3, order.getTotalPrice());
                stmt.setString(4, order.getOrderCode());

                int affectedRows = stmt.executeUpdate();

                return affectedRows > 0 ? order : null;
            } catch (Exception e) {
                System.out.println("[!] Creating order failed, no rows affected." + e.getMessage());
            }
            return null;
        }

//        public Order findById(Integer id) throws SQLException {
//            String sql = "SELECT * FROM orders WHERE id = ?";
//
//            try (Connection conn = DatabaseConnection.getConnection();
//                 PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//                stmt.setInt(1, id);
//                ResultSet rs = stmt.executeQuery();
//
//                if (rs.next()) {
//                    return new Order(
//                            rs.getInt("id"),
//                            rs.getInt("user_id"),
//                            rs.getDate("order_date"),
//                            rs.getDouble("total_price"),
//                            rs.getString("order_code"),
//                            rs.getString("status")
//                    );
//                }
//                return null;
//            }
//        }
//
//        public List<Order> findByUserId(Integer userId) throws SQLException {
//            String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY order_date DESC";
//            List<Order> orders = new ArrayList<>();
//
//            try (Connection conn = DatabaseConnection.getConnection();
//                 PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//                stmt.setInt(1, userId);
//                ResultSet rs = stmt.executeQuery();
//
//                while (rs.next()) {
//                    orders.add(new Order(
//                            rs.getInt("id"),
//                            rs.getInt("user_id"),
//                            rs.getDate("order_date"),
//                            rs.getDouble("total_price"),
//                            rs.getString("order_code"),
//                            rs.getString("status")
//                    ));
//                }
//
//                return orders;
//            }
//        }

}

