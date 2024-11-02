package services.patient;
import app.*;
import entities.Patient;
import exceptions.InvalidEmailException;
import exceptions.InvalidPhoneNumberException;
import information.Appointment;
import validators.ContactInfoValidator;

import java.util.InputMismatchException;

public class InformationAccessServicePatient {

    public static void viewMedicalRecord(Patient patient) {
        System.out.println("======= These are your medical records ======");
        System.out.println("Your ID: " + patient.getUserInformation().getID());
        System.out.println("Your Name: " + patient.getUserInformation().getPrivateInformation().getName());
        System.out.println("Your DOB: " + patient.getUserInformation().getPrivateInformation().getDateOfBirth());
        System.out.println("BloodType: " + patient.getMedicalInformation().getBloodType());
        System.out.println(
                "Email Address: "
                        + patient.getUserInformation().getPrivateInformation().getContactInfo().getEmailAddress());
        System.out.println("Phone Number: "
                + patient.getUserInformation().getPrivateInformation().getContactInfo().getPhoneNumber());
        System.out.println("Your records: ");
        for (Appointment medicalRecord : patient.getMedicalInformation().getPastTreatments()) {
            System.out.println(medicalRecord.getTreatmentTitle());
        }
    }

    public static void updatePersonalInformation(Patient patient) {
        // ○ Patients can update non-medical personal information such as email address
        // and
        // contact number.
        while (true) {
            int option = -1; // Initialize option with a default value
            System.out.println("===== Which Contact Info Do You Want to Update =====");
            System.out.println("(1) Email");
            System.out.println("(2) Phone");
            System.out.println("(0) Exit"); // Option to exit the loop

            try {
                option = Global.getScanner().nextInt();
                Global.getScanner().nextLine(); // Clear the buffer

                if (option == 0) {
                    System.out.println("Exiting the update process.");
                    break; // Exit the loop if the user chooses to exit
                } else if (option == 1 || option == 2) {
                    switch (option) {
                        case 1:
                            updateEmail(patient);
                            break;
                        case 2:
                            updatePhone(patient);
                            break;
                    }
                    // Optionally, break the loop after a successful update
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



    public static void updateEmail(Patient patient){
        String email;
        while(true){
            try{
                System.out.println("Key in your new email address: ");
                email = Global.getScanner().nextLine();
                if(ContactInfoValidator.validateEmail(email)){
                    patient.getUserInformation().getPrivateInformation().getContactInfo().setEmailAddress(email);
                    System.out.println("Updated your email");
                    break;
                }
            }catch(InvalidEmailException e){
                System.out.println(e);
            }
        }
    }

    public static void updatePhone(Patient patient){
        String phone;
        while(true){
            try{
                System.out.println("Key in your new phone number: ");
                phone = Global.getScanner().nextLine();
                if(ContactInfoValidator.validatePhoneNumber(phone)){
                    patient.getUserInformation().getPrivateInformation().getContactInfo().setPhoneNumber(phone);
                    System.out.println("Updated your phone number");
                    break;
                }
            }catch(InvalidPhoneNumberException e){
                System.out.println(e);
            }
        }
    }

}
