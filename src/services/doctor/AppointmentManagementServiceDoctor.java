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
 * Service class AppointmentManagementServiceDoctor, to manage appointment-related functionalities for doctors. This class extends the
 * AppointmentManagementService and includes operations such as viewing the doctor's personal schedule,
 * accepting or declining appointments, recording outcomes, and prescribing medications.
 */
public class AppointmentManagementServiceDoctor extends AppointmentManagementService {

    private DataManager<Medication, String> requestDataManager;
    private InventoryDataManager inventoryDataManager;

    private static AppointmentManagementServiceDoctor appointmentManagementServiceDoctor;
    private Doctor currentDoctor;

    /**
     * Constructor to initialize AppointmentManagementServiceDoctor with the given AppointmentDataManager and Doctor.
     * 
     * @param appointmentDataManager The data manager for appointments.
     * @param currentDoctor The unique doctor who signed in and is currently using the service.
     * @param requestDataManager type {@link DataManager} for requestDataManager to retrieve all {@link Medication} to make prescription requests
     * which are handled by pharmacist 
     * @param inventoryDataManager type {@link InventoryDataManager} for inventoryDataManager to retrieve all {@link Medication} to check that
     * the doctor enteres the correct medication that only exist within the medication inventory
     */
    public AppointmentManagementServiceDoctor(
            DataManager<Appointment, String> appointmentDataManager, Doctor currentDoctor) {
        super(appointmentDataManager);
        this.currentDoctor = currentDoctor;
        this.requestDataManager = MedicationRequestDataManager.getInstance();
        this.inventoryDataManager = InventoryDataManager.getInstance();
    }

    /**
     * Singleton method to get the instance of AppointmentManagementServiceDoctor.
     * 
     * @param doctor The doctor for whom the instance is to be created.
     * @return The instance of AppointmentManagementServiceDoctor is created if it is null
     */
    public static AppointmentManagementServiceDoctor getInstance(Doctor doctor) {
        if (appointmentManagementServiceDoctor == null) {
            appointmentManagementServiceDoctor = new AppointmentManagementServiceDoctor(
                    AppointmentDataManager.getInstance(), doctor);
        }
        return appointmentManagementServiceDoctor;
    }

    /**
     * viewPersonalSchedule()
     * Displays the doctor's schedule for confirmed appointments.
     * If no confirmed appointments are found, it informs the user.
     * @param filteredAppointments type {@link ArrayList} for filteredAppointments to obtain confirmed appointments that are accepted by any doctor
     * getConfirmedAppointmentList() is obtained from the superclass AppointmentManagementService
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
     * acceptDecline()
     * Allows the doctor to accept or decline only pending appointments that were only requested for him. It checks for conflicting schedules
     * and updates the appointment status accordingly.
     * 
     * If the appointment is rejected by the doctor, patient needs to resubmit the request for other doctors to accept
     * @param filteredAppointments type {@link List} for filteredAppointments to obtain only pending appointments linked to that current doctor
     */
    public void acceptDecline() {
        List<Appointment> filteredAppointments = getPendingAppointmentsForDoctor(
                this.currentDoctor.getUserInformation().getID().getId());

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
     * validateAvailability()
     * Validates the availability of the doctor to ensure there are no schedule clashes with confirmed appointments.
     * 
     * @param appointment The appointment to validate for schedule clash
     * @param confirmedAppointments The list of confirmed appointments that are in the doctor's personal schedule
     * @param targetDate obtains the current pending appointment's date in dd/mm/yyyy format
     * @param targetTime obtains the current pending appointment's time HH:MM format 
     */
    private void validateAvailability(Appointment appointment, ArrayList<Appointment> confirmedAppointments) {
        String targetDate = appointment.getDateOfTreatment();
        String targetTime = appointment.getTimeOfTreatment();

        for (Appointment confirmed : confirmedAppointments) {
            if (confirmed.getDateOfTreatment().equals(targetDate)
                    && confirmed.getTimeOfTreatment().equals(targetTime)) {
                System.out.println("Schedule clash with a confirmed appointment");
                System.out.println("Appointment acceptance failed!");
                return;
            }
        }

        System.out.println("Appointment successfully accepted!");
        appointment.setAppointmentStatus(AppointmentStatus.CONFIRMED);
        System.out.println("Appointment status: "+ appointment.getAppointmentStatus().toString());
    }

    /**
     * recordOutcome()
     * Records the outcome of an appointment, including consultation notes, treatment plan and prescribed medication(s).
     * 
     * The doctor is prompted for patient ID, appointment ID, consultation notes, and prescriptions and can only record outcome 
     * an appointment where the patient is assigned under and already by the doctor. Otherwise the program will return to doctorMenu
     *
     * @param treatmentChoice to select among the treatment plans provided by doctor
     * @param medicineInput to retrieve prescription input from doctor, will be used for validating with inventory to ensure medication exist
     * @param correctMedicineName returns correct medicine name as per in the inventory list
     * @param MedicationID returns correct medicine ID number as per in the inventory list
     * @param PatientID string input for doctor to enter Patient ID
     * @param AppointmentID string input for doctor to enter the specific appointment to record outcome 
     * @param currentAppointment obtain specifc and unique appointment which is derived from getSpecificConfirmedAppointment() in the superclass AppointmentManagementService
     * 
     * Consultation Notes strings:
     * @param criticalDetails string input for doctor to enter critical information
     * @param complaints string input for doctor to enter complaints from patient
     * @param furtherInfo string input for doctor to enter extra information relating to appointment
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

                    if (selection == 0) { // exiting prescription process
                        System.out.println("Stop adding medicines, moving on ...");
                        break;
                    } else if (selection == 1) { // enter new prescription
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
                            // Update appointment outcome of prescription and send medication request to pharmacist
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
     * medicineValidator()
     * Validates the entered medicine name and checks it against the inventory through string comparison
     * 
     * @param medicineInput The name of the medicine entered by the user.
     * @param normalizedInput parse the string input into lowercase for all and delete blank spaces
     * @param medicationList type {@link List} to obtain medications from inventory list
     * @return The correct name of the medicine if it exists in the inventory, otherwise null.
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
     * findMedID()
     * Finds the medication ID for a given medicine name which is parsed correctly already.
     * It will never return null 
     * 
     * @param correctName The name of the correct medication as per in the medicine inventory list.
     * @param correctMedicineName type {@link List} to obtain medication name from inventory list
     * @return The medication ID corresponding to the given medication name.
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
