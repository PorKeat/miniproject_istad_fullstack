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
    private final CartService cartService = new CartServiceImpl();
    Connection conn = null;
    @Override
    public OrderResponseDto placeOrder(Integer userId) throws IllegalStateException {
        try(Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            // 2. Get and validate cart items
            List<CartItem> cartItems = cartService.getCartItems(userId);
            if (cartItems == null || cartItems.isEmpty()) {
                throw new IllegalStateException("Cannot place order with empty cart");
            }

            // 3. Validate stock and calculate total price
            double totalPrice = 0;
            for (CartItem item : cartItems) {
                Product product = productRepository.findById(conn, item.getProductId());
                if (product == null) {
                    throw new IllegalStateException(
                            String.format("Product with ID %d not found", item.getProductId()));
                }
                if (product.getStockQuantity() < item.getQty()) {
                    throw new IllegalStateException(
                            String.format("Not enough stock for %s (Available: %d, Requested: %d)",
                                    product.getName(), product.getStockQuantity(), item.getQty()));
                }
                totalPrice += product.getPrice() * item.getQty();
            }

            // 4. Create and save order
            Order order = new Order();
            order.setUserId(userId);
            order.setOrderDate(new Date(System.currentTimeMillis()));
            order.setTotalPrice(totalPrice);
            order.setOrderCode(generateOrderCode());

            Order createdOrder = orderRepository.createOrder(order);

            // 5. Convert and save order products
            List<OrderProduct> orderProducts = cartItems.stream()
                    .map(item -> new OrderProduct(createdOrder.getId(), item.getProductId(), item.getQty()))
                    .collect(Collectors.toList());

            orderProductRepository.saveBatch(conn, orderProducts);

            // 6. Update product stock
            for (CartItem item : cartItems) {
                int updatedRows = productRepository.updateStock(conn, item.getProductId(), -item.getQty());
                if (updatedRows == 0) {
                    throw new SQLException("Failed to update stock for product ID " + item.getProductId());
                }
            }

            // 7. Clear cart
            cartService.clearCart(userId);

            // 8. Commit transaction
            conn.commit();

            // 9. Prepare and return response
            List<Integer> productIds = cartItems.stream()
                    .map(CartItem::getProductId)
                    .collect(Collectors.toList());

            List<Product> products = productRepository.findByIds(conn, productIds);

            return OrderMapper.toOrderResponseDto(createdOrder, orderProducts, products);

        } catch (SQLException | IllegalStateException e) {
            // Rollback transaction if any error occurs
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    // Log the rollback error but propagate the original exception
                    System.err.println("Failed to rollback transaction: " + ex.getMessage());
                }
            }
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