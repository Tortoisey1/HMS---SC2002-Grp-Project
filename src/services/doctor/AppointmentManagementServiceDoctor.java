package services.doctor;

import entities.Doctor;
import entities.Patient;
import entities.Staff;
import enums.AppointmentStatus;
import enums.MedicalService;
import enums.MedicationStatus;
import information.Appointment;
import information.medical.ConsultationNotes;
import information.medical.Medication;
import management.AppointmentDataManager;
import management.DataManager;
import menu.CustomCalendar;
import services.AppointmentManagementService;
import app.Global;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class AppointmentManagementServiceDoctor extends AppointmentManagementService {

    private static AppointmentManagementServiceDoctor appointmentManagementServiceDoctor;
    private Doctor currentDoctor;

    public AppointmentManagementServiceDoctor(
            DataManager<Appointment, String> appointmentDataManager, Doctor currentDoctor) {
        super(appointmentDataManager);
        this.currentDoctor = currentDoctor;
    }

    public static AppointmentManagementServiceDoctor getInstance(Doctor doctor) {
        if (appointmentManagementServiceDoctor == null) {
            appointmentManagementServiceDoctor = new AppointmentManagementServiceDoctor(
                    AppointmentDataManager.getInstance(), doctor);
        }
        return appointmentManagementServiceDoctor;
    }

    public void viewPersonalSchedule() {
        // get the filtered appointments for this specific doctor
        List<Appointment> filteredAppointments = getAppointmentsForDoctor(
                this.currentDoctor.getUserInformation().getID().getId());
        System.out.println("=== Personal Schedule ===");
        if (filteredAppointments.size() == 0) {
            System.out.println("No appointments scheduled.");
        } else {
            for (Appointment appointment : filteredAppointments) {
                System.out.println(appointment);
            }
        }
    }

    // public void setAvailability(Doctor doctor) {
    // Scanner scanner = Global.getScanner();
    // CustomCalendar customCalendar = new CustomCalendar();
    // System.out.println("Setting availability for appointments.");
    // customCalendar.generateSlots();
    // int choice;
    // try {
    // choice = scanner.nextInt();
    // } catch (Exception e) {
    // System.out.println("Invalid input. Please try again.");
    // return;
    // }
    // String time = customCalendar.getTimeSlot(choice);
    // if (!time.equals("invalid")) {
    // System.out.println("Availability set for: " + time);
    // } else {
    // System.out.println("Invalid time slot selection.");
    // }
    // }

    public void handleAppointmentRequests() {
        // Ensure getPendingAppointmentsForDoctor is implemented
        List<Appointment> requests = getPendingAppointmentsForDoctor(
                this.currentDoctor.getUserInformation().getID().getId());
        if (requests.isEmpty()) {
            System.out.println("No pending appointment requests.");
            return;
        }
        for (Appointment request : requests) {
            System.out.println(request);
            System.out.println("Accept (1) or Decline (2)?");
            int choice = -1;

            try {
                choice = Integer.valueOf(Global.getScanner().nextLine());

                switch (choice) {
                    case 1:
                        startAppointment(request);
                        break;
                    case 2:
                        request.setAppointmentStatus(AppointmentStatus.CANCELLED);
                        break;
                    default:
                        System.out.println("Invalid choice!");
                        break;
                }

            } catch (Exception e) {
                // TODO: handle exception
                System.out.println(e.getMessage());
            }
        }
    }

    private void startAppointment(Appointment appointment) {
        int choice = -1;

        System.out.println("Start appointment:");
        System.out.println("What is the service you provided:");
        MedicalService.printMedicalServices();

        try {
            choice = Integer.valueOf(Global.getScanner().nextLine());
            appointment.setMedicalService(MedicalService.values()[choice - 1]);

            while (true) {
                System.out.println("(1) to add\n (0) to quit");
                try {
                    int selection = Integer.valueOf(Global.getScanner().nextLine());

                    switch (selection) {
                        case 0:
                            System.out.println("Exiting adding medicines");
                            break;
                        case 1:
                            System.out.println("What medication does the patient need?");

                            String medicineName = Global.getScanner().nextLine();
                            appointment.getAppointmentOutcomeRecord()
                                    .addMedications(new Medication(medicineName, MedicationStatus.PENDING));

                            break;

                        default:
                            System.out.println("Invalid choice");
                            break;
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    break;
                }
            }

            System.out.println("Enter consultation notes:");
            System.out.println("Enter the critical details:");
            String criticalDetails = Global.getScanner().nextLine();

            System.out.println("Enter complaints:");
            String complaints = Global.getScanner().nextLine();

            System.out.println("Enter furtherInfo");
            String infos = Global.getScanner().nextLine();

            appointment.getAppointmentOutcomeRecord()
                    .setConsultationNotes(new ConsultationNotes(criticalDetails, complaints, infos));

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

    }

    // private void recordAppointmentOutcome(Doctor doctor) {
    // ArrayList<Appointment> appointments =
    // appointmentDataManager.getAppointmentsForDoctor(
    // doctor.getUserInformation().getID().getId());
    // if (appointments == null || appointments.isEmpty()) {
    // System.out.println("No appointments to record outcome for.");
    // return;
    // }
    // for (Appointment appointment : appointments) {
    // if (appointment.getAppointmentStatus() == AppointmentStatus.CONFIRMED) {
    // System.out.println("Recording outcome for: " + appointment);
    // System.out.println("Enter consultation notes:");
    // String notes = Global.getScanner().nextLine();
    // appointment.getAppointmentOutcomeRecord().getConsultationNotes().setCriticalDetails(notes);
    // appointment.setAppointmentStatus(AppointmentStatus.COMPLETED);
    // System.out.println("Outcome recorded.");
    // }
    // }
    // }
}
