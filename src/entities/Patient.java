package entities;

import enums.Gender;
import enums.UserType;

import java.time.LocalDate;

import contacts.ContactInfo;

public class Patient extends User {
    private LocalDate dateOfBirth;
    private ContactInfo contactInfo;

    public Patient(int id) {
        super(id, UserType.PATIENT);
    }


}
