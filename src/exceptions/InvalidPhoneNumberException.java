package exceptions;

/**
 * Custom exception class to handle invalid phone number errors.
 * 
 * <p>
 * This exception is thrown when a phone number fails to meet the expected
 * format or validation criteria.
 * The message provided during instantiation specifies the reason for the error.
 * 
 * <p>
 * Common scenarios for this exception:
 * <ul>
 * <li>Phone number contains non-numeric characters</li>
 * <li>Phone number is too short or too long</li>
 * <li>Phone number does not match the expected regional format</li>
 * </ul>
 * 
 * <p>
 * Example usage:
 * 
 * <pre>
 * {@code
 * String phoneNumberRegex = "^\\d{10}$"; // Example: 10-digit phone number
 * if (!phoneNumber.matches(phoneNumberRegex)) {
 *     throw new InvalidPhoneNumberException("Phone number must be exactly 10 digits.");
 * }
 * }
 * </pre>
 * 
 */
public class InvalidPhoneNumberException extends Exception {
    /**
     * Constructs a new {@code InvalidPhoneNumberException} with the specified
     * detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public InvalidPhoneNumberException(String message) {
        super(message);
    }
}
