package mapper;

import model.SearchProduct;
import utils.SearchProductDatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SearchProductDAO {
    private static final int PAGE_SIZE = 100;

    public List<SearchProduct> searchByName(String pattern, int page) throws SQLException {
        if (pattern == null || pattern.trim().isEmpty()) {
            return getAllProducts(page);
        }
        List<SearchProduct> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE to_tsvector('english', p_name) @@ to_tsquery(?) AND is_deleted = false LIMIT ? OFFSET ?";
        try (Connection conn = SearchProductDatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pattern.trim() + ":*");
            pstmt.setInt(2, PAGE_SIZE);
            pstmt.setInt(3, (page - 1) * PAGE_SIZE);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    products.add(new SearchProduct(
                            rs.getString("p_name"),
                            rs.getDouble("price"),
                            rs.getInt("qty"),
                            rs.getBoolean("is_deleted"),
                            rs.getString("p_uuid")
                    ));
                }
            }
        }
        return products;
    }

    public List<SearchProduct> searchByCategory(String category, int page) throws SQLException {
        // Placeholder since category is not in products table
        if (category == null || category.trim().isEmpty()) {
            return getAllProducts(page);
        }
        return new ArrayList<>(); // To be implemented with a join if category exists elsewhere
    }

    public List<SearchProduct> searchByNameAndCategory(String pattern, String category, int page) throws SQLException {
        if (pattern == null || pattern.trim().isEmpty()) {
            return searchByCategory(category, page);
        }
        if (category == null || category.trim().isEmpty()) {
            return searchByName(pattern, page);
        }
        return searchByName(pattern, page); // Placeholder, adjust if category data exists
    }

    public List<SearchProduct> getAllProducts(int page) throws SQLException {
        List<SearchProduct> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE is_deleted = false LIMIT ? OFFSET ?";
        try (Connection conn = SearchProductDatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, PAGE_SIZE);
            pstmt.setInt(2, (page - 1) * PAGE_SIZE);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    products.add(new SearchProduct(
                            rs.getString("p_name"),
                            rs.getDouble("price"),
                            rs.getInt("qty"),
                            rs.getBoolean("is_deleted"),
                            rs.getString("p_uuid")
                    ));
                }
            }
        }
        return products;
    }

    public void insertProduct(SearchProduct product) throws SQLException {
        String sql = "INSERT INTO products (p_name, price, qty, is_deleted, p_uuid) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = SearchProductDatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, product.getP_name());
            pstmt.setDouble(2, product.getPrice());
            pstmt.setInt(3, product.getQty());
            pstmt.setBoolean(4, product.isIs_deleted());
            pstmt.setString(5, product.getP_uuid());
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    product.id = rs.getInt(1); // Set auto-incremented ID
                }
            }
        }
    }

    public void bulkInsertProducts(List<SearchProduct> products) throws SQLException {
        String sql = "INSERT INTO products (p_name, price, qty, is_deleted, p_uuid) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = SearchProductDatabaseUtil.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                for (SearchProduct product : products) {
                    pstmt.setString(1, product.getP_name());
                    pstmt.setDouble(2, product.getPrice());
                    pstmt.setInt(3, product.getQty());
                    pstmt.setBoolean(4, product.isIs_deleted());
                    pstmt.setString(5, product.getP_uuid());
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }
}