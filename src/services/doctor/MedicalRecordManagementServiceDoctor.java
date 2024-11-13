package services.doctor;

import entities.Doctor;
import entities.Patient;
import information.Appointment;
import information.medical.MedicalRecord;
import management.AppointmentDataManager;
import management.PatientDataManager;
import services.patient.InformationAccessServicePatient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import app.Global;

public class MedicalRecordManagementServiceDoctor {

    public static void viewMedicalRecords(Doctor doctor) {
        // get the patients tagged under the doctor by checking the appointments
        ArrayList<Appointment> appointments = AppointmentDataManager.getInstance().getList();

        // A Set to store unique patient IDs to ensure no duplicate patients
        Set<String> uniquePatients = new HashSet<>();

        // filter based on the matching doctor id then get unique patients
        ArrayList<Appointment> filteredAppointments = (ArrayList<Appointment>) appointments.stream()
                .filter(appointment -> appointment.getDoctorId().getId()
                        .equals(doctor.getUserInformation().getID().getId()) // match doctorId
                        && uniquePatients.add(appointment.getPatientId().getId())) // unique patients
                .collect(Collectors.toList());

        if (filteredAppointments.size() == 0) {
            System.out.println("No patients under your care exiting");
            return;
        }

        while (true) {
            int option = -1; // Initialize option with a default value
            System.out.println("===== Which Patient Medical Record Do You Want to View =====");
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
                } else if (option > 0 && option < filteredAppointments.size()) {
                    Patient choicePatient = PatientDataManager.getInstance()
                            .retrieve(filteredAppointments.get(option - 1).getPatientId().getId());
                    InformationAccessServicePatient.viewMedicalRecord(choicePatient);
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

    public static void updateMedicalRecords(Doctor doctor) {
        // assume that doctor will view first before updating so wont be calling view
        // here

        // get the patients tagged under the doctor by checking the appointments
        ArrayList<Appointment> appointments = AppointmentDataManager.getInstance().getList();

        // A Set to store unique patient IDs to ensure no duplicate patients
        Set<String> uniquePatients = new HashSet<>();

        // filter based on the matching doctor id then get unique patients
        ArrayList<Appointment> filteredAppointments = (ArrayList<Appointment>) appointments.stream()
                .filter(appointment -> appointment.getDoctorId().getId()
                        .equals(doctor.getUserInformation().getID().getId()) // match doctorId
                        && uniquePatients.add(appointment.getPatientId().getId())) // unique patients
                .collect(Collectors.toList());

        if (filteredAppointments.size() == 0) {
            System.out.println("No patients under your care exiting");
            return;
        }

        while (true) {
            int option = -1; // Initialize option with a default value
            System.out.println("===== Which Patient Medical Record Do You Want to Update =====");
            for (int i = 0; i < filteredAppointments.size(); i++) {
                Patient patient = PatientDataManager.getInstance()
                        .retrieve(filteredAppointments.get(i).getPatientId().getId());
                System.out.println((i + 1) + ": " + patient.getUserInformation().getPrivateInformation().getName());
            }

            try {
                option = Integer.valueOf(Global.getScanner().nextLine());

                if (option == 0) {
                    System.out.println("Exiting the Update process.");
                    break; // Exit the loop if the user chooses to exit
                } else if (option > 0 && option < filteredAppointments.size()) {

                    // get the medical record of the patient
                    Patient choicePatient = PatientDataManager.getInstance()
                            .retrieve(filteredAppointments.get(option - 1).getPatientId().getId());
                    List<MedicalRecord> records = choicePatient.getMedicalRecords();

                    while (true) {
                        int choice = -1; // Initialize option with a default value

                        System.out.println("(1) Continue updating:");
                        System.out.println("(0) Quit");

                        try {
                            choice = Integer.valueOf(Global.getScanner().nextLine());

                            if (choice == 0) {
                                System.out.println("Exiting the update process.");
                                break; // Exit the loop if the user chooses to exit
                            } else if (choice == 1) {
                                records.add(updateRecord());
                            } else {
                                System.out.println("Invalid option. Please choose 0, 1, or 2.");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a number.");
                            Global.getScanner().nextLine(); // Clear the invalid input
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
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

    private static MedicalRecord updateRecord() {
        MedicalRecord record = new MedicalRecord();

        System.out.println("Enter new Diagnosis");
        String diagnosis = Global.getScanner().nextLine();

        System.out.println("Enter new treatment planL");
        String plan = Global.getScanner().nextLine();

        List<String> medications = new ArrayList<>();

        while (true) {
            int choice = -1; // Initialize option with a default value

            System.out.println("(1) Continue adding medications:");
            System.out.println("(0) Quit");

            try {
                choice = Integer.valueOf(Global.getScanner().nextLine());

                if (choice == 0) {
                    System.out.println("Exiting the update process.");
                    break; // Exit the loop if the user chooses to exit
                } else if (choice == 1) {
                    System.out.println("Enter medication name:");
                    medications.add(Global.getScanner().nextLine());
                } else {
                    System.out.println("Invalid option. Please choose 0, 1.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                Global.getScanner().nextLine(); // Clear the invalid input
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        record.setDiagnosis(diagnosis);
        record.setTreatmentPlan(plan);
        record.setMedications(medications);

        return record;

    }
}