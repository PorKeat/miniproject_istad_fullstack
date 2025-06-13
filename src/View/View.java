package view;

import java.util.Scanner;

public class View {
    private final Scanner scanner = new Scanner(System.in);

    public int showMenu() {
        System.out.println("\n== MENU ==");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.print("Choose: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public String[] getAuth() {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        return new String[]{email, password};
    }

    public String getStringInput(String promp) {
        System.out.print("Enter "+promp+" : ");
        return scanner.nextLine();
    }

    public Integer getIntegerInput(String promp) {
        System.out.print("Enter "+promp+" : ");
        return scanner.nextInt();
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

    public int adminMenu(){
        System.out.println("\n== Admin Panel ==");
        System.out.println("1. Add Product");
        System.out.println("2. Add Category");
        System.out.println("3. Show Product");
        System.out.println("4. Show Category");
        System.out.println("5. Search Product");
        System.out.println("6. Search Category");
        System.out.println("7. Remove Product By ID");
        System.out.println("8. Remove Category By ID");
        System.out.println("9. Log out");
        System.out.print("Choose: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public int userMenu(){
        System.out.println("\n== User Panel ==");
        System.out.println("1. Show Product");
        System.out.println("2. Search Product");
        System.out.println("3. Add Product To Cart");
        System.out.println("4. Show Cart");
        System.out.println("5. Order Product");
        System.out.println("6. Log out");
        System.out.print("Choose: ");
        return Integer.parseInt(scanner.nextLine());
    }


}
