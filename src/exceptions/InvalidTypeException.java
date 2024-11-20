package exceptions;

/**
 * Custom exception class to handle invalid type errors.
 * 
 * <p>
 * This exception is thrown when a value of an invalid type is encountered in
 * the program.
 * The message provided during instantiation specifies the reason for the error.
 * 
 * <p>
 * Common scenarios for this exception:
 * <ul>
 * <li>Attempting to process an object of an incorrect type (e.g., casting or
 * type mismatch)</li>
 * <li>Providing an invalid input type to a method or function (e.g., expecting
 * a number but receiving a string)</li>
 * </ul>
 * 
 * <p>
 * Example usage:
 * 
 * <pre>
 * {@code
 * if (!(input instanceof Integer)) {
 *     throw new InvalidTypeException("Expected an Integer, but received a different type.");
 * }
 * }
 * </pre>
 */
public class InvalidTypeException extends Exception {
    /**
     * Constructs a new {@code InvalidTypeException} with the specified detail
     * message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public InvalidTypeException(String message) {
        super(message);
    }
}
