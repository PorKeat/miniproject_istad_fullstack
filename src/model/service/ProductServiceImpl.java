package model.service;

import model.entity.Product;
import model.repository.ProductRepository;
import model.repository.ProductRepositoryImpl;

public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository = new ProductRepositoryImpl();

    @Override
    public void addProduct(Product product, int categoryId) {
        if (product.getName() == null || product.getName().isBlank())
            throw new IllegalArgumentException("Product name is required.");
        if (product.getPrice() < 0)
            throw new IllegalArgumentException("Price must be non-negative.");
        if (categoryId <= 0)
            throw new IllegalArgumentException("Invalid category ID.");

        productRepository.addProduct(product, categoryId);
    }
}
