package services;

import java.util.Map;
import java.util.List;

import information.AppointmentInformation;
import information.ConsultationNotes;
import information.id.DoctorID;
import information.medical.AppointmentOutcomeRecord;
import information.medical.Medication;
import menu.medicalServiceMenu;

import app.AppLogic;
import enums.AppointmentStatus;
import enums.MedicalService;
import exceptions.InvalidAmountException;

import java.util.HashMap;
import java.util.ArrayList;

public class AppointmentManagementServiceDoctor {
    // main service holds a map of list of appointments with key is the doctors id
    private static Map<DoctorID, ArrayList<AppointmentInformation>> doctorAppointments = new HashMap<DoctorID, ArrayList<AppointmentInformation>>();

    // Doctors can view their personal schedule and set their availability for
    // appointments.
    public static void viewPersonalSchedule(DoctorID id) {
        // view the schedule
        AppointmentManagementServiceDoctor.displayAppointments(id);

    }

    // ○ Doctors can accept or decline appointment requests.
    public static void acceptAppointmentRequest(DoctorID id, AppointmentInformation appointmentInformation) {
        ArrayList<AppointmentInformation> appointmentList = AppointmentManagementServiceDoctor.doctorAppointments
                .getOrDefault(id, null);
        appointmentList.add(appointmentInformation);
        appointmentInformation.setAppointmentStatus(AppointmentStatus.COMPLETED);
    }

    public static void declineAppointmentRequest(DoctorID id) {
        displayAppointments(id);

        ArrayList<AppointmentInformation> appointmentList = AppointmentManagementServiceDoctor.doctorAppointments
                .getOrDefault(id, null);
        // choose the appointment from the arrayList
        appointmentListMenu.printMenu();
        int choice = Integer.valueOf(AppLogic.getScanner().nextLine());
        if (choice <= 0 || choice > appointmentList.size()) {
            throw new InvalidAmountException("No such appointment dont play me");
        }

        AppointmentInformation appointment = appointmentList.get(choice - 1);
        appointment.setAppointmentStatus(AppointmentStatus.CANCELLED);
    }

    // ○ Doctors can view the list of their upcoming appointments.
    public static void displayAppointments(DoctorID id) {
        ArrayList<AppointmentInformation> appointmentList = AppointmentManagementServiceDoctor.doctorAppointments
                .getOrDefault(id, null);

        for (AppointmentInformation appointmentInformation : appointmentList) {
            System.out.println(appointmentInformation);
        }
    }

    // ○ Appointment Outcome Record: After each completed appointment, the doctor
    // will record the:
    // ● Date of Appointment
    // ● Type of service provided (e.g., consultation, X-ray, blood test etc).
    // ● Any prescribed medications: -
    // medication name -
    // status (default is pending)
    // ● Consultation notes

    public static void updateAppointmentOutcomeRecord(
            AppointmentInformation appointmentInformation) {
        // the doctor is the one that creates the record after the appointment and
        // patients can only vieww it
        System.out.println("What medical service did you provide today");
        medicalServiceMenu.printMenu();

        int choice = Integer.valueOf(AppLogic.getScanner().nextLine());
        if (choice <= 0 || choice > MedicalService.values().length) {
            throw new InvalidAmountException("No such choice");
        }

        MedicalService medicalService = MedicalService.values()[choice - 1];

        ConsultationNotes notes = new ConsultationNotes();
        notes.updateNotes();

        List<Medication> medications = new List<Medication>();
        medications.updateMedications();

        appointmentInformation.setAppointmentStatus(AppointmentStatus.COMPLETED);

        appointmentInformation.setAppointmentOutcomeRecord(new AppointmentOutcomeRecord(medicalService, notes, null));
    }

}
