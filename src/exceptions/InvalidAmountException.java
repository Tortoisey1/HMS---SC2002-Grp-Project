package exceptions;

/**
 * Custom exception class to handle invalid amount errors.
 * 
 * <p>
 * This exception is thrown when an invalid amount is encountered in a financial
 * or transactional operation.
 * The message provided during instantiation details the specific error.
 * 
 * <p>
 * Example usage:
 * 
 * <pre>
 * {@code
 * if (amount < 0) {
 *     throw new InvalidAmountException("Amount cannot be negative.");
 * }
 * }
 */

public class InvalidAmountException extends Exception {
    /**
     * Constructs a new {@code InvalidAmountException} with the specified detail
     * message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public InvalidAmountException(String message) {
        super(message);
    }
}
