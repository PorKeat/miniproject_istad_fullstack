package model.repository;

import model.entities.Product;
import util.DB_connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class ProductRepository {
    public Map<String, List<Product>> getProductsByCategory() {
        Map<String, List<Product>> productsByCategory = new LinkedHashMap<>();
        String query = """
                SELECT p.p_uuid, p.id, p.p_name, p.price, c.category_name AS category_name
                FROM products p
                JOIN product_categories pc ON p.id = pc.product_id
                JOIN categories c ON pc.category_id = c.id
                WHERE p.is_deleted = false
                """;

        try (Connection connection = DB_connection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(query)) {

            while (result.next()) {
                String category = result.getString("category_name");

                Product product = new Product(
                        UUID.randomUUID().toString(),
                        result.getInt("id"),
                        result.getString("p_name"),
                        result.getDouble("price"),
                        category
                );

                productsByCategory.computeIfAbsent(category, k -> new ArrayList<>()).add(product);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return productsByCategory;
    }
}
