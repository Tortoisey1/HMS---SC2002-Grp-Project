package information;

import exceptions.InvalidEmailException;
import exceptions.InvalidPhoneNumberException;
import validators.ContactInfoValidator;

public class ContactInfo {
    private String phoneNumber;
    private String emailAddress;

    public ContactInfo(String phoneNumber, String emailAddress) {
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

}
