package controller;

<<<<<<< HEAD
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
=======
public class ProductController {
    private String name;
    private Integer id;
    private String uuid;
    private Double price;
    private boolean isDeleted;
    private Integer qty;
>>>>>>> origin/taiyi
}
