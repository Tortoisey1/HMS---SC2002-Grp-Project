package entities;

import enums.Gender;
import enums.UserType;
import java.util.Date;

import contacts.ContactInfo;

public class Patient extends User {
    private String name;
    private Date dateOfBirth;
    private Gender gender;
    private ContactInfo contactInfo;

    public Patient(int id) {
        super(id, UserType.PATIENT);
    }
}
