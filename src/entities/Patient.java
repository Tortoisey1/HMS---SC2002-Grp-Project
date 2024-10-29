package entities;

import information.AppointmentInformation;
import information.MedicalInformation;
import information.UserInformation;

import java.util.List;

public class Patient extends User {
    private List<MedicalRecord> medicalRecords;
    private List<AppointmentInformation> appointments;
    private MedicalInformation medicalInformation;

    public Patient(UserInformation userInformation, List<MedicalRecord> medicalRecords,
            List<AppointmentInformation> appointments, MedicalInformation medicalInformation) {
        super(userInformation);
        this.medicalRecords = medicalRecords;
        this.appointments = appointments;
        this.medicalInformation = medicalInformation;
    }

    private static int patientCount = 0; // Static variable for Patient IDs

    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }

    public void setMedicalRecords(List<MedicalRecord> medicalRecords) {
        this.medicalRecords = medicalRecords;
    }

    public List<AppointmentInformation> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<AppointmentInformation> appointments) {
        this.appointments = appointments;
    }

    public MedicalInformation getMedicalInformation() {
        return medicalInformation;
    }

    public void setMedicalInformation(MedicalInformation medicalInformation) {
        this.medicalInformation = medicalInformation;
    }

    public static int getPatientCount() {
        return patientCount;
    }

    public static void setPatientCount(int patientCount) {
        Patient.patientCount = patientCount;
    }

}
