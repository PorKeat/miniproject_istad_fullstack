package util;

import model.dto.ProductResponseDto;
import model.entity.Cart;
import model.entity.Category;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.util.List;
import java.util.Scanner;

public class TablePaginator {

    private static final CellStyle center = new CellStyle(CellStyle.HorizontalAlign.center);

    public static int categoryPaginateAndSelect(List<Category> list, Scanner scanner, int pageSize) {
        int total = list.size();
        int page = 0;

        while (true) {
            int start = page * pageSize;
            int end = Math.min(start + pageSize, total);

            Table table = new Table(2, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.ALL);
            table.setColumnWidth(0, 20, 25);
            table.setColumnWidth(1, 40, 45);

            table.addCell("ID", center);
            table.addCell("Category Name", center);

            for (int i = start; i < end; i++) {
                Category c = list.get(i);
                table.addCell(String.valueOf(c.getId()), center);
                table.addCell(c.getCategoryName(), center);
            }

            System.out.println(table.render());
            System.out.println("[Page " + (page + 1) + " of " + ((total + pageSize - 1) / pageSize) + "]");
            System.out.print("Enter Category ID or (n)ext, (p)rev, (q)uit: ");

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("n") && end < total) {
                page++;
            } else if (input.equalsIgnoreCase("p") && page > 0) {
                page--;
            } else if (input.equalsIgnoreCase("q")) {
                return -1;  // Cancelled
            } else {
                try {
                    return Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid category ID, 'n', 'p', or 'q'.");
                }
            }
        }
    }

    public static void productPaginateAndSelect(List<ProductResponseDto> list, Scanner scanner, int pageSize) {
        // Pre-compute values that won't change
        final int total = list.size();
        final int totalPages = (total + pageSize - 1) / pageSize;
        final String navigationPrompt = "(n)ext, (p)rev, (q)uit";
        final CellStyle center = new CellStyle(CellStyle.HorizontalAlign.center);

        // Table configuration
        final BorderStyle borderStyle = BorderStyle.UNICODE_BOX_DOUBLE_BORDER;
        final ShownBorders borders = ShownBorders.ALL;
        final int[] columnWidths = {15, 30, 10, 20}; // Adjusted column widths

        int page = 0;
        long renderStartTime, renderEndTime;

        while (true) {
            renderStartTime = System.nanoTime();

            // Calculate page bounds
            final int start = page * pageSize;
            final int end = Math.min(start + pageSize, total);

            // Create new table for each page (more reliable than cloning)
            Table currentTable = new Table(4, borderStyle, borders);
            currentTable.setColumnWidth(0, columnWidths[0], columnWidths[0] + 10);
            currentTable.setColumnWidth(1, columnWidths[1], columnWidths[1] + 15);
            currentTable.setColumnWidth(2, columnWidths[2], columnWidths[2] + 5);
            currentTable.setColumnWidth(3, columnWidths[3], columnWidths[3] + 10);

            // Add headers
            currentTable.addCell("UUID", center);
            currentTable.addCell("Product Name", center);
            currentTable.addCell("Price", center);
            currentTable.addCell("Category", center);

            // Add data rows
            for (int i = start; i < end; i++) {
                ProductResponseDto p = list.get(i);
                currentTable.addCell(p.uuid(), center);
                currentTable.addCell(p.name(), center);
                currentTable.addCell(String.format("%.2f", p.price()), center);
                currentTable.addCell(p.category(), center);
            }

            // Render table and measure performance
            String tableOutput = currentTable.render();
            renderEndTime = System.nanoTime();

            // Display with performance metrics
            System.out.println(tableOutput);
            System.out.printf("[Page %d of %d] (Rendered in %.3f ms)%n",
                    page + 1, totalPages, (renderEndTime - renderStartTime) / 1_000_000.0);
            System.out.print("Enter Product UUID or " + navigationPrompt + ": ");

            // Process input
            String input = scanner.nextLine().trim();

            // Navigation handling
            if (input.equalsIgnoreCase("n")) {
                if (end < total) {
                    page++;
                } else {
                    System.out.println("Already on last page");
                }
            } else if (input.equalsIgnoreCase("p")) {
                if (page > 0) {
                    page--;
                } else {
                    System.out.println("Already on first page");
                }
            } else if (input.equalsIgnoreCase("q")) {
                return;
            } else if (!input.isEmpty()) {
                // Search for product UUID
                boolean found = false;
                for (ProductResponseDto p : list) {
                    if (p.uuid().equalsIgnoreCase(input)) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    return;
                }
                System.out.println("Product not found");
            }
        }
    }

    public static void cartPaginateAndSelect(List<Cart> list, Scanner scanner, int pageSize) {
        int total = list.size();
        int page = 0;

        while (true) {
            int start = page * pageSize;
            int end = Math.min(start + pageSize, total);

            Table table = new Table(4, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.ALL);
            table.setColumnWidth(0, 30, 25);
            table.setColumnWidth(1, 10, 45);
            table.setColumnWidth(2, 10, 15);
            table.setColumnWidth(3, 10, 15);

            table.addCell("Product Name", center);
            table.addCell("Quantity", center);
            table.addCell("Price", center);
            table.addCell("Total", center);

            for (int i = start; i < end; i++) {
                Cart c = list.get(i);
                table.addCell(c.getProductName(), center);
                table.addCell(String.valueOf(c.getQty()), center);
                table.addCell(String.valueOf(c.getPrice()), center);
                table.addCell(String.format("%.2f", c.getQty() * c.getPrice()),center);
            }

            System.out.println(table.render());
            System.out.println("[Page " + (page + 1) + " of " + ((total + pageSize - 1) / pageSize) + "]");
            System.out.print("Enter Product UUID or (n)ext, (p)rev, (q)uit: ");

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("n") && end < total) {
                page++;
            } else if (input.equalsIgnoreCase("p") && page > 0) {
                page--;
            } else if (input.equalsIgnoreCase("q")) {
                return;
            }
        }
    }

}
