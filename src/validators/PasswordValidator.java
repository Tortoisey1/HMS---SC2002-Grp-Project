package validators;

public class PasswordValidator {
    public static boolean validateNewPassword(String newPassword) {
        return newPassword.length() >= 8 && newPassword.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).*");
    }
}
