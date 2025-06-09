package mapper;

import model.SearchProduct;
import utils.SearchProductDatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SearchProductDAO {
    private static final int PAGE_SIZE = 100;

    public List<SearchProduct> searchByNameAndCategory(String pattern, String category, int page) throws SQLException {
        if (pattern == null && category == null) {
            return getAllProducts(page);
        }

        String sql = "SELECT * FROM products WHERE is_deleted = false";
        List<Object> params = new ArrayList<>();

        if (pattern != null) {
            sql += " AND to_tsvector('english', p_name) @@ to_tsquery(?)";
            params.add(pattern + ":*");
        }

        sql += " LIMIT ? OFFSET ?";
        params.add(PAGE_SIZE);
        params.add((page - 1) * PAGE_SIZE);

        try (Connection conn = SearchProductDatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }

            return executeQuery(pstmt);
        }
    }

    private List<SearchProduct> executeQuery(PreparedStatement pstmt) throws SQLException {
        try (ResultSet rs = pstmt.executeQuery()) {
            return mapResultSetToProducts(rs);
        }
    }

    private List<SearchProduct> getAllProducts(int page) throws SQLException {
        String sql = "SELECT * FROM products WHERE is_deleted = false LIMIT ? OFFSET ?";
        try (Connection conn = SearchProductDatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, PAGE_SIZE);
            pstmt.setInt(2, (page - 1) * PAGE_SIZE);
            return executeQuery(pstmt);
        }
    }

    private List<SearchProduct> mapResultSetToProducts(ResultSet rs) throws SQLException {
        List<SearchProduct> products = new ArrayList<>();
        while (rs.next()) {
            SearchProduct product = new SearchProduct(
                    rs.getString("p_name"),
                    rs.getDouble("price"),
                    rs.getBoolean("is_deleted"),
                    rs.getString("p_uuid")
            );
            product.id = rs.getInt("id");
            products.add(product);
        }
        return products;
    }

    public List<SearchProduct> readAllProducts() throws SQLException {
        String sql = "SELECT * FROM products WHERE is_deleted = false";
        try (Connection conn = SearchProductDatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            return mapResultSetToProducts(rs);
        }
    }
}