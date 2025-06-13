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
        int total = list.size();
        int page = 0;

        while (true) {
            int start = page * pageSize;
            int end = Math.min(start + pageSize, total);

            Table table = new Table(4, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.ALL);
            table.setColumnWidth(0, 15, 25);
            table.setColumnWidth(1, 30, 45);
            table.setColumnWidth(2, 10, 15);

            table.addCell("UUID", center);
            table.addCell("Product Name", center);
            table.addCell("Price", center);
            table.addCell("Category",center);

            for (int i = start; i < end; i++) {
                ProductResponseDto p = list.get(i);
                table.addCell(p.uuid(), center);
                table.addCell(p.name(), center);
                table.addCell(String.format("%.2f", p.price()), center);
                table.addCell(p.category(),center);
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
            } else {
                boolean found = list.stream().anyMatch(p -> p.uuid().equals(input));
                if (found) return;
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
            table.setColumnWidth(0, 15, 25);
            table.setColumnWidth(1, 30, 45);
            table.setColumnWidth(2, 10, 15);

            table.addCell("Product Name", center);
            table.addCell("Quantity", center);
            table.addCell("Price", center);
            table.addCell("Total", center);

            for (int i = start; i < end; i++) {
                Cart c = list.get(i);
                table.addCell(c.getProductName(), center);
                table.addCell(String.valueOf(c.getQty()), center);
                table.addCell(String.valueOf(c.getPrice()), center);
                table.addCell(String.valueOf((c.getQty()*c.getPrice())),center);
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
