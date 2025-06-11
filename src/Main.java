import controller.ProductController;
import view.TableUI;

public class Main {
    public static void main(String[] args) {
        ProductController productController = new ProductController();
        productController.displayProducts();
//        TableUI t = new TableUI();
//        t.displayTable();
    }
}
