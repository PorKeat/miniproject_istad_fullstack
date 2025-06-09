import View.SearchProductView;
import controller.SearchProductController;
import model.SearchProduct;
import utils.SearchProductDatabaseUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SearchProductController controller = new SearchProductController();
        SearchProductView view = new SearchProductView();

        view.showMessage("E-Commerce Search System");
        while (true) {
            int choice = view.getMenuChoice();
            if (choice == 3) {
                view.showMessage("Exiting...");
                break;
            } else if (choice == 1) {
                String[] criteria = view.getSearchCriteria();
                String namePattern = criteria[0];
                String category = criteria[1];
                int page = 1;
                while (page > 0) {
                    try {
                        List<SearchProduct> products = controller.searchProducts(namePattern, category, page);
                        view.displayProducts(products, page);
                        page = view.getPageNumber();
                    } catch (SQLException e) {
                        view.showError("Database error: " + e.getMessage());
                        break;
                    } catch (IllegalArgumentException e) {
                        view.showError(e.getMessage());
                        break;
                    }
                }
            } else if (choice == 2) {
                List<SearchProduct> products = new ArrayList<>();
                for (int i = 0; i < 10_000_000; i++) {
                    products.add(new SearchProduct(
                            "Product" + i + (i % 2 == 0 ? " Coca" : ""),
                            10.0 + (i % 100),
                            100,
                            false,
                            java.util.UUID.randomUUID().toString()
                    ));
                }
                try {
                    long start = System.currentTimeMillis();
                    controller.bulkInsertProducts(products);
                    long end = System.currentTimeMillis();
                    view.showMessage("Inserted 10M products in " + (end - start) + "ms");
                } catch (SQLException e) {
                    view.showError("Bulk insert error: " + e.getMessage());
                }
            } else {
                view.showError("Invalid option. Please try again.");
            }
        }

        try {
            SearchProductDatabaseUtil.closeConnection();
        } catch (SQLException e) {
            view.showError("Error closing database: " + e.getMessage());
        }
    }
}