package services.patient;

import app.*;
import entities.Patient;
import exceptions.InvalidEmailException;
import exceptions.InvalidPhoneNumberException;
import information.Appointment;
import information.medical.Medication;
import menu.CustomCalendar;
import validators.ContactInfoValidator;
import java.util.InputMismatchException;

/**
 * This Service InformationAccessServicePatient class handles the business logic
 * of the app for Patient Menu
 * It allows patient to view and update Patient Details
 */
public class InformationAccessServicePatient {

    /**
     * Static viewMedicalRecord() without having to initialize
     * InformationAccessServicePatient
     * Display details for current patient based on the {@param patient}
     */
    public static void viewMedicalRecord(Patient patient) {
        System.out.println("======= These are your medical records ======");
        System.out.println("Your ID: " + patient.getUserInformation().getID().getId());
        System.out.println("Your Name: " + patient.getUserInformation().getPrivateInformation().getName());
        System.out.println("Your DOB: " + patient.getUserInformation().getPrivateInformation().getDateOfBirth());
        System.out.println("BloodType: " + patient.getMedicalInformation().getBloodType());
        System.out.println(
                "Email Address: "
                        + patient.getUserInformation().getPrivateInformation().getContactInfo().getEmailAddress());
        System.out.println("Phone Number: "
                + patient.getUserInformation().getPrivateInformation().getContactInfo().getPhoneNumber());
        
        if(patient.getMedicalInformation().getPastTreatments().isEmpty()) 
        {
        	System.out.println("No medical records found!");
        	System.out.println();
        	return;
        }
        
        
        System.out.println();
        System.out.println(patient.getMedicalInformation().getPastTreatments().size() + " records found! Your records: ");
        
        int z=1;
        
        for (Appointment medicalRecord : patient.getMedicalInformation().getPastTreatments()) {
        	System.out.println();
        	System.out.println("Medical Record "+ (z++));
            System.out.println("Treatment: "+medicalRecord.getTreatmentTitle());
            System.out.println("Date of treatment: "+medicalRecord.getDateOfTreatment());
            System.out.println("Doctor in charge: "+medicalRecord.getDoctorName());
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
            	return;
            }
            
            System.out.println("Medication prescribed: ");
            int k=1;
            
            for (Medication prescription : medicalRecord.getAppointmentOutcomeRecord().getMedications()) {
                System.out.println("Prescription " + (k++) + ": " + prescription.getName());
            }
            
            
            
            
        }
    }

    /**
     * Static viewMedicalRecord() without having to initialize
     * InformationAccessServicePatient
     * Display details for current patient based on the {@param patient}
     */
    public static void updatePersonalInformation(Patient patient) {
        while (true) {
            int option = -1;
            System.out.println("===== Which Contact Info Do You Want to Update =====");
            System.out.println("(1) Email");
            System.out.println("(2) Phone");
            System.out.println("(0) Exit");

            try {
                option = Global.getScanner().nextInt();
                Global.getScanner().nextLine();

                if (option == 0) {
                    System.out.println("Exiting the update process.");
                    break;
                } else if (option == 1 || option == 2) {
                    switch (option) {
                        case 1:
                            updateEmail(patient);
                            break;
                        case 2:
                            updatePhone(patient);
                            break;
                    }
                    break;
                } else {
                    System.out.println("Invalid option. Please choose 0, 1, or 2.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                Global.getScanner().nextLine(); // Clear the invalid input
            }
        }

    }

    /**
     * Static updateEmail() sub functions to allow user to update email
     * Used {@link ContactInfoValidator} to validate the email
     * Continuous prompt until patient give the valid email to update
     * {
     * 
     * @exception InvalidEmailException} to be handled
     */
    public static void updateEmail(Patient patient) {
        String email;
        while (true) {
            try {
                System.out.println("Key in your new email address: ");
                email = Global.getScanner().nextLine();
                if (ContactInfoValidator.validateEmail(email)) {
                    patient.getUserInformation().getPrivateInformation().getContactInfo().setEmailAddress(email);
                    System.out.println("Updated your email");
                    break;
                }
            } catch (InvalidEmailException e) {
                System.out.println(e);
            }
        }
    }

    /**
     * Static updatePhone() sub functions to allow user to update phone number
     * Used {@link ContactInfoValidator} to validate the phone number
     * Continuous prompt until patient give the valid phone number to update
     * {
     * 
     * @exception InvalidPhoneNumberException} to be handled
     */
    public static void updatePhone(Patient patient) {
        String phone;
        while (true) {
            try {
                System.out.println("Key in your new phone number: ");
                phone = Global.getScanner().nextLine();
                if (ContactInfoValidator.validatePhoneNumber(phone)) {
                    patient.getUserInformation().getPrivateInformation().getContactInfo().setPhoneNumber(phone);
                    System.out.println("Updated your phone number");
                    break;
                }
            } catch (InvalidPhoneNumberException e) {
                System.out.println(e);
            }
        }
    }

}
