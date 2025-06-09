package View;

import controller.SearchProductController;
import model.SearchProduct;
import java.util.List;
import java.util.Scanner;

public class SearchProductView {
    private final Scanner scanner;
    private final SearchProductController controller;

    public SearchProductView(SearchProductController controller) {
        this.scanner = new Scanner(System.in);
        this.controller = controller;
    }

    public void startApplication() {
        showMessage("E-Commerce Search System");
        while (true) {
            showMenu();
            int choice = getMenuChoice();

            try {
                switch (choice) {
                    case 1 -> handleSearchProducts();
                    case 2 -> handleReadAllProducts();
                    case 3 -> {
                        showMessage("Exiting...");
                        return;
                    }
                    default -> showError("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                showError("Error: " + e.getMessage());
            }
        }
    }

    private void handleSearchProducts() throws Exception {
        String[] criteria = getSearchCriteria();
        String namePattern = criteria[0];
        String category = criteria[1];
        int page = 1;

        while (page > 0) {
            List<SearchProduct> products = controller.searchProducts(namePattern, category, page);
            displayProducts(products, page);
            page = getPageNumber();
        }
    }

    private void handleReadAllProducts() throws Exception {
        showMessage("Starting to read all products...");
        long startTime = System.currentTimeMillis();

        List<SearchProduct> products = controller.readAllProducts();

        long endTime = System.currentTimeMillis();
        showMessage(String.format("Read %,d products in %d ms",
                products.size(), (endTime - startTime)));
    }

    private String[] getSearchCriteria() {
        System.out.print("Enter product name or part of name (press Enter for all): ");
        String namePattern = scanner.nextLine().trim();
        System.out.print("Enter category (press Enter for all categories): ");
        String category = scanner.nextLine().trim();
        return new String[]{namePattern.isEmpty() ? null : namePattern,
                category.isEmpty() ? null : category};
    }

    private int getPageNumber() {
        while (true) {
            try {
                System.out.print("Enter page number (1 or higher, 0 to stop): ");
                int page = Integer.parseInt(scanner.nextLine());
                if (page >= 0) return page;
                System.out.println("Please enter a non-negative number.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private void displayProducts(List<SearchProduct> products, int page) {
        if (products.isEmpty()) {
            System.out.println("No products found on page " + page + ".");
            return;
        }

        System.out.println("\nPage " + page + ":");
        products.forEach(System.out::println);
        System.out.println("\nFound " + products.size() + " products on page " + page + ".");
    }

    private void showMenu() {
        System.out.println("\n1. Search Products");
        System.out.println("2. Read All Products (Performance Test)");
        System.out.println("3. Exit");
    }

    private int getMenuChoice() {
        while (true) {
            try {
                System.out.print("Choose an option: ");
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number (1-3).");
            }
        }
    }

    private void showMessage(String message) {
        System.out.println(message);
    }

    private void showError(String message) {
        System.err.println("Error: " + message);
    }
}