package controller;

import mapper.SearchProductDAO;
import model.SearchProduct;

import java.sql.SQLException;
import java.util.List;

public class SearchProductController {
    private SearchProductDAO productDAO;

    public SearchProductController() {
        this.productDAO = new SearchProductDAO();
    }

    public List<SearchProduct> searchProducts(String namePattern, String category, int page) throws SQLException {
        if (page < 1) {
            throw new IllegalArgumentException("Page number must be 1 or higher");
        }
        return productDAO.searchByNameAndCategory(namePattern, category, page);
    }

    public void bulkInsertProducts(List<SearchProduct> products) throws SQLException {
        productDAO.bulkInsertProducts(products);
    }
}