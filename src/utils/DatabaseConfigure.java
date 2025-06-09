package utils;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

public class DatabaseConfigure {
    private static final String dbUrl = "jdbc:postgresql://ecommerce-istad-miniproject-alexkgm2412-1226.f.aivencloud.com:10132/defaultdb?sslmode=require";
    private static final String dbUser = "avnadmin";
    private static final String dbPass = "AVNS_c68zmWJiGuAGzeqLjcS";
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(dbUrl,dbUser,dbPass);
        } catch (Exception exception){
            System.err.println("‼️‼️ ERROR during get connection with database: "+exception.getMessage());
        }
        return null;
    }
}
