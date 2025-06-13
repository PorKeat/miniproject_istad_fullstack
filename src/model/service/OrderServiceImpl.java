package model.service;

import db.DBConnection;
import model.dto.OrderResponseDto;
import model.entity.*;
import model.mapper.OrderMapper;
import model.repository.OrderProductRepositoryImpl;
import model.repository.OrderRepositoryImpl;
import model.repository.ProductRepositoryImpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {
    private final OrderRepositoryImpl orderRepository = new OrderRepositoryImpl();
    private final OrderProductRepositoryImpl orderProductRepository = new OrderProductRepositoryImpl();
    private final ProductRepositoryImpl productRepository = new ProductRepositoryImpl();
    private final CartServiceImpl cartService = new CartServiceImpl();

    @Override
    public OrderResponseDto placeOrder(Integer userId) throws IllegalStateException {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            // 1. Get and validate cart items
            List<Cart> carts = cartService.getUserCart(userId);
            if (carts == null || carts.isEmpty()) {
                throw new IllegalStateException("Cannot place order with empty cart");
            }

            // 2. Get all products in cart and validate
            List<Integer> productIds = carts.stream()
                    .map(Cart::getProductId)
                    .collect(Collectors.toList());

            List<Product> products = productRepository.findByIds(conn, productIds);
            if (products == null || products.size() != carts.size()) {
                throw new IllegalStateException("Some products in cart are not available");
            }

            // 3. Calculate total price
            double totalPrice = 0;
            for (Cart item : carts) {
                Product product = products.stream()
                        .filter(p -> p.getId().equals(item.getProductId()))
                        .findFirst()
                        .orElseThrow(() -> new IllegalStateException(
                                "Product with ID " + item.getProductId() + " not found"));

                totalPrice += product.getPrice() * item.getQty();
            }

            // 4. Create and save order
            Order order = Order.builder()
                    .userId(userId)
                    .orderDate(new Date(System.currentTimeMillis()))
                    .totalPrice(totalPrice)
                    .orderCode(generateOrderCode())
                    .build();

            Order createdOrder = orderRepository.createOrder(order);
            if (createdOrder == null) {
                throw new SQLException("Failed to create order");
            }

            // 5. Convert and save order products
            List<OrderProduct> orderProducts = carts.stream()
                    .map(cartItem -> new OrderProduct(
                            createdOrder.getId(), // Use the order ID from the saved order
                            cartItem.getProductId(),
                            cartItem.getQty()))
                    .collect(Collectors.toList());

            orderProductRepository.createBatch(orderProducts);

            // 6. Clear cart
            cartService.deleteFromCart(userId);

            // 7. Commit transaction
            conn.commit();

            // 8. Prepare and return response
            return OrderMapper.toOrderResponseDto(createdOrder, orderProducts, products);

        } catch (SQLException | IllegalStateException e) {
            // Rollback transaction if any error occurs
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Failed to rollback transaction: " + ex.getMessage());
                }
            }
            throw new IllegalStateException("Failed to place order: " + e.getMessage(), e);
        } finally {
            // Reset connection state
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }

    private String generateOrderCode() {
        return "ISTAD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}