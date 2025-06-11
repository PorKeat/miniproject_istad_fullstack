package view;

import model.entities.Product;
import model.repository.ProductRepository;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.util.List;

public class TableUI {
    private  String[] columnName;
    public void displayTable(List<Product> product) {
        CellStyle center = new CellStyle(CellStyle.HorizontalAlign.center);
        Table table = new Table(
                4,
                BorderStyle.UNICODE_BOX_DOUBLE_BORDER,
                ShownBorders.ALL
        );
        columnName= new String[]{"UUID","Product Name","Price","Quantity"};

        for (String c: columnName){
            table.addCell(c,center);
        }

        for (Product p: product){
            table.addCell(p.getUuid(),center);
            table.addCell(p.getP_name(),center);
            table.addCell(String.valueOf(p.getPrice()),center);
            table.addCell(String.valueOf(p.getQty()),center);
        }

        System.out.println(table.render());

    }
}