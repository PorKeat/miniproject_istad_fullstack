package model.repository;

import model.entity.User;

public interface UserRepository {
    boolean existsByEmail(String email);
    User findByEmailAndPassword(String email, String hashedPassword);
    User findByEmail(String email);
    void save(User user);
}
