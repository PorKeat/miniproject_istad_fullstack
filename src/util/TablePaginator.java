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

    // ANSI Colors
    public static final String RESET = "\u001B[0m";
    public static final String CYAN = "\u001B[36m";
    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BOLD = "\u001B[1m";

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
            System.out.println(BOLD + YELLOW + "[Page " + (page + 1) + " of " + ((total + pageSize - 1) / pageSize) + "]" + RESET);
            System.out.print(BOLD + GREEN + "Enter Category ID or (n)ext, (p)rev, (q)uit: " + RESET);

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
                    System.out.println(BOLD + RED + "Invalid input. Please enter a valid category ID, 'n', 'p', or 'q'." + RESET);
                }
            }
        }
    }

    public static void productPaginateAndSelect(List<ProductResponseDto> list, Scanner scanner, int pageSize) {
        final int total = list.size();
        final int totalPages = (total + pageSize - 1) / pageSize;
        final String navigationPrompt = "(n)ext, (p)rev, (q)uit";

        final BorderStyle borderStyle = BorderStyle.UNICODE_BOX_DOUBLE_BORDER;
        final ShownBorders borders = ShownBorders.ALL;
        final int[] columnWidths = {55, 30, 10, 20};

        int page = 0;
        long renderStartTime, renderEndTime;

        while (true) {
            renderStartTime = System.nanoTime();

            int start = page * pageSize;
            int end = Math.min(start + pageSize, total);

            Table currentTable = new Table(4, borderStyle, borders);
            currentTable.setColumnWidth(0, columnWidths[0], columnWidths[0] + 10);
            currentTable.setColumnWidth(1, columnWidths[1], columnWidths[1] + 15);
            currentTable.setColumnWidth(2, columnWidths[2], columnWidths[2] + 5);
            currentTable.setColumnWidth(3, columnWidths[3], columnWidths[3] + 10);

            currentTable.addCell("UUID", center);
            currentTable.addCell("Product Name", center);
            currentTable.addCell("Price", center);
            currentTable.addCell("Category", center);

            for (int i = start; i < end; i++) {
                ProductResponseDto p = list.get(i);
                currentTable.addCell(p.uuid(), center);
                currentTable.addCell(p.name(), center);
                currentTable.addCell(String.format("%.2f", p.price()), center);
                currentTable.addCell(p.category(), center);
            }

            String tableOutput = currentTable.render();
            renderEndTime = System.nanoTime();

            System.out.println(tableOutput);
            System.out.printf(BOLD + YELLOW + "[Page %d of %d] " + RESET, page + 1, totalPages);
            System.out.printf(BOLD + CYAN + "(Rendered in %.3f ms)%n" + RESET,
                    (renderEndTime - renderStartTime) / 1_000_000.0);
            System.out.print(BOLD + GREEN + "Enter Product UUID or " + BOLD + CYAN + navigationPrompt + BOLD + GREEN + ": " + RESET);

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("n")) {
                if (end < total) {
                    page++;
                } else {
                    System.out.println(BOLD + RED + "Already on last page" + RESET);
                }
            } else if (input.equalsIgnoreCase("p")) {
                if (page > 0) {
                    page--;
                } else {
                    System.out.println(BOLD + RED + "Already on first page" + RESET);
                }
            } else if (input.equalsIgnoreCase("q")) {
                return;
            } else if (!input.isEmpty()) {
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
                System.out.println(BOLD + RED + "Product not found" + RESET);
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
                table.addCell(String.format("%.2f", c.getPrice()), center);
                table.addCell(String.format("%.2f", c.getQty() * c.getPrice()), center);
            }

            System.out.println(table.render());
            System.out.println(BOLD + YELLOW + "[Page " + (page + 1) + " of " + ((total + pageSize - 1) / pageSize) + "]" + RESET);
            System.out.print(BOLD + GREEN + "Enter Product UUID or (n)ext, (p)rev, (q)uit: " + RESET);

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
