package services;

import java.util.List;
import app.AppLogic;

import entities.Doctor;
import entities.MedicalRecord;
import entities.Patient;
import exceptions.InvalidAmountException;
import information.id.DoctorID;
import information.id.PatientID;

public class MedicalRecordManagementServiceDoctor {
    // Doctors can view the medical records of patients under their care.
    public void viewMedicalRecords() {
        // get the list of patients of the doctor

        // then choose the patient or you want to print everything

        for (Patient patient : Doctor.getPatients()) {
            // then the medical record for each patient
            for (MedicalRecord medicalRecord : patient.getMedicalRecords()) {
                System.out.println(medicalRecord);
            }
        }
    }
    // ○ Doctors can update the medical records of patients by adding new diagnoses,
    // prescriptions, and treatment plans.

    public void updateMedicalRecords(List<MedicalRecord> medicalRecords) throws InvalidAmountException {
        // get the medical records of the patient
        // get which specific record he want
        int choice = Integer.valueOf(AppLogic.getScanner().nextLine());
        if (choice <= 0 || choice > medicalRecords.size()) {
            throw new InvalidAmountException("no such record");
        }

        MedicalRecord record = medicalRecords.get(choice - 1);
        MedicalRecord.update(record);
    }

}
