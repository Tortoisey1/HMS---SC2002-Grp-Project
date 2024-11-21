package services.doctor;

import entities.Doctor;
import entities.Patient;
import information.Appointment;
import information.medical.Medication;
import management.AppointmentDataManager;
import management.PatientDataManager;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import app.Global;

/**
 * Service class to manage medical records for doctors. This class allows doctors to view and print detailed
 * medical records and personal information for patients under their care.
 */
public class MedicalRecordManagementServiceDoctor {

    /**
     * Displays the list of patients assigned to the doctor and allows the doctor to view medical records for a selected patient.
     * The doctor can exit the process by entering '0'.
     *
     * @param doctor The doctor whose assigned patient records are to be viewed.
     */
    public void viewMedicalRecords(Doctor doctor) {
        ArrayList<Appointment> appointments = AppointmentDataManager.getInstance().getList();
        Set<String> uniquePatients = new HashSet<>();
        ArrayList<Appointment> filteredAppointments = (ArrayList<Appointment>) appointments.stream()
                .filter(appointment -> appointment.getDoctorId().getId().equals(doctor.getUserInformation().getID().getId()) 
                        && uniquePatients.add(appointment.getPatientId().getId()))
                .collect(Collectors.toList());

        if (filteredAppointments.size() == 0) {
            System.out.println("No patients under your care exiting");
            return;
        }

        while (true) {
            int option = -1; // Default value
            System.out.println("===== Which Patient Medical Record Do You Want to View =====");
            System.out.println("0. Enter 0 to return");
            for (int i = 0; i < filteredAppointments.size(); i++) {
                Patient patient = PatientDataManager.getInstance()
                        .retrieve(filteredAppointments.get(i).getPatientId().getId());
                System.out.println((i + 1) + ": " + patient.getUserInformation().getPrivateInformation().getName());
            }

            try {
                option = Integer.valueOf(Global.getScanner().nextLine());

                if (option == 0) {
                    System.out.println("Exiting the view process.");
                    break; // Exit the loop if the user chooses to exit
                } else if (option > 0 && option < filteredAppointments.size() + 1) {
                    Patient choicePatient = PatientDataManager.getInstance()
                            .retrieve(filteredAppointments.get(option - 1).getPatientId().getId());
                    printMedicalInfo(choicePatient);
                    break;
                } else {
                    System.out.println("Invalid option. Please choose within " + filteredAppointments.size());
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                Global.getScanner().nextLine(); // Clear invalid input
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Prints detailed medical information for a given patient, including personal, contact, and medical record details.
     * 
     * @param patient The patient whose medical information is to be displayed.
     */
    private void printMedicalInfo(Patient patient) {
        System.out.println("===== Patient Information =====");
        System.out.println("Patient Name: " + patient.getUserInformation().getPrivateInformation().getName());
        System.out.println("Patient ID: " + patient.getUserInformation().getID().getId());
        System.out.println("Gender: " + patient.getUserInformation().getPrivateInformation().getGender());
        System.out.println("DOB: " + patient.getUserInformation().getPrivateInformation().getDateOfBirth());
        System.out.println("Blood Type: " + patient.getMedicalInformation().getBloodType());

        System.out.println();
        System.out.println("===== Contact Information =====");
        System.out.println("Email Address: " + patient.getUserInformation().getPrivateInformation().getContactInfo().getEmailAddress());
        System.out.println("Contact Number: " + patient.getUserInformation().getPrivateInformation().getContactInfo().getPhoneNumber());

        System.out.println();
        System.out.println("===== Medical Records =====");

        if (patient.getMedicalInformation().getPastTreatments().isEmpty()) {
            System.out.println("No Medical Records found!");
            return;
        }

        List<Appointment> completedAppointments = patient.getMedicalInformation().getPastTreatments();
        System.out.println(completedAppointments.size() + " medical records found!");
        int i = 1;

        for (Appointment medicalRecord : completedAppointments) {
            System.out.println();
            System.out.println("Record " + (i++));
            System.out.println("Treatment: " + medicalRecord.getTreatmentTitle());
            System.out.println("Date: " + medicalRecord.getDateOfTreatment().toString());
            System.out.println("Doctor in charge: Dr " + medicalRecord.getDoctorName().toString());

            System.out.println("Consultation Notes:");
            String notes = medicalRecord.getAppointmentOutcomeRecord().getConsultationNotes().getCriticalDetails();
            System.out.println("Critical Details: " + (notes != null ? notes : "None"));
            notes = medicalRecord.getAppointmentOutcomeRecord().getConsultationNotes().getComplaints();
            System.out.println("Complaints: " + (notes != null ? notes : "None"));
            notes = medicalRecord.getAppointmentOutcomeRecord().getConsultationNotes().getFurtherInfo();
            System.out.println("Further Information: " + (notes != null ? notes : "None"));

            System.out.println();
            if (medicalRecord.getAppointmentOutcomeRecord().getMedications().isEmpty()) {
                System.out.println("No prescriptions.");
            } else {
                System.out.println("Medications prescribed:");
                int k = 1;
                for (Medication medicine : medicalRecord.getAppointmentOutcomeRecord().getMedications()) {
                    System.out.println("Prescription " + (k++) + ": " + medicine.getName());
                }
            }
        }
    }
}
