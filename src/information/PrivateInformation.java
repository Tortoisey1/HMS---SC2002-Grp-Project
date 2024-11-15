package information;

import enums.Gender;

/**
 * This class holds the fields for name, dateOfBirth, gender and contact info of user
 * provides basic getter/setters
 * Applicable to all users in the app
 */
public class PrivateInformation {
    private String name;
    private String dateOfBirth;
    private Gender gender;
    private ContactInfo contactInfo;

    /**
     * Constructs an PrivateInformation
     * @param name for name of the user
     * @param dateOfBirth for the date of birth
     * @param gender for gender of the user
     * @param contactInfo type {@link ContactInfo} that holds the field: email and phone number
     */
    public PrivateInformation(String name, String dateOfBirth, Gender gender, ContactInfo contactInfo) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.contactInfo = contactInfo;
    }

    /**
     * @return name of user
     */
    public String getName() {
        return name;
    }

    /**
     * Sets {@code name} with new {@param name}
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return date of birth of user
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets {@code dateOfBirth} with new {@param dateOfBirth}
     */
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * @return gender of user
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Sets {@code gender} with new {@param gender}
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * @return contact info of user that holds the email and phone number
     */
    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    /**
     * Sets {@code contactInfo} with new {@param contactInfo}
     */
    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    /**
     * @return A string representation of the {@code name}
     * {@code dateOfBirth}, {@code gender} , {@code contactInfo}
     */
    @Override
    public String toString() {
        return "PrivateInformation{" +
                "name='" + name + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", gender=" + gender +
                ", contactInfo=" + contactInfo.toString() +
                '}';
    }
}
