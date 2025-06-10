package controller;

import lombok.AllArgsConstructor;
import model.entity.User;
import model.service.UserService;
import view.AuthView;

import java.util.UUID;

@AllArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthView view;

    public void handleSession() {
        String current = userService.getCurrentUser();
        if (current != null && !current.isBlank()) {
            view.showAlreadyLoggedIn(current);
            if (view.promptLogout()) {
                userService.logout();
                view.showLogoutMessage();
            }
            return;
        }

        switch (view.showMenu()) {
            case 1 -> handleLogin();
            case 2 -> handleRegistration();
            default -> System.out.println("Invalid option.");
        }
    }

    private void handleLogin() {
        String[] creds = view.getLoginDetails();

        try {
            User user = userService.login(creds[0], creds[1]);

            if (user == null) {
                view.showError("Wrong credentials.");
                return;
            }

            userService.saveLoginStatus(user.getEmail());
            view.showWelcome(user.getUserName());

            switch (user.getRole().toLowerCase()) {
                case "admin" -> handleAdminTasks(user);
                case "user" -> handleUserTasks(user);
                default -> view.showError("Unknown role: " + user.getRole());
            }

        } catch (IllegalArgumentException ex) {
            view.showError(ex.getMessage());
        }
    }

    private void handleRegistration() {
        String[] creds = view.getRegistrationDetails();
        String name = view.getUsernamePrompt();

        User newUser = new User(
                null,
                name,
                creds[0],
                creds[1],
                false,
                UUID.randomUUID().toString(),
                "user"
        );

        try {
            userService.register(newUser);
            userService.saveLoginStatus(newUser.getEmail());
            view.showWelcome(newUser.getUserName());
        } catch (IllegalArgumentException ex) {
            view.showError(ex.getMessage());
        }
    }

    private void handleAdminTasks(User admin) {
        System.out.println("Welcome to the admin dashboard, " + admin.getUserName());
        // admin feature here
    }

    private void handleUserTasks(User user) {
        System.out.println("Welcome to the user dashboard, " + user.getUserName());
        // user feature here
    }
}
