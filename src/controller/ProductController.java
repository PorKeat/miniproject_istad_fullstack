package controller;

import model.dto.ProductResponDto;
import model.service.ProductServiceImpl;

import java.util.List;

public class ProductController {
    private final ProductServiceImpl productServiceImpl = new ProductServiceImpl();
    public ProductResponDto addProductToCart(String UUID) {
        return productServiceImpl.addToCart(UUID);
    }
    public List<ProductResponDto> showCart() {
        return productServiceImpl.getCartProducts();
    }
}
