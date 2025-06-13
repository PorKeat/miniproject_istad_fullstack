package model.service;

import model.dto.ProductResponseDto;
import model.entity.Product;

import java.util.List;

public interface ProductService {
    void addProduct(Product product, int categoryId) throws IllegalArgumentException;
    boolean deleteProductByUuid(String uuid);
    List<ProductResponseDto> getAllProducts();
    List<ProductResponseDto> searchProductByName(String name);
    void insertStaticProducts(int numberOfProducts, int categoryId);
    void dropAllStaticProducts();
}
