package View;

import util.ColorUtil;
import java.util.Scanner;

public class View {
    private final Scanner scanner = new Scanner(System.in);//khmer tver ban

    public int showMenu() {
        System.out.println(ColorUtil.CYAN_BOLD +
                "███████╗     ██████╗ ██████╗ ███╗   ███╗███╗   ███╗███████╗██████╗  ██████╗███████╗    ███╗   ███╗ █████╗ ███╗   ██╗ █████╗  ██████╗ ███████╗███╗   ███╗███████╗███╗   ██╗████████╗\n" +
                "██╔════╝    ██╔════╝██╔═══██╗████╗ ████║████╗ ████║██╔════╝██╔══██╗██╔════╝██╔════╝    ████╗ ████║██╔══██╗████╗  ██║██╔══██╗██╔════╝ ██╔════╝████╗ ████║██╔════╝████╗  ██║╚══██╔══╝\n" +
                "█████╗█████╗██║     ██║   ██║██╔████╔██║██╔████╔██║█████╗  ██████╔╝██║     █████╗      ██╔████╔██║███████║██╔██╗ ██║███████║██║  ███╗█████╗  ██╔████╔██║█████╗  ██╔██╗ ██║   ██║   \n" +
                "██╔══╝╚════╝██║     ██║   ██║██║╚██╔╝██║██║╚██╔╝██║██╔══╝  ██╔══██╗██║     ██╔══╝      ██║╚██╔╝██║██╔══██║██║╚██╗██║██╔══██║██║   ██║██╔══╝  ██║╚██╔╝██║██╔══╝  ██║╚██╗██║   ██║   \n" +
                "███████╗    ╚██████╗╚██████╔╝██║ ╚═╝ ██║██║ ╚═╝ ██║███████╗██║  ██║╚██████╗███████╗    ██║ ╚═╝ ██║██║  ██║██║ ╚████║██║  ██║╚██████╔╝███████╗██║ ╚═╝ ██║███████╗██║ ╚████║   ██║   \n" +
                "╚══════╝     ╚═════╝ ╚═════╝ ╚═╝     ╚═╝╚═╝     ╚═╝╚══════╝╚═╝  ╚═╝ ╚═════╝╚══════╝    ╚═╝     ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝ ╚═════╝ ╚══════╝╚═╝     ╚═╝╚══════╝╚═╝  ╚═══╝   ╚═╝   \n" +
                ColorUtil.RESET);

        System.out.println(ColorUtil.PURPLE_BOLD +
                "                                                            ███████╗██╗   ██╗███████╗████████╗███████╗███╗   ███╗                                                                  \n" +
                "                                                            ██╔════╝╚██╗ ██╔╝██╔════╝╚══██╔══╝██╔════╝████╗ ████║                                                                  \n" +
                "                                                            ███████╗ ╚████╔╝ ███████╗   ██║   █████╗  ██╔████╔██║                                                                  \n" +
                "                                                            ╚════██║  ╚██╔╝  ╚════██║   ██║   ██╔══╝  ██║╚██╔╝██║                                                                  \n" +
                "                                                            ███████║   ██║   ███████║   ██║   ███████╗██║ ╚═╝ ██║                                                                  \n" +
                "                                                            ╚══════╝   ╚═╝   ╚══════╝   ╚═╝   ╚══════╝╚═╝     ╚═╝       " +
                ColorUtil.RESET);
        System.out.println(ColorUtil.BLUE_BOLD + "\n== MENU ==" + ColorUtil.RESET);
        System.out.println(ColorUtil.PURPLE + "1. Login");
        System.out.println(ColorUtil.PURPLE + "2. Register");
        System.out.print(ColorUtil.GREEN + "Choose: " + ColorUtil.RESET);
        return Integer.parseInt(scanner.nextLine());
    }

    public String[] getAuth() {
        System.out.print(ColorUtil.CYAN + "Email: " + ColorUtil.RESET);
        String email = scanner.nextLine();
        System.out.print(ColorUtil.CYAN + "Password: " + ColorUtil.RESET);
        String password = scanner.nextLine();
        return new String[]{email, password};
    }

    public String getStringInput(String prompt) {
        System.out.print(ColorUtil.PURPLE + "Enter " + prompt + " : " + ColorUtil.RESET);
        return scanner.nextLine();
    }

    public Integer getIntegerInput(String prompt) {
        System.out.print(ColorUtil.PURPLE + "Enter " + prompt + " : " + ColorUtil.RESET);
        return Integer.parseInt(scanner.nextLine());
    }

    public void showAlreadyLoggedIn(String email) {
        System.out.println(ColorUtil.YELLOW_BOLD + "Already logged in as " + email + ColorUtil.RESET);
    }

    public boolean promptLogout() {
        System.out.print(ColorUtil.RED + "Logout? (yes/no): " + ColorUtil.RESET);
        return scanner.nextLine().equalsIgnoreCase("yes");
    }

    public void showWelcome(String name) {
        System.out.println(ColorUtil.GREEN_BOLD + "\nWelcome, " + name + "!" + ColorUtil.RESET);
    }

    public void showLogoutMessage() {
        System.out.println(ColorUtil.BLUE + "You are now logged out." + ColorUtil.RESET);
    }

    public void showError(String message) {
        System.out.println(ColorUtil.RED_BOLD + "Error: " + message + ColorUtil.RESET);
    }

    public void showSuccess(String message) {
        System.out.println(ColorUtil.GREEN_BOLD + "Success: " + message + ColorUtil.RESET);
    }

    public String[] getProductInput() {
        System.out.print(ColorUtil.CYAN + "Enter product name: " + ColorUtil.RESET);
        String name = scanner.nextLine();
        System.out.print(ColorUtil.CYAN + "Enter product price: " + ColorUtil.RESET);
        String price = scanner.nextLine();
        return new String[]{name, price};
    }

    public int adminMenu(){
        System.out.println(ColorUtil.BLUE_BOLD + "\n== Admin Panel ==" + ColorUtil.RESET);
        System.out.println(ColorUtil.YELLOW + "1. Add Product");
        System.out.println("2. Add Category");
        System.out.println("3. Show Product");
        System.out.println("4. Show Category");
        System.out.println("5. Search Product");
        System.out.println("6. Search Category");
        System.out.println("7. Remove Product By ID");
        System.out.println("8. Remove Category By ID");
        System.out.println("9. Log out");
        System.out.print(ColorUtil.GREEN + "Choose: " + ColorUtil.RESET);
        return Integer.parseInt(scanner.nextLine());
    }

    public int userMenu(){
        System.out.println(ColorUtil.BLUE_BOLD + "\n== User Panel ==" + ColorUtil.RESET);
        System.out.println(ColorUtil.YELLOW + "1. Show Product");
        System.out.println("2. Search Product");
        System.out.println("3. Add Product To Cart");
        System.out.println("4. Order Product");
        System.out.println("5. Log out");
        System.out.print(ColorUtil.GREEN + "Choose: " + ColorUtil.RESET);
        return Integer.parseInt(scanner.nextLine());
    }
}