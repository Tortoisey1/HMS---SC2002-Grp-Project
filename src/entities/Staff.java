package entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import information.UserInformation;

public class Staff extends User {
    // Define the DATE_FORMATTER here with the correct pattern
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Staff(UserInformation userInformation) {
        super(userInformation);
    }

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
