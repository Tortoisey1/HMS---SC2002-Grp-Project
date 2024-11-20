package exceptions;

/**
 * Custom exception class to handle invalid login errors.
 * 
 * <p>
 * This exception is thrown when a login attempt fails due to invalid
 * credentials or other issues.
 * The message provided during instantiation details the specific reason for the
 * exception.
 * 
 * <p>
 * Example scenarios that might throw this exception:
 * <ul>
 * <li>Incorrect username or password</li>
 * <li>Account locked due to too many failed login attempts</li>
 * </ul>
 * 
 * <p>
 * Example usage:
 * 
 * <pre>
 * {@code
 * if (!username.equals(storedUsername) || !password.equals(storedPassword)) {
 *     throw new InvalidLoginException("Invalid login credentials.");
 * }
 * }
 * </pre>
 */
public class InvalidLoginException extends Exception {
    /**
     * Constructs a new {@code InvalidLoginException} with the specified detail
     * message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public InvalidLoginException(String message) {
        super(message);
    }
}
