package services.doctor;

import entities.Doctor;
import entities.Patient;
import information.Appointment;
import information.MedicalBill;
import information.medical.Medication;
import management.AppointmentDataManager;
import management.DataManager;
import management.MedicationRequestDataManager;
import management.PatientDataManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import app.Global;

/**
 * The service class, MedicalRecordManagementServiceDoctor, only enables doctors to manage medical records. 
 * Provides functionality to view and print detailed 
 * medical records and general personal information of patients assigned to a doctor.
 */

public class MedicalRecordManagementServiceDoctor {

    /**
     * viewMedicalRecords()
     * Displays the list of patients under the doctor's care and allows the doctor
     * to view medical records of a selected patient.
     * 
     * @param doctor is the argument passed in whose patient records are to be viewed.
     */
	
	

    public void viewMedicalRecords(Doctor doctor) {
    	
    	/**
         * @param appointments type {@link ArrayList} for appointments to retrieve all {@link Appointment}
         * @param uniquePatients type {@link Set} for uniquePatients to store unique patient IDs to ensure no duplicate
         * @param filteredAppointments type {@ArrayList } for filteredAppointments to filter based on matching doctor ID to get unique patients
         * 
         * Print out the list of patients for doctor to select to view medical records 
         * Exits the viewing process when 0 is entered
         */
    	
        ArrayList<Appointment> appointments = AppointmentDataManager.getInstance().getList();
        Set<String> uniquePatients = new HashSet<>();
        ArrayList<Appointment> filteredAppointments = (ArrayList<Appointment>) appointments.stream()
                .filter(appointment -> appointment.getDoctorId().getId()
                        .equals(doctor.getUserInformation().getID().getId()) // match doctorId
                        && uniquePatients.add(appointment.getPatientId().getId())) // unique patients
                .collect(Collectors.toList());

        if (filteredAppointments.size() == 0) { // exit if no patients assigned to doctor currently
            System.out.println("No patients under your care exiting");
            return;
        }

        while (true) {
            int option = -1; // Initialize option with a default value
            System.out.println("===== Which Patient Medical Record Do You Want to View =====");
            System.out.println("0. Enter 0 to return");
            for (int i = 0; i < filteredAppointments.size(); i++) { // print list of patients assigned to doctor
                Patient patient = PatientDataManager.getInstance()
                        .retrieve(filteredAppointments.get(i).getPatientId().getId());
                System.out.println((i + 1) + ": " + patient.getUserInformation().getPrivateInformation().getName());
            }

            try {
                option = Integer.valueOf(Global.getScanner().nextLine());

                if (option == 0) {
                    System.out.println("Exiting the view process.");
                    break; // Exit the loop if the user chooses to exit
                } else if (option > 0 && option < filteredAppointments.size()+1) {
                    Patient choicePatient = PatientDataManager.getInstance()
                            .retrieve(filteredAppointments.get(option - 1).getPatientId().getId());

                    printMedicalInfo(choicePatient);

                    break;
                } else {
                    System.out.println("Invalid option. Please choose within" + filteredAppointments.size());
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                Global.getScanner().nextLine(); // Clear the invalid input
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Prints detailed medical information for a given patient, including personal,
     * contact, and medical record details.
     *
     * @param patient The patient whose medical information is to be displayed.
     * @param completedAppointments type {@link List} for completedAppointments to retrieve all {@link Appointment} which has 
     * AppointmentStatus.COMPLETED which indicates appointments that have been recorded an outcome by the doctor
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
        System.out.println("Email Adress:" + patient.getUserInformation().getPrivateInformation().getContactInfo().getEmailAddress());
        System.out.println("Contact Number:" + patient.getUserInformation().getPrivateInformation().getContactInfo().getPhoneNumber());

        System.out.println();
        System.out.println("===== Medical Records =====");

        if (patient.getMedicalInformation().getPastTreatments().isEmpty()) {
            System.out.println("No Medical Records found!");
            System.out.println();
            return;
        }

        List<Appointment> completedAppointments = patient.getMedicalInformation().getPastTreatments();
        System.out.println(completedAppointments.size() + " medical records found!");
        System.out.println();

        int i = 1;

        for (Appointment medicalRecord : completedAppointments) {
            System.out.println();
            System.out.println("Record " + (i++));
            System.out.println("Treatment: " + medicalRecord.getTreatmentTitle());
            System.out.println("Date: " + medicalRecord.getDateOfTreatment().toString());
            System.out.println("Doctor in charge: Dr " + medicalRecord.getDoctorName().toString());
            System.out.println();

            System.out.println("Consultation notes: ");
            
            String notes= medicalRecord.getAppointmentOutcomeRecord().getConsultationNotes().getCriticalDetails();
            System.out.print("Critical Details: ");
            if(notes!=null)System.out.println(notes);
            else System.out.println("None");
            
            notes=medicalRecord.getAppointmentOutcomeRecord().getConsultationNotes().getComplaints();
            System.out.print("Complaints: ");
            if(notes!=null)System.out.println(notes);
            else System.out.println("None");
            
            notes=medicalRecord.getAppointmentOutcomeRecord().getConsultationNotes().getFurtherInfo();
            System.out.print("Further information: ");
            if(notes!=null)System.out.println(notes);
            else System.out.println("None");            
            
            System.out.println();
            
            if(medicalRecord.getAppointmentOutcomeRecord().getMedications().size()==0) 
            {
            	System.out.println("No prescription(s)");
            	System.out.println();
            	break;
            }
            else
            {
            	System.out.println("Medication prescribed:");
                int k = 1;
                for (Medication medicine : medicalRecord.getAppointmentOutcomeRecord().getMedications()) {
                    System.out.println("Prescription " + (k++) + ": " + medicine.getName());
                }
            }
        }

        System.out.println();
    }
}
