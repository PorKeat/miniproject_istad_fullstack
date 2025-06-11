package model.service;

import model.entity.User;

public interface UserService {
    void register(User user) throws IllegalArgumentException;
    User login(String email, String password) throws IllegalArgumentException;
    boolean userExists(String email);
    void saveLoginStatus(String email);
    String getCurrentUser();
    void logout();
    User findByEmail(String email);
}
