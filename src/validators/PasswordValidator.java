package validators;



/**
 * This helper class provides static method to validate
 * password and ensure it meets the conditions below
 */
public class PasswordValidator {
    /**
     * Validates the newPassword entered by the user to change
     * @param newPassword to validate
     * @return true if the newPassword matches the conditions below, else false
     */
    public static boolean validateNewPassword(String newPassword) {
        return newPassword.length() >= 8 && newPassword.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).*");
    }
}
