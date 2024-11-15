package entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import information.UserInformation;


/**
 * Staff class implements the basic behaviours from User
 * A class inheritable by workers in hospital : Doctor, Pharmacist etc
 */
public class Staff extends User {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Constructs a Staff and initialize the field needed with super
     * from the class User
     * @param userInformation with the basic information respective to
     * each user
     */

    public Staff(UserInformation userInformation) {
        super(userInformation);
    }

    /**
     * Uses LocalDate to compare the dateOfBirth of staff and now().
     * @return an age calculated by this function
     */
    public int getAge() {
        try {
            LocalDate birthDate = LocalDate.parse(getUserInformation().getPrivateInformation().getDateOfBirth(), DATE_FORMATTER);
            LocalDate currentDate = LocalDate.now();
            return currentDate.getYear() - birthDate.getYear() - (currentDate.getDayOfYear() < birthDate.getDayOfYear() ? 1 : 0);
        } catch (DateTimeParseException e) {
            System.out.println("Error parsing date of birth: " + e.getMessage());
            return -1; // Return a default value or handle as needed
        }
    }
}
