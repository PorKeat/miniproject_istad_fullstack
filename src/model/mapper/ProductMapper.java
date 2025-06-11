package model.mapper;

import model.dto.ProductDto;
import model.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    public static ProductDto fromProductToProductDto(Product product) {
        return new ProductDto(product.getName(), product.getPrice());
    }

    public static List<ProductDto> fromProductToProductDtoList(List<Product> products) {
        return products.stream()
                .map(ProductMapper::fromProductToProductDto)
                .collect(Collectors.toList());
    }
}
