package exceptions;

/**
 * Custom exception class to handle invalid password errors.
 * 
 * <p>
 * This exception is thrown when a password does not meet the required criteria
 * or fails validation.
 * The message provided during instantiation specifies the reason for the error.
 * 
 * <p>
 * Common scenarios for this exception:
 * <ul>
 * <li>Password is too short or too long</li>
 * <li>Missing required character types (e.g., uppercase letters, digits,
 * special characters)</li>
 * <li>Password contains forbidden patterns or words</li>
 * </ul>
 * 
 * <p>
 * Example usage:
 * 
 * <pre>
 * {@code
 * String passwordRegex =
 * "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$";
 * if (!password.matches(passwordRegex)) {
 * throw new InvalidPasswordException("Password must be at least 8 characters
 * long, "
 * + "include an uppercase letter, a lowercase letter, a digit, and a special
 * character.");
 * }
 * }
 * 
 */

public class InvalidPasswordException extends Exception {
    /**
     * Constructs a new {@code InvalidPasswordException} with the specified detail
     * message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public InvalidPasswordException(String message) {
        super(message);
    }
}
