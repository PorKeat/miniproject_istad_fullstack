package model.service;

import View.ValidationUtil;
import model.entity.User;
import model.repository.UserRepository;
import model.repository.UserRepositoryImpl;
import util.HashUtil;

import java.io.*;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository = new UserRepositoryImpl();
    private static final String STATUS_FILE = "C:\\Users\\AlexKGM\\OneDrive\\Desktop\\JavaMIniProject\\MiniProject\\src\\data\\login_status.txt";

    @Override
    public void register(User user) {
        // Validation
        validateUserForRegistration(user);

        // Hash password
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
        return userRepository.existsByEmail(email);
    }

    @Override
    public void saveLoginStatus(String email) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STATUS_FILE))) {
            writer.write(email);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String getCurrentUser() {
        try (BufferedReader reader = new BufferedReader(new FileReader(STATUS_FILE))) {
            return reader.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public void logout() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STATUS_FILE))) {
            writer.write("");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
