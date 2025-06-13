package model.repository;

import db.DBConnection;
import model.entity.OrderProduct;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class OrderProductRepositoryImpl implements OrderProductRepository {
    @Override
    public void createBatch(List<OrderProduct> orderProducts) {
        String sql = "INSERT INTO order_products (order_id, product_id, qty) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (OrderProduct op : orderProducts) {
                stmt.setInt(1, op.getOrderId());
                stmt.setInt(2, op.getProductId());
                stmt.setInt(3, op.getQty());
                stmt.addBatch();
            }

            stmt.executeBatch();
        } catch (Exception e) {
            System.out.println("[!] Error while batching order product creation: "+e.getMessage());
        }
    }


//    public List<OrderProduct> findByOrderId(Integer orderId) throws SQLException {
//        String sql = "SELECT * FROM order_products WHERE order_id = ?";
//        List<OrderProduct> orderProducts = new ArrayList<>();
//
//        try (Connection conn = DatabaseConnection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            stmt.setInt(1, orderId);
//            ResultSet rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                orderProducts.add(new OrderProduct(
//                        rs.getInt("order_id"),
//                        rs.getInt("product_id"),
//                        rs.getInt("qty")
//                ));
//            }
//
//            return orderProducts;
//        }
//    }
}
