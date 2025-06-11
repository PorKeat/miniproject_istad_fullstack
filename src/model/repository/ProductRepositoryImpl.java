package model.repository;

import db.DBConnection;
import model.entity.Product;

import java.sql.*;

public class ProductRepositoryImpl implements ProductRepository {

    @Override
    public void addProduct(Product product, int categoryId) {
        String insertProductSQL = "INSERT INTO products (p_name, price, p_uuid, is_deleted) VALUES (?, ?, ?, ?) RETURNING id";
        String insertProductCategorySQL = "INSERT INTO product_categories (product_id, category_id) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (
                    PreparedStatement ps1 = conn.prepareStatement(insertProductSQL);
                    PreparedStatement ps2 = conn.prepareStatement(insertProductCategorySQL)
            ) {
                ps1.setString(1, product.getName());
                ps1.setDouble(2, product.getPrice());
                ps1.setString(3, product.getUuid());
                ps1.setBoolean(4, false);

                ResultSet rs = ps1.executeQuery();
                if (rs.next()) {
                    int productId = rs.getInt("id");

                    ps2.setInt(1, productId);
                    ps2.setInt(2, categoryId);
                    ps2.executeUpdate();

                    conn.commit();
                    System.out.println("Product added successfully.");
                } else {
                    conn.rollback();
                    System.out.println("Failed to get product ID.");
                }
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.out.println("DB Error: " + e.getMessage());
        }
    }
}
