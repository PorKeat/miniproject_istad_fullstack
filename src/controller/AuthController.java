package controller;

import lombok.AllArgsConstructor;
import model.dto.ProductResponseDto;
import model.entity.*;
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
    private final CartService cartService = new CartServiceImpl();
    private final OrderService orderService = new OrderServiceImpl();
    private final Scanner scanner = new Scanner(System.in);
    private static final String RESET = "\u001B[0m";
    private static final String CYAN = "\u001B[36m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String BOLD = "\u001B[1m";



    public void handleSession() {
        while (true) {
            String current = userService.getCurrentUser();
            if (current != null && !current.isBlank()) {
                User user = userService.findByEmail(current);

                if (user != null) {
                    view.showAlreadyLoggedIn(current);

                    switch (user.getRole().toLowerCase()) {
                        case "admin" -> handleAdminTasks(user);
                        case "user" -> handleUserTasks(user);
                        default -> view.showError("Unknown role: " + user.getRole());
                    }
                } else {
                    view.showError("Logged in user not found.");
                    userService.logout();
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

        if (creds[0].isEmpty() || creds[1].isEmpty()) {
            view.showError("Email and password cannot be empty.");
            return;
        }

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

        if (name.isEmpty() || creds[0].isEmpty() || creds[1].isEmpty()) {
            view.showError("Name, email, and password cannot be empty.");
            return;
        }

        User newUser = User.builder()
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
                if (categories.isEmpty()) {
                    view.showError("No categories available. Please add one first.");
                    break;
                }

                int selectedId = TablePaginator.categoryPaginateAndSelect(categories, scanner, 3);
                Category selected = categories.stream()
                        .filter(c -> c.getId() == selectedId)
                        .findFirst()
                        .orElse(null);

                if (selected == null) {
                    view.showError("Invalid category selected.");
                    break;
                }

                String[] input = view.getProductInput();
                if (input[0].isEmpty() || input[1].isEmpty()) {
                    view.showError("Product name and price cannot be empty.");
                    break;
                }

                try {
                    double price = Double.parseDouble(input[1]);
                    if (price < 0) throw new IllegalArgumentException("Price cannot be negative.");

                    Product product = new Product(
                            null, input[0], price, false, UUID.randomUUID().toString(), selected.getCategoryName());

                    productService.addProduct(product, selected.getId());
                    System.out.println("✅ Product added successfully.");
                } catch (NumberFormatException e) {
                    view.showError("Invalid price format.");
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
            case 7 -> {
                String uuid = view.getStringInput("UUID");
                if (uuid.isEmpty()) {
                    view.showError("UUID cannot be empty.");
                    break;
                }

                try {
                    productService.deleteProductByUuid(uuid);
                    System.out.println("✅ Product deleted.");
                } catch (Exception e) {
                    view.showError("Failed to delete product: " + e.getMessage());
                }
            }

            case 8 ->{
                try {
                    categoryService.deleteCategoryById(view.getIntegerInput("ID"));
                }catch (Exception e) {
                    view.showError("Failed to delete products: " + e.getMessage());
                }
            }
            case 10->{
                productService.insertStaticProducts(view.getIntegerInput("Amount"),1);
            }
            case 11->{
                productService.dropAllStaticProducts();
            }
            case 12->{
                userService.logout();
                view.showLogoutMessage();
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
                case 3 -> {
                    int productId = view.getIntegerInput("Product ID");
                    int qty = view.getIntegerInput("Qty");

                    if (productId <= 0 || qty <= 0) {
                        view.showError("Product ID and quantity must be positive.");
                        break;
                    }

                    try {
                        cartService.addToCart(Cart.builder()
                                .userId(user.getId())
                                .productId(productId)
                                .qty(qty)
                                .build());

                        System.out.println("✅ Product added to cart.");
                    } catch (Exception e) {
                        view.showError("Failed to add to cart: " + e.getMessage());
                    }
                }

                case 4 ->{
                    if (cartService.getUserCart(user.getId()).isEmpty()){
                        System.out.println("❌ Cart is empty");
                    }else {
                        TablePaginator.cartPaginateAndSelect(cartService.getUserCart(user.getId()),new Scanner(System.in),5);
                    }
                }
                case 5 -> {
                    Order order = orderService.placeOrderAndReturnFullInfo(user.getId());

                    if (order == null) {
                        System.out.println(RED + "❌ Order could not be placed — cart is empty or failed." + RESET);
                        return;
                    }

                    System.out.println(BLUE + "\n===================================" + RESET);
                    System.out.println(BOLD + "🧾 ORDER CONFIRMATION" + RESET);
                    System.out.println(BLUE + "===================================" + RESET);
                    System.out.printf(CYAN + "Order Code  : %s%n" + RESET, order.getOrderCode());
                    System.out.printf(CYAN + "Order Date  : %s%n" + RESET, new java.sql.Date(System.currentTimeMillis()));
                    System.out.printf(GREEN + "Total Price : $%.2f%n" + RESET, order.getTotalPrice());
                    System.out.println(BLUE + "-----------------------------------" + RESET);
                    System.out.println(YELLOW + "📦 Items Ordered:" + RESET);
                    System.out.printf(BOLD + "%-20s %-5s %-10s %-10s%n" + RESET, "Product", "Qty", "Unit Price", "Total");

                    for (OrderProduct item : order.getOrderProducts()) {
                        double total = item.getQty() * item.getProductPrice();
                        System.out.printf("%-20s %-5d $%-9.2f $%-9.2f%n",
                                item.getProductName(), item.getQty(), item.getProductPrice(), total);
                    }

                    System.out.println(BLUE + "===================================\n" + RESET);
                }

                case 6 -> {
                    List<Order> history = orderService.getOrderHistory(user.getId());

                    if (history.isEmpty()) {
                        System.out.println(RED + "📭 No orders found for this user." + RESET);
                    } else {
                        System.out.println(BLUE + "\n===================================" + RESET);
                        System.out.printf(BOLD + "📦 ORDER HISTORY for User #%s%n" + RESET, user.getUserName());
                        System.out.println(BLUE + "===================================" + RESET);

                        for (Order order : history) {
                            System.out.printf("\n" + GREEN + "🧾 Order Code : %s%n" + RESET, order.getOrderCode());
                            System.out.printf(CYAN + "   Date       : %s%n" + RESET, order.getOrderDate());
                            System.out.printf(GREEN + "   Total      : $%.2f%n" + RESET, order.getTotalPrice());
                            System.out.println(BLUE + "   --------------------------------------" + RESET);
                            System.out.println(YELLOW + "   Items:" + RESET);
                            System.out.printf(BOLD + "   %-20s %-5s %-10s %-10s%n" + RESET, "Product", "Qty", "Unit Price", "Total");

                            for (OrderProduct item : order.getOrderProducts()) {
                                double total = item.getQty() * item.getProductPrice();
                                System.out.printf("   %-20s %-5d $%-9.2f $%-9.2f%n",
                                        item.getProductName(), item.getQty(), item.getProductPrice(), total);
                            }
                        }

                        System.out.println(BLUE + "\n===================================\n" + RESET);
                    }
                }

                case 7->{
                    userService.logout();
                    view.showLogoutMessage();
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
