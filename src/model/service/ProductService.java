package model.service;

import model.entity.Product;

public interface ProductService {
    void addProduct(Product product, int categoryId) throws IllegalArgumentException;
}
