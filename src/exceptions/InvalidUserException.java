package exceptions;

/**
 * Custom exception class to handle invalid user errors.
 * 
 * <p>
 * This exception is thrown when a user is deemed invalid based on specific
 * criteria, such as incorrect credentials,
 * missing required information, or unauthorized access attempts. The message
 * provided during instantiation specifies
 * the reason for the error.
 * 
 * <p>
 * Common scenarios for this exception:
 * <ul>
 * <li>User not found in the system</li>
 * <li>Incorrect username or password</li>
 * <li>User account is locked or deactivated</li>
 * <li>User does not have sufficient permissions or access rights</li>
 * </ul>
 * 
 * <p>
 * Example usage:
 * 
 * <pre>
 * {@code
 * if (!isValidUser(username)) {
 *     throw new InvalidUserException("User not found or invalid credentials.");
 * }
 * }
 * </pre>
 * 
 */
public class InvalidUserException extends Exception {
    /**
     * Constructs a new {@code InvalidUserException} with the specified detail
     * message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public InvalidUserException(String message) {
        super(message);
    }
}
