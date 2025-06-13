package controller;

import lombok.AllArgsConstructor;
import model.dto.ProductResponseDto;
import model.entity.Category;
import model.entity.Product;
import model.entity.User;
import model.service.*;
import View.View;
import util.TablePaginator;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;


@AllArgsConstructor
public class AuthController {
    private final UserService userService;
    private final View view;
    private final ProductService productService = new ProductServiceImpl();
    private final CategoryService categoryService = new CategoryServiceImpl();
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
        String[] creds = view.getAuth();

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
        String[] creds = view.getAuth();
        String name = view.getStringInput("Name");

        User newUser = User
                .builder()
                .userName(name)
                .email(creds[0])
                .password(creds[1])
                .uUuid(UUID.randomUUID().toString())
                .role("user")
                .build();

        try {
            userService.register(newUser);
            userService.saveLoginStatus(newUser.getEmail());
            view.showWelcome(newUser.getUserName());
        } catch (IllegalArgumentException ex) {
            view.showError(ex.getMessage());
        }
    }

    private void handleAdminTasks(User admin) {
        switch (view.adminMenu()) {
            case 1 -> {
                List<Category> categories = categoryService.getAllCategories();
                int selectedId = TablePaginator.categoryPaginateAndSelect(categories, scanner, 3);
                Category selected = categories.stream()
                        .filter(c -> c.getId() == selectedId)
                        .findFirst()
                        .orElse(null);

                if (selected == null) {
                    System.out.println("❌ Category creation cancelled.");
                    break;
                }

                int categoryId = selected.getId();
                String[] input = view.getProductInput();

                try {
                    Product product = new Product(
                            null,
                            input[0],
                            Double.parseDouble(input[1]),
                            false,
                            UUID.randomUUID().toString()
                            ,selected.getCategoryName()
                    );
                    productService.addProduct(product, categoryId);
                    System.out.println("✅ Product added successfully.");
                } catch (Exception e) {
                    view.showError("Failed to add product: " + e.getMessage());
                }
            }

            case 2 -> {
                try {
                    String name = view.getStringInput("Category Name");
                    categoryService.addCategory(name.toUpperCase());
                    System.out.println("✅ Category added successfully.");
                } catch (Exception e) {
                    view.showError("Failed to add category: " + e.getMessage());
                }
            }

            case 3 -> {
                try {
                    List<ProductResponseDto> products = productService.getAllProducts();
                    if (products.isEmpty()) {
                        System.out.println("No products found.");
                    } else {
                        TablePaginator.productPaginateAndSelect(products, new Scanner(System.in), 5);
                    }
                } catch (Exception e) {
                    view.showError("Failed to fetch products: " + e.getMessage());
                }
            }
            case 4 -> {
                try {
                    List<Category> categories = categoryService.getAllCategories();
                    if (categories.isEmpty()) {
                        System.out.println("No products found.");
                    } else {
                        TablePaginator.categoryPaginateAndSelect(categories, new Scanner(System.in), 5);
                    }
                } catch (Exception e) {
                    view.showError("Failed to fetch products: " + e.getMessage());
                }
            }
            case 5->{
                try {
                    List<ProductResponseDto> serachResult = productService.searchProductByName(view.getStringInput("Product Name"));
                    if (serachResult.isEmpty()) {
                        System.out.println("No products found.");
                    } else {
                        TablePaginator.productPaginateAndSelect(serachResult, new Scanner(System.in), 5);
                    }
                } catch (Exception e) {
                    view.showError("Failed to fetch products: " + e.getMessage());
                }
            }
            case 6->{
                try {
                    List<Category> serachResult = categoryService.searchCategoryByName(view.getStringInput("Product Name").toUpperCase());
                    if (serachResult.isEmpty()) {
                        System.out.println("No products found.");
                    } else {
                        TablePaginator.categoryPaginateAndSelect(serachResult, new Scanner(System.in), 5);
                    }
                } catch (Exception e) {
                    view.showError("Failed to fetch products: " + e.getMessage());
                }
            }
            case 7->{
                try {
                    productService.deleteProductByUuid(view.getStringInput("UUID"));
                }catch (Exception e) {
                    view.showError("Failed to delete products: " + e.getMessage());
                }
            }
            case 8 ->{
                try {
                    categoryService.deleteCategoryById(view.getIntegerInput("ID"));
                }catch (Exception e) {
                    view.showError("Failed to delete products: " + e.getMessage());
                }
            }
            default -> System.out.println("Invalid menu option.");
        }
    }


    private void handleUserTasks(User user) {
        try{
            System.out.println("Welcome to our system, " + user.getUserName());
            switch (view.userMenu()){
                case 1 ->{
                    List<ProductResponseDto> products = productService.getAllProducts();
                    if (products.isEmpty()) {
                        System.out.println("No products found.");
                    } else {
                            TablePaginator.productPaginateAndSelect(products, new Scanner(System.in), 5);
                    }
                }
                case 2 ->{
                    List<ProductResponseDto> serachResult = productService.searchProductByName(view.getStringInput("Product Name"));
                    if (serachResult.isEmpty()) {
                        System.out.println("No products found.");
                    } else {
                        TablePaginator.productPaginateAndSelect(serachResult, new Scanner(System.in), 5);
                    }
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
