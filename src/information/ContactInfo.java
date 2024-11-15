package information;


/**
 * This class holds the fields for phone number and email for the User
 * provides basic getter/setter for phone number and email
 */
public class ContactInfo {
    private String phoneNumber;
    private String emailAddress;

    /**
     * Constructs an ContactInfo
     * @param phoneNumber for phone number of user
     * @param emailAddress for email of user
     */
    public ContactInfo(String phoneNumber, String emailAddress) {
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    /**
     * @return phone number of user
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets {@code phoneNumber} with new {@param phoneNumber}
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return email of user
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Sets {@code emailAddress} with new {@param emailAddress}
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * @return A string representation of the {@code phoneNumber}
     * {@code emailAddress}
     */
    @Override
    public String toString() {
        return "ContactInfo{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                '}';
    }
}
