package entities;

import information.Appointment;
import information.MedicalInformation;
import information.UserInformation;
import information.medical.MedicalRecord;
import java.util.ArrayList;
import java.util.List;
import enums.BloodType;



/**
 * Patient class implements the basic behaviours from User
 */

public class Patient extends User {
    private List<Appointment> appointments;
    private MedicalInformation medicalInformation;
    private static int patientCount = 0; // Static variable for Patient IDs
    private List<MedicalRecord> medicalRecords = new ArrayList<>();


    /**
     * Constructs a Patient and initialize the field needed with super
     * from the class User and additional fields
     * @param userInformation with the basic information respective to each user
     * @param bloodType to be used in the MedicalInformation object
     * Local variable: {@link #appointments} for all the appointments for this patient
     * Local variable: {@link #medicalInformation} for all medical Info for this patient
     */
    public Patient(UserInformation userInformation, BloodType bloodType) {
        super(userInformation);
        this.appointments = new ArrayList<>();
        this.medicalInformation = new MedicalInformation(bloodType);
    }

    /**
     * Retrieves all the appointments under this patient
     * @return List of type {@link Appointment}
     */
    public List<Appointment> getAppointments() {
        return appointments;
    }

    /**
     * Sets the list of appointments with the new list {@param appointments}
     */
    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    /**
     * Retrieves MedicalInformation of this patient
     * @return Memory Reference of {@link MedicalInformation} under this patient
     */
    public MedicalInformation getMedicalInformation() {
        return medicalInformation;
    }

    /**
     * Sets the MedicalInformation with the new updated MedicalInformation {@param medicalInformation}
     */
    public void setMedicalInformation(MedicalInformation medicalInformation) {
        this.medicalInformation = medicalInformation;
    }

    /**
     * Retrieves Static field {@code #patientCount} for Patient class
     * @return type int of current number of patients initialized.
     */
    public static int getPatientCount() {
        return patientCount;
    }

    /**
     * Sets the Static field {@code #patientCount} with the new {@param patientCount}
     */
    public static void setPatientCount(int patientCount) {
        Patient.patientCount = patientCount;
    }

    /**
     * Retrieves all the MedicalRecord under this patient
     * @return List of type {@link MedicalRecord}
     */
    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }

    /**
     * Sets the list of MedicalRecord with the new list {@param medicalRecords}
     */
    public void setMedicalRecords(List<MedicalRecord> medicalRecords) {
        this.medicalRecords = medicalRecords;
    }


    /**
     * @return A string representation of the {@code appointments}
     * {@code medicalInformation}, {@code medicalRecords}
     */
    @Override
    public String toString() {
        return "Patient{" +
                "appointments=" + appointments.stream().toList() +
                ", medicalInformation=" + medicalInformation.toString() +
                ", medicalRecords=" + medicalRecords.stream().toString() +
                '}';
    }
}
