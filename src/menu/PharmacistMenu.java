package menu;

import app.Global;
import entities.User;
import services.pharmacist.AppointmentOutcomeRecordPharmacistService;

public class PharmacistMenu extends Menu{
    private AppointmentOutcomeRecordPharmacistService pharmacistService;
    public PharmacistMenu(){
        this.pharmacistService = new AppointmentOutcomeRecordPharmacistService();
    }

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
    private void viewAppointmentOutcomeRecord(){
        System.out.println("Viewing appointment outcome record");
        pharmacistService.viewAppointmentOutcomeRecord();
    }
    private void updatePrescriptionStatus(){
        System.out.println("Updating Prescription Status");
        pharmacistService.updatePrescriptionStatus();
    }
    private void viewMedicationInventory(){
        System.out.println("Viewing medication inventory");
        pharmacistService.viewMedicationInventory();
    }
    private void submitReplenishmentRequest(){
        System.out.println("Submitting replenishment request");
        pharmacistService.submitReplenishmentRequest();
    }
}
