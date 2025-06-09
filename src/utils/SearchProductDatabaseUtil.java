package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class SearchProductDatabaseUtil {
    private static final Logger logger = Logger.getLogger(SearchProductDatabaseUtil.class.getName());
    private static final String DB_URL = "jdbc:postgresql://ecommerce-istad-miniproject-alexkgm2412-1226.f.aivencloud.com:10132/defaultdb?sslmode=require&connectTimeout=10&socketTimeout=30";
    private static final String USER = "avnadmin";
    private static final String PASSWORD = "AVNS_c68zmWJiGuAGzeqLjcS";
    private static Connection conn;

    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            System.out.println("Loading...");
            // Temporarily disable logger output
            Logger.getLogger("").setLevel(java.util.logging.Level.OFF);

            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            initializeDatabase();

            // Re-enable logger output
            Logger.getLogger("").setLevel(java.util.logging.Level.INFO);
        }
        return conn;
    }

    private static void initializeDatabase() throws SQLException {
        String createIndex = "CREATE INDEX IF NOT EXISTS idx_products_p_name_search ON products USING GIN(to_tsvector('english', p_name))";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createIndex);
            logger.info("Index for full-text search created or already exists.");
        } catch (SQLException e) {
            logger.severe("Failed to create index on products table: " + e.getMessage());
            throw e;
        }
    }

    public static void closeConnection() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
            logger.info("Database connection closed.");
        }
    }
}