package controller;
import model.entities.Product;
import model.repository.ProductRepository;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProductController {

    ProductRepository productRepository  = new ProductRepository();
    public void displayProducts() {
        Map<String, List<Product>> productsByCategory = productRepository.getProductsByCategory();
        for (String category : productsByCategory.keySet()) {
            System.out.println("=== " + category.toUpperCase() + " ===");
            for (Product product : productsByCategory.get(category)) {
                System.out.println(product);
            }
            System.out.println();
        }
    }
}
