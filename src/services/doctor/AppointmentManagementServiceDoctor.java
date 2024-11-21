package services.doctor;

import entities.Doctor;
import entities.Patient;
import entities.Staff;
import enums.AppointmentStatus;
import enums.MedicalService;
import enums.MedicationStatus;
import information.Appointment;
import information.MedicalBill;
import information.medical.ConsultationNotes;
import information.medical.Medication;
import management.AppointmentDataManager;
import management.MedicationRequestDataManager;
import management.PatientDataManager;
import management.DataManager;
import management.InventoryDataManager;
import menu.CustomCalendar;
import services.AppointmentManagementService;
import app.Global;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class to manage appointment-related functionalities for doctors. This class extends the
 * {@link AppointmentManagementService} class and includes operations such as viewing the doctor's personal schedule,
 * accepting or declining appointments, recording outcomes, and prescribing medications.
 */
public class AppointmentManagementServiceDoctor extends AppointmentManagementService {

    private DataManager<Medication, String> requestDataManager;
    private InventoryDataManager inventoryDataManager;

    private static AppointmentManagementServiceDoctor appointmentManagementServiceDoctor;
    private Doctor currentDoctor;

    /**
     * Constructor to initialize the {@link AppointmentManagementServiceDoctor} with the given
     * {@link AppointmentDataManager} and the current doctor.
     * 
     * @param appointmentDataManager The data manager for managing appointments.
     * @param currentDoctor The doctor who is using the service.
     */
    public AppointmentManagementServiceDoctor(DataManager<Appointment, String> appointmentDataManager, Doctor currentDoctor) {
        super(appointmentDataManager);
        this.currentDoctor = currentDoctor;
        this.requestDataManager = MedicationRequestDataManager.getInstance();
        this.inventoryDataManager = InventoryDataManager.getInstance();
    }

    /**
     * Singleton method to retrieve the instance of {@link AppointmentManagementServiceDoctor}. 
     * If the instance doesn't exist, it creates a new one.
     * 
     * @param doctor The doctor for whom the instance is to be created.
     * @return The singleton instance of {@link AppointmentManagementServiceDoctor}.
     */
    public static AppointmentManagementServiceDoctor getInstance(Doctor doctor) {
        if (appointmentManagementServiceDoctor == null) {
            appointmentManagementServiceDoctor = new AppointmentManagementServiceDoctor(AppointmentDataManager.getInstance(), doctor);
        }
        return appointmentManagementServiceDoctor;
    }

    /**
     * Displays the doctor's personal schedule by showing the list of confirmed appointments. 
     * If no confirmed appointments are found, a message is shown to the user.
     */
    public void viewPersonalSchedule() {
        ArrayList<Appointment> filteredAppointments = getConfirmedAppointmentList();

        System.out.println("=== Personal Schedule ===");
        if (filteredAppointments.size() == 0) {
            System.out.println("No appointments scheduled.");
            return;
        }

        int i = 1;
        for (Appointment appointment : filteredAppointments) {
            if (appointment.getAppointmentStatus() == AppointmentStatus.CONFIRMED) {
                System.out.println("Appointment " + (i++) + ":");
                System.out.println("Treatment: " + appointment.getTreatmentTitle().toString());
                System.out.println("Date: " + appointment.getDateOfTreatment().toString());
                System.out.println("Time: " + appointment.getTimeOfTreatment().toString());
                System.out.println();
            }
        }
    }

    /**
     * Allows the doctor to accept or decline pending appointments.
     * It checks for conflicting schedules before accepting appointments, 
     * and updates the appointment status to either accepted or cancelled.
     * 
     * filteredAppointments List of pending appointments requested for the current doctor.
     */
    public void acceptDecline() {
        List<Appointment> filteredAppointments = getPendingAppointmentsForDoctor(this.currentDoctor.getUserInformation().getID().getId());

        if (filteredAppointments.size() == 0) {
            System.out.println("No pending appointments");
            return;
        }

        int i = 1;
        for (Appointment appointment : filteredAppointments) {
            System.out.println("Appointment " + (i++) + ": ");
            System.out.println("Treatment: " + appointment.getTreatmentTitle());
            System.out.println("Date: " + appointment.getDateOfTreatment());
            System.out.println("Time: " + appointment.getTimeOfTreatment());
            System.out.println();

            System.out.println("Would you like to accept?");
            System.out.println("1. YES");
            System.out.println("2. NO");

            int choice = -1;
            try {
                choice = Integer.valueOf(Global.getScanner().nextLine());

                switch (choice) {
                    case 1:
                        System.out.println("Validating appointment ...");
                        validateAvailability(appointment, getConfirmedAppointmentList());
                        break;
                    case 2:
                        System.out.println("Declining appointment ...");
                        appointment.setAppointmentStatus(AppointmentStatus.CANCELLED);
                        break;
                    default:
                        System.out.println("Invalid choice!");
                        break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Validates the availability of the doctor by checking for any schedule conflicts with existing confirmed appointments.
     * 
     * @param appointment The appointment to validate.
     * @param confirmedAppointments The list of confirmed appointments in the doctor's schedule.
     */
    private void validateAvailability(Appointment appointment, ArrayList<Appointment> confirmedAppointments) {
        String targetDate = appointment.getDateOfTreatment();
        String targetTime = appointment.getTimeOfTreatment();

        for (Appointment confirmed : confirmedAppointments) {
            if (confirmed.getDateOfTreatment().equals(targetDate) && confirmed.getTimeOfTreatment().equals(targetTime)) {
                System.out.println("Schedule clash with a confirmed appointment");
                System.out.println("Appointment acceptance failed!");
                return;
            }
        }

        System.out.println("Appointment successfully accepted!");
        appointment.setAppointmentStatus(AppointmentStatus.CONFIRMED);
        System.out.println("Appointment status: " + appointment.getAppointmentStatus().toString());
    }

    /**
     * Records the outcome of an appointment, including consultation notes, prescribed medication, and treatment plans.
     * 
     * treatmentChoice The treatment choice made by the doctor from predefined options.
     * medicineInput The input for the prescribed medication.
     * correctMedicineName The correct name of the medication validated against the inventory.
     * MedicationID The ID of the prescribed medication.
     * PatientID The ID of the patient associated with the appointment.
     * AppointmentID The ID of the appointment.
     * currentAppointment The specific appointment being recorded.
     * criticalDetails The critical details recorded by the doctor.
     * complaints The complaints noted by the doctor.
     * furtherInfo Additional information recorded by the doctor during the appointment.
     */
    public void recordOutcome() {
        int treatmentChoice = -1;
        String medicineInput, correctMedicineName, MedicationID;

        System.out.println("Please enter Patient ID: ");
        String PatientID = String.valueOf(Global.getScanner().nextLine());

        System.out.println("Please enter Appointment ID: ");
        String AppointmentID = String.valueOf(Global.getScanner().nextLine());

        Appointment currentAppointment = getSpecificConfirmedAppointment(AppointmentID);

        if (currentAppointment == null || currentAppointment.getAppointmentStatus() != AppointmentStatus.CONFIRMED) {
            System.out.println("Appointment has not been completed or no such appointment exists!");
            return;
        }

        if (PatientID.equals(currentAppointment.getPatientId().getId())
                && currentAppointment.getDoctorId().getId().equals(this.currentDoctor.getUserInformation().getID().getId())) {
            System.out.println("Appointment found!");

            System.out.println("===== Please input consultation notes!");
            System.out.println("Critical Details:");
            String criticalDetails = String.valueOf(Global.getScanner().nextLine());

            System.out.println("Complaints:");
            String complaints = String.valueOf(Global.getScanner().nextLine());

            System.out.println("Further Information:");
            String furtherInfo = String.valueOf(Global.getScanner().nextLine());

            currentAppointment.getAppointmentOutcomeRecord()
                    .setConsultationNotes(new ConsultationNotes(criticalDetails, complaints, furtherInfo));

            while (true) {
                System.out.println("0. Exit prescription process");
                System.out.println("1. Prescription needed for patient");

                try {
                    int selection = Integer.valueOf(Global.getScanner().nextLine());

                    if (selection == 0) {
                        System.out.println("Stop adding medicines, moving on ...");
                        break;
                    } else if (selection == 1) {
                        System.out.println("Enter the name of prescription");
                        medicineInput = String.valueOf(Global.getScanner().nextLine());

                        correctMedicineName = medicineValidator(medicineInput);

                        if (correctMedicineName == null) {
                            System.out.println("Medicine does not exist, please try again");
                        } else {
                            currentAppointment.getAppointmentOutcomeRecord()
                                    .addMedications(new Medication(correctMedicineName, MedicationStatus.PENDING));

                            MedicationID = findMedID(correctMedicineName);

                            requestDataManager.add(new Medication(AppointmentID, MedicationID, MedicationStatus.PENDING, correctMedicineName));
                            System.out.println("Medication successfully prescribed");
                            System.out.println();
                        }
                    } else {
                        System.out.println("Invalid choice");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            while (true) {
                try {
                    System.out.println("===== Please input the treatment done =====");
                    MedicalService.printMedicalServices();
                    treatmentChoice = Integer.valueOf(Global.getScanner().nextLine());

                    if (treatmentChoice > 0 && treatmentChoice < 8) break;
                    else System.out.println("Invalid input for treatment");

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            currentAppointment.setAppointmentStatus(AppointmentStatus.COMPLETED);
            currentAppointment.setMedicalService(MedicalService.values()[treatmentChoice - 1]);

            Patient patient = PatientDataManager.getInstance().retrieve(PatientID);
            patient.getMedicalInformation().getPastTreatments().add(currentAppointment);
        } else {
            System.out.println("Not certified to process appointment. Goodbye");
        }
    }

    /**
     * Validates the medication name entered by the doctor and checks it against the inventory.
     * 
     * @param medicineInput The name of the medication entered by the doctor.
     * @return The valid medication name if it exists in the inventory, null otherwise.
     */
    private String medicineValidator(String medicineInput) {
        String normalizedInput = medicineInput.replace(" ", "").toLowerCase();

        List<Medication> medicationList = inventoryDataManager.getList();

        for (Medication correctMedicineName : medicationList) {
            String normalizedReference = correctMedicineName.getName().replace(" ", "").toLowerCase();

            if (normalizedInput.equals(normalizedReference)) {
                return correctMedicineName.getName();
            }
        }

        return null;
    }

    /**
     * Finds the medication ID for a given medication name.
     * 
     * @param correctName The name of the medication to find.
     * @return The medication ID if the medication exists, null otherwise.
     */
    private String findMedID(String correctName) {
        List<Medication> medicationList = inventoryDataManager.getList();

        for (Medication correctMedicineName : medicationList) {
            if (correctName.equals(correctMedicineName.getName())) {
                return correctMedicineName.getMedicationId();
            }
        }

        return null;
    }
}
