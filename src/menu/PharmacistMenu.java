package menu;

import app.Global;
import entities.User;
import services.pharmacist.AppointmentOutcomeRecordPharmacistService;

import java.time.LocalDate;


/**
 * This menu prints and displays all the options specific to the actions of the pharmacist.
 */
public class PharmacistMenu extends Menu{
    private AppointmentOutcomeRecordPharmacistService pharmacistService;

    /**
     * Below is the main constructor for the pharmacist menu.
     * It is initialized to {@link AppointmentOutcomeRecordPharmacistService}
     */
    public PharmacistMenu(){
        this.pharmacistService = new AppointmentOutcomeRecordPharmacistService();
    }


    /**
     * Below is the printMenu function which displays the various actions/options
     * for the pharmacist that has logged in.
     * The options are 1) View Appointment Outcome Record, 2) Update Prescription Status,
     * 3) View Medication Inventory, 4) Submit Replenishment Request and 5) Logout.
     */
    @Override
    public void printMenu(User currentUser) {
        int choice;
        do {
            System.out.println("Pharmacist Menu: ");
            System.out.println("1.View Appointment Outcome Record");
            System.out.println("2.Update Prescription Status");
            System.out.println("3.View Medication Inventory");
            System.out.println("4.Submit Replenishment Request");
            System.out.println("5.Logout");
            System.out.println("Enter choice: ");
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
        }while (choice != 5);
    }

    /**
     * Below is the function related to the action of viewing the appointment outcome record
     * present in {@link AppointmentOutcomeRecordPharmacistService}
     */
    private void viewAppointmentOutcomeRecord(){
        System.out.println("Viewing appointment outcome record");
        pharmacistService.viewAppointmentOutcomeRecord();
    }

    /**
     * Below is the function related to the action of updating the prescription status
     * present in {@link AppointmentOutcomeRecordPharmacistService}
     */
    private void updatePrescriptionStatus(){
        System.out.println("Updating Prescription Status");
        pharmacistService.updatePrescriptionStatus();
    }

    /**
     * Below is the function related to the action of viewing the medication inventory
     * present in {@link AppointmentOutcomeRecordPharmacistService}
     */
    private void viewMedicationInventory(){
        System.out.println("Viewing medication inventory");
        pharmacistService.viewMedicationInventory();
    }

    /**
     * Below is the function related to the action of submitting replenishment requests when
     * stock levels are low for the administrator to approve.
     * present in {@link AppointmentOutcomeRecordPharmacistService}
     */
    private void submitReplenishmentRequest(){
        System.out.println("Submitting replenishment request");
        pharmacistService.submitReplenishmentRequest();
    }
}
