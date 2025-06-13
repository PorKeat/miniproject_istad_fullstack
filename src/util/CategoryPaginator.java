package util;

import model.entity.Category;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.util.List;
import java.util.Scanner;

public class CategoryPaginator {

    private static final CellStyle center = new CellStyle(CellStyle.HorizontalAlign.center);

    public static int paginateAndSelect(List<Category> categories, Scanner scanner, int pageSize) {
        int total = categories.size();
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
                Category c = categories.get(i);
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
}
