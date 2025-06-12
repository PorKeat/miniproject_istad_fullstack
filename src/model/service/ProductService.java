package model.service;

import model.dto.ProductResponDto;

import java.util.List;

public interface ProductService {
    ProductResponDto addToCart(String UUID);
    List<ProductResponDto> getCartProducts();
}
