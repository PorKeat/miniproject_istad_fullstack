package model.repository;

import db.DBConnection;
import model.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements Repository<Product>,ProductRepository {

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

    @Override
    public boolean deleteById(int id) {
        String sql = "UPDATE products SET is_deleted = true WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public List<Product> findAll() {
        List<Product> productList = new ArrayList<>();

        String sql = """ 
                    SELECT c.category_name, p.p_uuid, p.p_name, p.price
                    FROM categories c
                    JOIN product_categories pc ON c.id = pc.category_id
                    JOIN products p ON pc.product_id = p.id
                    WHERE p.is_deleted = false
                    ORDER BY c.category_name, p.p_name
                    """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                productList.add(Product.builder()
                        .uuid(rs.getString("p_uuid"))
                        .name(rs.getString("p_name"))
                        .price(rs.getDouble("price"))
                        .category(rs.getString("category_name"))
                        .build());
            }
            return productList;

        } catch (SQLException e) {
            System.out.println("Error fetching products by category: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Product> searchByName(String name) {
        String sql = """
                    SELECT p.id, p.p_name, p.price, p.is_deleted, p.p_uuid,
                           c.id AS category_id, c.category_name
                    FROM products p
                    LEFT JOIN product_categories pc ON p.id = pc.product_id
                    LEFT JOIN categories c ON c.id = pc.category_id
                    WHERE p.p_name LIKE ? AND p.is_deleted = false
                    ORDER BY p.id;
                    """;
        try(Connection conn = DBConnection.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,"%"+name+ "%");
            List<Product> productList = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    productList.add(new Product(
                            rs.getInt("id"),
                            rs.getString("p_name"),
                            rs.getDouble("price"),
                            rs.getBoolean("is_deleted"),
                            rs.getString("p_uuid"),
                            rs.getString("category_name")
                    ));
                }
            }
            return productList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Product findByUuid(String uuid) {
        String sql = """
                    SELECT p.id, p.p_name, p.price, p.is_deleted, p.p_uuid,
                           c.id AS category_id, c.category_name
                    FROM products p
                    LEFT JOIN product_categories pc ON p.id = pc.product_id
                    LEFT JOIN categories c ON c.id = pc.category_id
                    WHERE p.p_uuid = ? AND p.is_deleted = false
                    """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){
             ps.setString(1, uuid);
             try (ResultSet rs = ps.executeQuery()) {
                 if (rs.next()) {
                     return new Product(
                             rs.getInt("id"),
                             rs.getString("p_name"),
                             rs.getDouble("price"),
                             rs.getBoolean("is_deleted"),
                             rs.getString("p_uuid"),
                             rs.getString("category_name")
                     );
                 }
             }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
