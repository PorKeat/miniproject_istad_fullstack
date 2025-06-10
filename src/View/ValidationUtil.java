package View;

public class ValidationUtil {

    public static void validateEmail(String email) {
        if (email == null || email.isBlank() || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email.");
        }
    }

    public static void validatePassword(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password is required.");
        }
        if (password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters.");
        }
    }

    public static void validateUsername(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Username is required.");
        }
    }
}
