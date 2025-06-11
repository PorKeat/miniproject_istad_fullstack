package controller;

import model.entities.Product;
import model.repository.ProductRepository;
import view.TableUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ProductController {
    ProductRepository productRepository = new ProductRepository();
    public void displayProducts() {
        Map<String, List<Product>> productsByCategory = productRepository.getProductsByCategory();
        List<Product> product = new ArrayList<>();
        for (Map.Entry<String, List<Product>> entry : productsByCategory.entrySet()){
            product.addAll(entry.getValue());
        }

        TableUI tableUI = new TableUI();
        tableUI.displayTable(product);

//        for (String category : productsByCategory.keySet()) {
//            System.out.println("." + category.toUpperCase());
//
//            // Print table header
//            System.out.println("-".repeat(75));
//            System.out.printf("%-36s %-20s %-10s %-5s%n", "UUID", "Product Name", "Price", "QTY");
//            System.out.println("-".repeat(75));
//
//            // Print product rows
//            for (Product product : productsByCategory.get(category)) {
//                System.out.println(product);
//            }
//            System.out.println();
//        }
    }
}
