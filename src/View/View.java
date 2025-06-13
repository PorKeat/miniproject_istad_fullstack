package View;

import java.util.Scanner;

public class View {
    private final Scanner scanner = new Scanner(System.in);

    // ANSI color codes
    public static final String RESET = "\u001B[0m";
    public static final String CYAN = "\u001B[36m";
    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String BOLD = "\u001B[1m";

    public int showMenu() {
        System.out.println(CYAN + "\n==========| MENU |==========" + RESET);
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println(CYAN + "============================" + RESET);
        System.out.print(GREEN + "[+] Choose: " + RESET);
        return Integer.parseInt(scanner.nextLine());
    }

    public String[] getAuth() {
        System.out.print(BLUE + "[+] Email: " + RESET);
        String email = scanner.nextLine();
        System.out.print(BLUE + "[+] Password: " + RESET);
        String password = scanner.nextLine();
        return new String[]{email, password};
    }

    public String getStringInput(String prompt) {
        System.out.print(YELLOW + "[+] Enter " + prompt + ": " + RESET);
        return scanner.nextLine();
    }

    public Integer getIntegerInput(String prompt) {
        System.out.print(YELLOW + "[+] Enter " + prompt + ": " + RESET);
        return scanner.nextInt();
    }

    public void showAlreadyLoggedIn(String email) {
        System.out.println(GREEN + "[+] Already logged in as " + email + RESET);
    }

    public boolean promptLogout() {
        System.out.print(RED + "[!] Logout? (yes/no): " + RESET);
        return scanner.nextLine().equalsIgnoreCase("yes");
    }

    public void showWelcome(String name) {
        System.out.println(GREEN + "[+] Welcome, " + name + "!" + RESET);
    }

    public void showLogoutMessage() {
        System.out.println(RED + "[!] You are now logged out." + RESET);
    }

    public void showError(String message) {
        System.out.println(RED + "[!] Error: " + message + RESET);
    }

    public String[] getProductInput() {
        System.out.print(BLUE + "[+] Enter product name: " + RESET);
        String name = scanner.nextLine();
        System.out.print(BLUE + "[+] Enter product price: " + RESET);
        String price = scanner.nextLine();
        return new String[]{name, price};
    }

    public int adminMenu() {
        System.out.println(CYAN + "\n==========| Admin Panel |==========" + RESET);
        System.out.println("1. Add Product");
        System.out.println("2. Add Category");
        System.out.println("3. Show Product");
        System.out.println("4. Show Category");
        System.out.println("5. Search Product");
        System.out.println("6. Search Category");
        System.out.println("7. Remove Product By ID");
        System.out.println("8. Remove Category By ID");
        System.out.println("10. Generate Products");
        System.out.println("11. Remove all product");
        System.out.println("12. Log out");
        System.out.println(CYAN + "====================================" + RESET);
        System.out.print(GREEN + "[+] Choose: " + RESET);
        return Integer.parseInt(scanner.nextLine());
    }

    public int userMenu() {
        System.out.println(CYAN + "\n==========| User Panel |==========" + RESET);
        System.out.println("1. Show Product");
        System.out.println("2. Search Product");
        System.out.println("3. Add Product To Cart");
        System.out.println("4. Show Cart");
        System.out.println("5. Order Product");
        System.out.println("6. Order History");
        System.out.println("7. Log out");
        System.out.println(CYAN + "====================================" + RESET);
        System.out.print(GREEN + "[+] Choose: " + RESET);
        return Integer.parseInt(scanner.nextLine());
    }
}
