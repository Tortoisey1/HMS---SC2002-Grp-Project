package menu;

import app.Global;
import entities.Doctor;
import entities.User;
import exceptions.InvalidChoiceException;
import services.doctor.AppointmentManagementServiceDoctor;
import services.doctor.MedicalRecordManagementServiceDoctor;

/**
 * DoctorMenu() 
 * Represents the menu for doctors to interact with various services, such as viewing patient medical records,
 * managing personal schedules, accepting or declining appointments, and recording appointment outcomes.
 * This class extends the `Menu` class and provides doctor-specific functionalities.
 */
public class DoctorMenu extends Menu {

    private MedicalRecordManagementServiceDoctor medicalRecordService;

    /**
     * Constructor to initialize the DoctorMenu. Sets up the medical record service.
     * Appointment service only sets up when the doctor wants to open that service
     */
    public DoctorMenu() {
        this.medicalRecordService = new MedicalRecordManagementServiceDoctor();
    }

    /**
     * Displays the Doctor menu and allows the doctor to interact with the various available options.
     * The doctor can choose from viewing patient medical records, managing their schedule, accepting or declining appointments,
     * recording appointment outcomes, or logging out.
     * 
     * @param currentUser The current user who is logged in, expected to be a `Doctor` object.
     * @param choice records input for doctor's choice of service
     */
    @Override
    public void printMenu(User currentUser) {
        int choice = -1;
        do {
            printOptions(); 
            try {
                choice = Integer.valueOf(Global.getScanner().nextLine());
                callService(choice, (Doctor) currentUser); // Call the appropriate service based on choice
            } catch (InvalidChoiceException e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (choice != 5); // 5 = Logout
    }

    /**
     * Prints the available options in the Doctor menu.
     * This includes options to view medical records, manage the schedule, accept/decline appointments,
     * record appointment outcomes, or log out.
     */
    private void printOptions() {
        System.out.println();
        System.out.println("===== Doctor Menu =====");
        System.out.println("1. View Patient Medical Records");
        System.out.println("2. View Personal Schedule");
        System.out.println("3. Accept or Decline Appointment Requests");
        System.out.println("4. Record Appointment Outcome");
        System.out.println("5. Logout");
        System.out.println("Enter choice:");
    }

    /**
     * Calls the appropriate service based on the user's choice in the Doctor menu.
     * The service corresponds to the selected option, which could involve viewing medical records, managing schedules,
     * accepting/declining appointments, or recording outcomes.
     * 
     * @param choice The choice selected by the doctor in the menu.
     * @param doctor The current doctor object that will be used to access specific services.
     * @throws InvalidChoiceException If the choice is invalid, an exception will be thrown.
     */
    private void callService(int choice, Doctor doctor) throws InvalidChoiceException {
        switch (choice) {
            case 1 -> medicalRecordService.viewMedicalRecords(doctor); // View medical records
            case 2 -> AppointmentManagementServiceDoctor.getInstance(doctor).viewPersonalSchedule(); // View personal schedule
            case 3 -> AppointmentManagementServiceDoctor.getInstance(doctor).acceptDecline(); // Accept or decline appointments
            case 4 -> AppointmentManagementServiceDoctor.getInstance(doctor).recordOutcome(); // Record appointment outcome
            case 5 -> System.out.println("Logging out..."); // Log out
            default -> throw new InvalidChoiceException("Invalid choice, please try again."); // Handle invalid choice
        }
    }
}
