package model.service;

import util.ValidationUtil;
import model.entity.User;
import model.repository.UserRepository;
import model.repository.UserRepositoryImpl;
import util.HashUtil;

import java.io.*;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository = new UserRepositoryImpl();
    private static final String STATUS_FILE = "src/data/login_status.txt";

    @Override
    public void register(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }

        validateUserForRegistration(user);

        String hashedPassword = HashUtil.sha256(user.getPassword());
        user.setPassword(hashedPassword);

        userRepository.save(user);
    }

    @Override
    public User login(String email, String password) {
        ValidationUtil.validateEmail(email);
        ValidationUtil.validatePassword(password);

        String hashed = HashUtil.sha256(password);
        return userRepository.findByEmailAndPassword(email, hashed);
    }

    private void validateUserForRegistration(User user) {
        ValidationUtil.validateUsername(user.getUserName());
        ValidationUtil.validateEmail(user.getEmail());
        ValidationUtil.validatePassword(user.getPassword());

        if (userExists(user.getEmail())) {
            throw new IllegalArgumentException("Email already registered.");
        }
    }

    @Override
    public boolean userExists(String email) {
        ValidationUtil.validateEmail(email);
        return userRepository.existsByEmail(email);
    }

    @Override
    public void saveLoginStatus(String email) {
        ValidationUtil.validateEmail(email);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STATUS_FILE))) {
            writer.write(email);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save login status: " + e.getMessage());
        }
    }

    @Override
    public String getCurrentUser() {
        try (BufferedReader reader = new BufferedReader(new FileReader(STATUS_FILE))) {
            String line = reader.readLine();
            return (line != null && !line.isBlank()) ? line : null;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public void logout() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STATUS_FILE))) {
            writer.write("");
        } catch (IOException e) {
            throw new RuntimeException("Failed to logout: " + e.getMessage());
        }
    }

    @Override
    public User findByEmail(String email) {
        ValidationUtil.validateEmail(email);
        return userRepository.findByEmail(email);
    }
}

