package model.repository;

import model.entity.Product;

public interface ProductRepository {
    void addProduct(Product product, int categoryId);
    Product findByUuid(String uuid);
    void insertStaticProducts(int numberOfProducts, int categoryId);
    void dropAllStaticProducts();
}
