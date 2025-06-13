import controller.AuthController;
import model.service.UserService;
import model.service.UserServiceImpl;
import View.View;

public class Main {
    public static void main(String[] args) {
        View view = new View();
        UserService userService = new UserServiceImpl();
        AuthController controller = new AuthController(userService, view);
        controller.handleSession();
    }
}
