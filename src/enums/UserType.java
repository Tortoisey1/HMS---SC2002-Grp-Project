package enums;

public enum UserType {
    PATIENT,
    DOCTOR,
    PHARMACIST,
    ADMINISTRATOR;

    public static void printUserTypes() {
        for (UserType userType : UserType.values()) {
            System.out.println((userType.ordinal() + 1) + ". " + userType);
        }
    }
}