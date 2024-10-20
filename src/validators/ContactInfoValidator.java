package validators;

import exceptions.InvalidEmailException;
import exceptions.InvalidPhoneNumberException;

public class ContactInfoValidator {
    public static void validatePhoneNumber(String phoneNumber) throws InvalidPhoneNumberException {
        if (phoneNumber.length() != 10 || !phoneNumber.matches("\\d{8}")) {
            throw new InvalidPhoneNumberException("Invalid phone number. Must be a 8-digit number.");
        }
    }

    public static void validateEmail(String email) throws InvalidEmailException {
        if (!email.contains("@") || !email.contains(".")) {
            throw new InvalidEmailException("Invalid email address.");
        }
    }
}
