package model.repository;

import db.DBConnection;
import model.entity.User;

import java.sql.*;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT 1 FROM users WHERE email = ? AND is_deleted = false";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            return ps.executeQuery().next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public User findByEmailAndPassword(String email, String hashedPassword) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ? AND is_deleted = false";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, hashedPassword);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("user_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getBoolean("is_deleted"),
                        rs.getString("u_uuid"),
                        rs.getString("role")
                );
            }
        } catch (SQLException e) {
            System.out.println("Login failed: " + e.getMessage());
        }

        return null;
    }

    @Override
    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ? AND is_deleted = false";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("user_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getBoolean("is_deleted"),
                        rs.getString("u_uuid"),
                        rs.getString("role")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error fetching user by email: " + e.getMessage());
        }
        return null;
    }



    @Override
    public void save(User user) {
        String sql = "INSERT INTO users (user_name, email, password, u_uuid, role) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getUUuid());
            ps.setString(5, user.getRole());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
