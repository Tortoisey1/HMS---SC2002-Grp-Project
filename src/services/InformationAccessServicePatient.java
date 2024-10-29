package services;

import entities.MedicalRecord;
import entities.Patient;
import exceptions.InvalidChoiceException;

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
        for (MedicalRecord medicalRecord : patient.getMedicalRecords()) {
            System.out.println(medicalRecord);
        }

    }

    public static void updatePersonalInformation(Patient patient) {
        // ○ Patients can update non-medical personal information such as email address
        // and
        // contact number.
        while (true) {
            int option;
            System.out.println("===== Which Contact info do you want to update =====");
            System.out.println("(1) Email");
            System.out.println("(2) Phone");
            try {
                option = Integer.valueOf(Global.getScanner().nextLine());
                if (option == 1 || option == 2) {
                    switch (option) {
                        case 1:
                            System.out.println("Key in your new email address: ");
                            patientController.updateDetails(patient, 1, scanner.next());
                            System.out.println("Updated your email");
                            break;
                        case 2:
                            System.out.println("Key in your new phone number: ");
                            patientController.updateDetails(patient, 2, scanner.next());
                            System.out.println("Updated your phone number");
                            break;
                        default:
                            throw new InvalidChoiceException("No such choice dont play");
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }
}
