package View;

import model.dto.OrderProductResponseDto;
import model.dto.OrderResponseDto;

public class OrderView {
    public void displayOrderDetails(OrderResponseDto order) {
        System.out.println("\n=== ORDER DETAILS ===");
        System.out.println("Order #: " + order.orderCode() );
        System.out.println("Date: " + order.orderDate());
        System.out.println("\nItems:");
        System.out.printf("%-20s %-10s %-8s %-10s\n",
                "Product", "Price", "Qty", "Subtotal");
        System.out.println("----------------------------------------");

        for (OrderProductResponseDto item : order.items()) {
            System.out.printf("%-20s $%-7.2f %-8d $%-7.2f\n",
                    item.pName(),
                    item.price(),
                    item.qty(),
                    item.subTotal());
        }

        System.out.println("----------------------------------------");
        System.out.printf("%38s $%-7.2f\n", "TOTAL:", order.totalPrice());
    }
}
