package exceptions;

/**
 * Custom exception class to handle invalid email errors.
 * 
 * <p>
 * This exception is thrown when an invalid email address is encountered during
 * validation or processing.
 * The message provided during instantiation details the specific error.
 * 
 * *
 * <p>
 * The regex used for validation matches standard email formats:
 * 
 * <pre>
 * ^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$
 * </pre>
 * 
 * - **`^[\\w.%+-]+`**: Matches the local part of the email (letters, numbers,
 * and valid special characters).
 * - **`@[\\w.-]+`**: Matches the "@" symbol followed by the domain name.
 * - **`\\.[a-zA-Z]{2,6}$`**: Matches a dot (`.`) followed by a valid top-level
 * domain (2–6 alphabetic characters).
 * 
 * <p>
 * Example usage:
 * 
 * <pre>
 * {@code
 * if (!email.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
 *     throw new InvalidEmailException("Invalid email format: " + email);
 * }
 * }
 * </pre>
 */
public class InvalidEmailException extends Exception {
    /**
     * Constructs a new {@code InvalidEmailException} with the specified detail
     * message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public InvalidEmailException(String message) {
        super(message);
    }
}
