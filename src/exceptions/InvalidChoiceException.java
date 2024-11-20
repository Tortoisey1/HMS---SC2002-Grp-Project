package exceptions;

/**
 * Custom exception class to handle invalid choice errors.
 * 
 * <p>
 * This exception is thrown when an invalid choice is made in the menu system.
 * The message provided during instantiation details the specific error.
 * 
 * <p>
 * Example usage:
 * 
 * <pre>
 * {@code
 * if (choice < 1 || choice > maxChoices) {
 *     throw new InvalidChoiceException("Invalid choice. Please select a valid option.");
 * }
 * }
 * </pre>
 */
public class InvalidChoiceException extends Exception {
    /**
     * Constructs a new {@code InvalidChoiceException} with the specified detail
     * message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public InvalidChoiceException(String message) {
        super(message);
    }
}
