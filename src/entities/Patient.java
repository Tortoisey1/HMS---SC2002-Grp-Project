package entities;

import information.Appointment;
import information.MedicalInformation;
import information.UserInformation;

import java.util.ArrayList;
import java.util.List;

import enums.BloodType;

public class Patient extends User {
    private List<Appointment> appointments;
    private MedicalInformation medicalInformation;

    public Patient(UserInformation userInformation,BloodType bloodType) {
        super(userInformation);
        this.appointments = new ArrayList<>();
        this.medicalInformation = new MedicalInformation(bloodType);
    }

    private static int patientCount = 0; // Static variable for Patient IDs

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
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
