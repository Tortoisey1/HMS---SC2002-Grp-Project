package services;

import java.util.Scanner;

import enums.AppointmentStatus;
import information.medical.AppointmentOutcomeRecord;
import menu.ReplenishStockMenu;

public class PrescriptionManagementService {
    // Pharmacists can view the Appointment Outcome Record to fulfill medication
    // prescriptions orders from doctors.

    // ○ Pharmacists can update the status of prescription in the Appointment
    // Outcome
    // Record (e.g., pending to dispensed).
    public void updatePrescriptionStatus(AppointmentOutcomeRecord appointmentOutcomeRecord,AppointmentStatus appointmentStatus ){
        appointmentOutcomeRecord.set
    }

    // ○ Pharmacists can monitor the inventory of medications, including tracking
    // stock
    // levels.
    public void monitorMedicineInventory() {
        Scanner scanner = new Scanner(System.in);
        InventoryManagementService.display();// print out the inventory then choose the medicine to replenish
                                             // if
                                             // necessary

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
        // check if it exists first

    }
}
