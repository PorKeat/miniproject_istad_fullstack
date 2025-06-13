package mapper;

import model.dto.ProductResponseDto;
import model.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    public static ProductResponseDto fromProductToProductDto(Product product) {
        return new ProductResponseDto(product.getUuid(), product.getName(), product.getPrice());
    }

    public static List<ProductResponseDto> fromProductToProductDtoList(List<Product> products) {
        return products.stream()
                .map(ProductMapper::fromProductToProductDto)
                .collect(Collectors.toList());
    }
}
