package enums;


/**
 * All the Type for the User in this app
 */

public enum UserType {
    PATIENT,
    DOCTOR,
    PHARMACIST,
    ADMINISTRATOR;
    /**
     * Static method to print all the Type of Users
     */
    public static void printUserTypes() {
        for (UserType userType : UserType.values()) {
            System.out.println((userType.ordinal() + 1) + ". " + userType);
        }
    }
}