import View.SearchProductView;
import controller.SearchProductController;
import utils.SearchProductDatabaseUtil;

public class Main {
    public static void main(String[] args) {
        SearchProductController controller = new SearchProductController();
        SearchProductView view = new SearchProductView(controller);
        view.startApplication();

        try {
            SearchProductDatabaseUtil.closeConnection();
        } catch (Exception e) {
            System.err.println("Error closing database: " + e.getMessage());
        }
    }
}