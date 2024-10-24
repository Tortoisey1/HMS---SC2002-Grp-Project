package services;

import enums.AppointmentStatus;
import exceptions.MedicineDoesNotExistException;
import information.ReplenishmentRequest;
import information.medical.AppointmentOutcomeRecord;
import menu.ReplenishStockMenu;
import services.update.UpdateReplenishmentListService;
import app.AppLogic;

public class PrescriptionManagementService {
    // Pharmacists can view the Appointment Outcome Record to fulfill medication
    // prescriptions orders from doctors.

    // ○ Pharmacists can update the status of prescription in the Appointment
    // Outcome
    // Record (e.g., pending to dispensed).
    public void updatePrescriptionStatus(AppointmentOutcomeRecord appointmentOutcomeRecord,AppointmentStatus appointmentStatus ){
// use the AppointmentOutcomeRecordPharmacistService
    }

    // ○ Pharmacists can monitor the inventory of medications, including tracking
    // stock
    // levels.
    public void monitorMedicineInventory() {
        InventoryManagementService.display();// print out the inventory then choose the medicine to replenish
                                             // if
                                             // necessary


        //have access to inventory management service

        while (true) {
            ReplenishStockMenu.printMenu();

            System.out.println("Is there a medicine you would like to replenish?");
            int option = Integer.valueOf(scanner.nextLine());

            switch (option) {
                case 1:
                    replenishmentRequest();
                    break;

                default:
                    System.out.println("Do not replenish");
                    return;
            }
        }
    }

    // ○ Pharmacists can submit replenishment requests to administrators when stock
    // levels are low.
    private void replenishmentRequest() {
        System.out.println("Which medicine would you like to replenish");
        String medicineName = AppLogic.getScanner().nextLine();
        //check if medicine exist
        if (InventoryManagementService.findMedicine(medicineName)==null) {
            throw new MedicineDoesNotExistException(
                    "Medicine does not exist, can't remove");
        }


        int amount = Integer.valueOf(AppLogic.getScanner().nextLine());

        //check if<< amount is not infeasible
        if (amount<=0) {
            throw new ;
        }

        InventoryManagementService.getReplenishmentRequest().add(new ReplenishmentRequest(pharmacist, medicineName, amount));
    }
}
