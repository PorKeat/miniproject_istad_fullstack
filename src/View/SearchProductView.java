package View;

import model.SearchProduct;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SearchProductView {
    private Scanner scanner;

    public SearchProductView() {
        this.scanner = new Scanner(System.in);
    }

    public String[] getSearchCriteria() {
        System.out.print("Enter product name or part of name (e.g., 'coca' for Coca Cola, or press Enter for all): ");
        String namePattern = scanner.nextLine().trim();
        System.out.print("Enter category (or press Enter for all categories): ");
        String category = scanner.nextLine().trim();
        return new String[]{namePattern, category.isEmpty() ? null : category};
    }

    public int getPageNumber() {
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

    public void displayProducts(List<SearchProduct> products, int page) {
        if (products.isEmpty()) {
            System.out.println("No products found on page " + page + ".");
            return;
        }

        Map<String, List<SearchProduct>> categorized = products.stream()
                .collect(Collectors.groupingBy(p -> "N/A")); // Placeholder, no category

        System.out.println("\nPage " + page + ":");
        for (Map.Entry<String, List<SearchProduct>> entry : categorized.entrySet()) {
            System.out.println("\nCategory: " + entry.getKey());
            for (SearchProduct product : entry.getValue()) {
                System.out.println(product);
            }
        }
        System.out.println("\nFound " + products.size() + " products on page " + page + ".");
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showError(String message) {
        System.out.println("Error: " + message);
    }

    public int getMenuChoice() {
        while (true) {
            try {
                System.out.println("\n1. Search Products\n2. Bulk Insert Test (10M Products)\n3. Exit");
                System.out.print("Choose an option: ");
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}