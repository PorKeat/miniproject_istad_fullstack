package View;

import model.dto.OrderProductResponseDto;
import model.dto.OrderResponseDto;
import util.ColorUtil;

public class OrderView {
    public void displayOrderDetails(OrderResponseDto order) {
        System.out.println(ColorUtil.BLUE_BOLD + "\n╔════════════════════════════════════════════╗");
        System.out.println("║               ORDER DETAILS               ║");
        System.out.println("╚════════════════════════════════════════════╝" + ColorUtil.RESET);


        System.out.println(ColorUtil.CYAN + "  Order #: " + ColorUtil.YELLOW_BOLD + order.orderCode());
        System.out.println(ColorUtil.CYAN + "  Date:    " + ColorUtil.YELLOW + order.orderDate() + ColorUtil.RESET);


        System.out.println(ColorUtil.PURPLE_BOLD + "\n  ┌────────────────────────────────────────────┐");
        System.out.printf("  │ %-20s %-10s %-6s %-10s │\n",
                "Product", "Price", "Qty", "Subtotal");
        System.out.println("  ├────────────────────────────────────────────┤" + ColorUtil.RESET);

        // Items List
        for (OrderProductResponseDto item : order.items()) {
            System.out.printf("  │ " + ColorUtil.WHITE + "%-20s" + ColorUtil.RESET + " " +
                            ColorUtil.GREEN + "$%-9.2f" + ColorUtil.RESET + " " +
                            ColorUtil.CYAN + "%-6d" + ColorUtil.RESET + " " +
                            ColorUtil.YELLOW + "$%-9.2f" + ColorUtil.RESET + " │\n",
                    item.pName(),
                    item.price(),
                    item.qty(),
                    item.subTotal());
        }

        // Footer
        System.out.println(ColorUtil.PURPLE_BOLD + "  └────────────────────────────────────────────┘");
        System.out.printf("  %38s " + ColorUtil.YELLOW_BOLD + "$%-9.2f" + ColorUtil.RESET + "\n",
                "TOTAL:", order.totalPrice());

        // Additional space
        System.out.println();
    }
}