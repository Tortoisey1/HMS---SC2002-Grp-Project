package validators;

import exceptions.InvalidEmailException;
import exceptions.InvalidPhoneNumberException;


/**
 * This helper class provides static methods to validate
 * the phone number and email when user wants to change.
 */

public class ContactInfoValidator {
    /**
     * Validates the phoneNumber entered by the user to change
     * @param phoneNumber to validate
     * @return true if the password matches the conditions below, else false
     * @throws InvalidPhoneNumberException for invalid phone number
     */
    public static boolean validatePhoneNumber(String phoneNumber) throws InvalidPhoneNumberException {
        if (phoneNumber.length() != 8 || !phoneNumber.matches("\\d{8}")) {
            throw new InvalidPhoneNumberException("Invalid phone number. Must be a 8-digit number.");
        }
        return true;
    }

    /**
     * Validates the email entered by the user to change
     * @param email to validate
     * @return true if the email matches the conditions below, else false
     * @throws InvalidEmailException for invalid email
     */
    public static boolean validateEmail(String email) throws InvalidEmailException {
        if (!email.contains("@") || !email.contains(".")) {
            throw new InvalidEmailException("Invalid email address.");
        }
        return true;
    }
}
