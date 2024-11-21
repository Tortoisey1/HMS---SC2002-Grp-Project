package menu;

import app.Global;
import entities.User;
import services.pharmacist.AppointmentOutcomeRecordPharmacistService;

/**
 * Represents the menu for pharmacists to interact with various functionalities related to appointment outcome records, 
 * prescription status updates, medication inventory management, and replenishment requests.
 * This menu provides pharmacists with options to perform their tasks within the system.
 */
public class PharmacistMenu extends Menu {
    
    private AppointmentOutcomeRecordPharmacistService pharmacistService;

    /**
     * Constructor to initialize the PharmacistMenu and set up the pharmacist service.
     */
    public PharmacistMenu() {
        this.pharmacistService = new AppointmentOutcomeRecordPharmacistService();
    }

    /**
     * Displays the pharmacist menu with various options and handles user input for the selected choice.
     * It allows the pharmacist to view appointment outcome records, update prescription statuses,
     * view medication inventory, submit replenishment requests, or log out.
     * 
     * @param currentUser The current logged-in user, expected to be a `User` object, but not used in this method.
     */
    @Override
    public void printMenu(User currentUser) {
        int choice;
        do {
            System.out.println("Pharmacist Menu: ");
            System.out.println("1. View Appointment Outcome Record");
            System.out.println("2. Update Prescription Status");
            System.out.println("3. View Medication Inventory");
            System.out.println("4. Submit Replenishment Request");
            System.out.println("5. Logout");
            System.out.print("Enter choice: ");
            choice = Integer.parseInt(Global.getScanner().nextLine());
            switch (choice) {
                case 1:
                    viewAppointmentOutcomeRecord();
                    break;
                case 2:
                    updatePrescriptionStatus();
                    break;
                case 3:
                    viewMedicationInventory();
                    break;
                case 4:
                    submitReplenishmentRequest();
                    break;
                case 5:
                    System.out.println("Logging out");
                    break;
                default:
                    System.out.println("Choice is invalid. Enter again.");
            }
        } while (choice != 5); // Loops until the user logs out
    }

    /**
     * Handles the option to view the appointment outcome record by invoking the appropriate service.
     */
    private void viewAppointmentOutcomeRecord() {
        System.out.println("Viewing appointment outcome record");
        pharmacistService.viewAppointmentOutcomeRecord();
    }

    /**
     * Handles the option to update the prescription status by invoking the appropriate service.
     */
    private void updatePrescriptionStatus() {
        System.out.println("Updating Prescription Status");
        pharmacistService.updatePrescriptionStatus();
    }

    /**
     * Handles the option to view the medication inventory by invoking the appropriate service.
     */
    private void viewMedicationInventory() {
        System.out.println("Viewing medication inventory");
        pharmacistService.viewMedicationInventory();
    }

    /**
     * Handles the option to submit a replenishment request by invoking the appropriate service.
     */
    private void submitReplenishmentRequest() {
        System.out.println("Submitting replenishment request");
        pharmacistService.submitReplenishmentRequest();
    }
}
