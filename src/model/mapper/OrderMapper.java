// model/mapper/OrderMapper.java
package model.mapper;

import model.dto.OrderProductResponseDto;
import model.dto.OrderResponseDto;
import model.entity.Order;
import model.entity.OrderProduct;
import model.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderResponseDto toOrderResponseDto(Order order,
                                                      List<OrderProduct> orderProducts,
                                                      List<Product> products) {
        return OrderResponseDto.builder()
                .orderCode(order.getOrderCode())
                .orderDate(order.getOrderDate())
                .totalPrice(order.getTotalPrice())
                .items(mapOrderProductsToDto(orderProducts, products))
                .build();
    }

    private static List<OrderProductResponseDto> mapOrderProductsToDto(
            List<OrderProduct> orderProducts,
            List<Product> products) {

        return orderProducts.stream()
                .map(op -> {
                    Product product = findProductById(products, op.getProductId());
                    return toOrderProductDto(op, product);
                })
                .collect(Collectors.toList());
    }

    private static Product findProductById(List<Product> products, Integer productId) {
        return products.stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    public static OrderProductResponseDto toOrderProductDto(OrderProduct orderProduct, Product product) {
        double price = product != null ? product.getPrice() : 0.0;
        return OrderProductResponseDto.builder()
                .orderId(orderProduct.getOrderId())
                .pId(orderProduct.getProductId())
                .pName(product != null ? product.getName() : "Unknown Product")
                .price(price)
                .qty(orderProduct.getQty())
                .subTotal(price * orderProduct.getQty())
                .build();
    }
}