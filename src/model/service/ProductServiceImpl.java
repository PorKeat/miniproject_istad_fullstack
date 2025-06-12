package model.service;

import mapper.ProductMapper;
import model.dto.ProductResponDto;
import model.entities.ProductModel;
import model.repository.ProductRepository;
import model.repository.ProductRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository = new ProductRepositoryImpl();
    private final List<ProductResponDto> cart = new ArrayList<>();

    @Override
    public ProductResponDto addToCart(String UUID) {
        ProductModel product = productRepository.findByUUID(UUID);
        if (product != null) {
            ProductResponDto dto = ProductMapper.mapFromProductToProductResponDto(product);
            cart.add(dto);
            return dto;
        }
        return null;
    }

    @Override
    public List<ProductResponDto> getCartProducts() {
        return cart;
    }
}
