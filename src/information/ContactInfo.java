package information;

import exceptions.InvalidEmailException;
import exceptions.InvalidPhoneNumberException;
import validators.ContactInfoValidator;

public class ContactInfo {
    private String phoneNumber;
    private String emailAddress;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        try {
            ContactInfoValidator.validatePhoneNumber(phoneNumber);
            this.phoneNumber = phoneNumber;
        } catch (InvalidPhoneNumberException e) {
            // Handle the exception, e.g., log it or notify the user
            System.out.println(e.getMessage());
        }
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        try {
            ContactInfoValidator.validateEmail(emailAddress);
            this.emailAddress = emailAddress;
        } catch (InvalidEmailException e) {
            // Handle the exception
            System.out.println(e.getMessage());
        }
    }

}
