package utils;



import java.security.MessageDigest;
import java.util.Base64;

public class PassWordManager {
    public static String hashPassWord;

    public static String hashPassWord(String passWord) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(passWord.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b)); // convert to hex
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace(); // ❗ always log errors for debugging
        }
        return null;
    }
}
