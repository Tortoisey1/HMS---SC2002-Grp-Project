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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }

    public void setMedicalRecords(List<MedicalRecord> medicalRecords) {
        this.medicalRecords = medicalRecords;
    }

    public static int getPatientCount() {
        return patientCount;
    }

    public static void setPatientCount(int patientCount) {
        Patient.patientCount = patientCount;
    }

}
