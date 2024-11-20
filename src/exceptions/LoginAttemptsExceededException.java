package exceptions;

/**
 * Custom exception class to handle login attempts exceeded errors.
 * 
 * <p>
 * This exception is thrown when the number of allowed login attempts has been
 * exceeded. The message provided
 * during instantiation specifies the reason for the exception, such as too many
 * failed attempts.
 * 
 * <p>
 * Common scenarios for this exception:
 * <ul>
 * <li>User account lockout due to repeated failed login attempts</li>
 * </ul>
 * 
 * <p>
 * Example usage:
 * 
 * <pre>
 * {@code
 * if (failedLoginAttempts >= MAX_ATTEMPTS) {
 *     throw new LoginAttemptsExceededException("Maximum number of login attempts exceeded.");
 * }
 * }
 * </pre>
 * 
 * @version 1.0
 */
public class LoginAttemptsExceededException extends Exception {
    /**
     * Constructs a new {@code LoginAttemptsExceededException} with the specified
     * detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public LoginAttemptsExceededException(String message) {
        super(message);
    }
}
