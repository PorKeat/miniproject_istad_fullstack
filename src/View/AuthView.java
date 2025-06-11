package View;

import java.util.Scanner;

public class AuthView {
    private final Scanner scanner = new Scanner(System.in);

    public int showMenu() {
        System.out.println("\n== MENU ==");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.print("Choose: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public String[] getLoginDetails() {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        return new String[]{email, password};
    }

    public String[] getRegistrationDetails() {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        return new String[]{email, password};
    }

    public String getUsernamePrompt() {
        System.out.print("Enter your name: ");
        return scanner.nextLine();
    }

    public void showAlreadyLoggedIn(String email) {
        System.out.println("Already logged in as " + email);
    }

    public boolean promptLogout() {
        System.out.print("Logout? (yes/no): ");
        return scanner.nextLine().equalsIgnoreCase("yes");
    }

    public void showWelcome(String name) {
        System.out.println("Welcome, " + name + "!");
    }

    public void showLogoutMessage() {
        System.out.println("You are now logged out.");
    }

    public void showError(String message) {
        System.out.println("Error: " + message);
    }

    public String[] getProductInput() {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter product price: ");
        String price = scanner.nextLine();
        return new String[]{name, price};
    }



}
