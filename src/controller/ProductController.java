package controller;

import model.entities.Product;
import model.repository.ProductRepository;
import java.util.List;
import java.util.Map;

public class ProductController {
    ProductRepository productRepository = new ProductRepository();
    public void displayProducts() {
        Map<String, List<Product>> productsByCategory = productRepository.getProductsByCategory();
        for (String category : productsByCategory.keySet()) {
            System.out.println("." + category.toUpperCase());

            // Print table header
            System.out.println("-".repeat(75));
            System.out.printf("%-36s %-20s %-10s %-5s%n", "UUID", "Product Name", "Price", "QTY");
            System.out.println("-".repeat(75));

            // Print product rows
            for (Product product : productsByCategory.get(category)) {
                System.out.println(product);
            }
            System.out.println();
        }
    }
}
