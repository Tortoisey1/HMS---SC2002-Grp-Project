package entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import enums.Gender;
import information.ContactInfo;
import enums.BloodType;

public class MedicalRecord {
    private final int patientId; // Patient ID
    private String patientName; // Name 
    private LocalDate dateOfBirth; // Date of Birth
    private Gender gender; // Gender
    private ContactInfo contactInfo; // Contact Information
    private final BloodType bloodType; // Blood Type
    private List<Diagnosis> pastDiagnoses; // Past Diagnoses
    private List<Treatment> pastTreatments; // Past Treatments

    public MedicalRecord(int patientId, String patientName, LocalDate dateOfBirth, Gender gender,
            ContactInfo contactInfo, BloodType bloodType, List<String> pastDiagnoses, List<String> pastTreatments) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.contactInfo = contactInfo;
        this.bloodType = bloodType;
        this.pastDiagnoses = new ArrayList<>();
        this.pastTreatments = new ArrayList<>();
    }

    public int getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public BloodType getBloodType() {
        return bloodType;
    }


    public List<Diagnosis> getPastDiagnoses() {
        return pastDiagnoses;
    }

    public void setPastDiagnoses(List<Diagnosis> pastDiagnoses) {
        this.pastDiagnoses = pastDiagnoses;
    }

    public List<Treatment> getPastTreatments() {
        return pastTreatments;
    }

    public void setPastTreatments(List<Treatment> pastTreatments) {
        this.pastTreatments = pastTreatments;
    }
}
