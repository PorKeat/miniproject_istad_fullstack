package controller;

import lombok.AllArgsConstructor;
import model.entity.Category;
import model.entity.Product;
import model.entity.User;
import model.repository.CategoryRepository;
import model.repository.CategoryRepositoryImpl;
import model.service.ProductService;
import model.service.ProductServiceImpl;
import model.service.UserService;
import view.AuthView;
import util.CategoryPaginator;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

@AllArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthView view;
    private final ProductService productService = new ProductServiceImpl();
    private final Scanner scanner = new Scanner(System.in);




    public void handleSession() {
        while (true) {
            String current = userService.getCurrentUser();

            if (current != null && !current.isBlank()) {
                view.showAlreadyLoggedIn(current);

                if (view.promptLogout()) {
                    userService.logout();
                    view.showLogoutMessage();
                } else {
                    // Stay logged in → show dashboard
                    User user = userService.findByEmail(current); // You need this method
                    if (user != null) {
                        switch (user.getRole().toLowerCase()) {
                            case "admin" -> handleAdminTasks(user);
                            case "user" -> handleUserTasks(user);
                            default -> view.showError("Unknown role: " + user.getRole());
                        }
                    }
                }
            } else {
                switch (view.showMenu()) {
                    case 1 -> handleLogin();
                    case 2 -> handleRegistration();
                    default -> System.out.println("Invalid option.");
                }
            }
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
        System.out.println("\n== Admin Panel ==");
        System.out.println("1. Add Product");
        System.out.println("2. Log out");

        int choice = Integer.parseInt(scanner.nextLine());

        if (choice == 1) {
            CategoryRepository categoryRepo = new CategoryRepositoryImpl();
            List<Category> categories = categoryRepo.findAll();

            int categoryId = CategoryPaginator.paginateAndSelect(categories, scanner, 5);
            if (categoryId == -1) {
                view.showError("Cancelled adding product.");
                return;
            }

            String[] input = view.getProductInput();
            try {
                Product product = new Product(
                        null,
                        input[0],
                        Double.parseDouble(input[1]),
                        false,
                        UUID.randomUUID().toString()
                );
                productService.addProduct(product, categoryId);
                System.out.println("✅ Product added successfully.");
            } catch (Exception e) {
                view.showError("Failed to add product: " + e.getMessage());
            }


        } else {
            System.out.println("Returning to main menu...");
        }
    }


    private void handleUserTasks(User user) {
        System.out.println("Welcome to the user dashboard, " + user.getUserName());
        // user feature here
    }
}
