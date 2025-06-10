import controller.AuthController;
import model.service.UserService;
import model.service.UserServiceImpl;
import view.AuthView;

public class Main {
    public static void main(String[] args) {
        AuthView view = new AuthView();
        UserService userService = new UserServiceImpl();
        AuthController controller = new AuthController(userService, view);
        controller.handleSession();
    }
}
