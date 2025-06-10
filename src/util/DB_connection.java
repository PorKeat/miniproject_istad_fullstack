package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB_connection {
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:postgresql://ecommerce-istad-miniproject-alexkgm2412-1226.f.aivencloud.com:10132/defaultdb?sslmode=require","avnadmin","AVNS_c68zmWJiGuAGzeqLjcS"
            );
        }catch (Exception exception){
            throw new RuntimeException(exception);
        }
    }
}
