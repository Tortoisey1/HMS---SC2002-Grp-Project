package entities;

import enums.Gender;
import enums.UserType;
import information.ContactInfo;

import java.time.LocalDate;
import java.util.List;

public class Patient extends User {
    private LocalDate dateOfBirth;
    private ContactInfo contactInfo;
    private List<MedicalRecord> medicalRecords;

    private static int patientCount = 0; // Static variable for Patient IDs

    public Patient(int id) {
        super(id, UserType.PATIENT);
    }

}
