package model.repository;

import model.dto.UserLoginDto;
import model.entities.User;
import utils.DatabaseConfigure;
import utils.PassWordManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements Repository<User , Integer>{

    @Override
    public User save(User user) {
        try (Connection con = DatabaseConfigure.getConnection()){
            String insert = """
                    INSERT INTO users(uuid, username, password, email, is_deleted) VALUES(?,?,?,?,?)
                    """;
            PreparedStatement ps = con.prepareStatement(insert);
            String hashedPassword = PassWordManager.hashPassWord(user.getPassword());
            ps.setString(1, user.getUuid());
            ps.setString(2, user.getUsername());
            ps.setString(3, hashedPassword);
            ps.setString(4, user.getEmail());
            ps.setBoolean(5, user.getIsDeleted() != null ? user.getIsDeleted() : false);
            ps.executeUpdate();
            System.out.println("UserPass : "+hashedPassword);
        }catch (Exception e){
            System.out.println("Error while saving user");
            return null;
        }
        return user;
    }
    public User login(String email, String password) {
        try(Connection con = DatabaseConfigure.getConnection()){
            String sql = """
                    SELECT * FROM users WHERE email = ? AND password = ?
                    """;
            PreparedStatement ps = con.prepareStatement(sql);
            String hashedPassword = PassWordManager.hashPassWord(password);
            ps.setString(1, email);
            ps.setString(2, hashedPassword);
            System.out.println("Passsssssss"+ hashedPassword);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUuid(rs.getString("uuid"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setIsDeleted(rs.getBoolean("is_deleted"));
                    return user;
                } else {
                    return null;
                }
            }
        }catch (Exception e){
            System.out.println("Error while login" + e.getMessage());
        }
        return null;
    }
    @Override
    public Integer delete(Integer id) {
        try(Connection con = DatabaseConfigure.getConnection()) {
            String sql = """
                    DELETE FROM users WHERE id = ?
                    """;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1 , id);
            ps.executeUpdate();
        }catch (Exception e){}

        return 0;
    }
}
