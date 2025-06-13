package model.service;

import model.dto.ProductResponseDto;
import model.entity.Product;
import mapper.ProductMapper;
import model.repository.ProductRepositoryImpl;

import java.util.List;
import java.util.stream.Collectors;

public class ProductServiceImpl implements ProductService {
    private final ProductRepositoryImpl productRepository = new ProductRepositoryImpl();

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


    @Override
    public boolean deleteProductByUuid(String uuid) {
        Product product = productRepository.findByUuid(uuid);
        if (product != null) {
            return productRepository.deleteById(product.getId());
        }
        return false;
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductMapper::fromProductToProductDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDto> searchProductByName(String name) {
        List<Product> productList =  productRepository.searchByName(name);
        if (!(productList.isEmpty())){
            return productList.stream().map(ProductMapper::fromProductToProductDto)
                    .collect(Collectors.toList());
        }
        return null;
    }
}
