import java.util.List;

import enums.MedicationStatus;
import exceptions.InvalidAmountException;
import exceptions.MedicineDoesNotExistException;
import information.ReplenishmentRequest;
import information.id.PharmacistID;
import information.medical.AppointmentOutcomeRecord;
import information.medical.Medication;
import menu.ReplenishStockMenu;

public class PrescriptionManagementService {
    // Pharmacists can view the Appointment Outcome Record to fulfill medication
    // prescriptions orders from doctors.

    // ○ Pharmacists can update the status of prescription in the Appointment
    // Outcome
    // Record (e.g., pending to dispensed).
    public void updatePrescriptionStatus(AppointmentOutcomeRecord appointmentOutcomeRecord, String medicineName,
            MedicationStatus medicationStatus) {
        // shld dispense the medicine
        List<Medication> medicines = appointmentOutcomeRecord.getMedications();
        Medication medicine = null;
        for (Medication medication : medicines) {
            if (medication.getName().equals(medicineName)) {
                medicine = medication;
            }
        }

        if (medicine != null) {
            medicine.setStatus(medicationStatus);
        }

    }

    // ○ Pharmacists can monitor the inventory of medications, including tracking
    // stock
    // levels.
    public void monitorMedicineInventory(PharmacistID id) throws MedicineDoesNotExistException, InvalidAmountException {
        InventoryManagementService.display();// print out the inventory then choose the medicine to replenish
                                             // if
                                             // necessary

        // have access to inventory management service

        while (true) {
            ReplenishStockMenu.printMenu();

            System.out.println("Is there a medicine you would like to replenish?");
            int option = Integer.valueOf(AppLogic.getScanner().nextLine());

            switch (option) {
                case 1:
                    replenishmentRequest(id);
                    break;

                default:
                    System.out.println("Do not replenish");
                    return;
            }
        }
    }

    // ○ Pharmacists can submit replenishment requests to administrators when stock
    // levels are low.
    private void replenishmentRequest(PharmacistID id)
            throws MedicineDoesNotExistException, InvalidAmountException {
        System.out.println("Which medicine would you like to replenish");
        String medicineName = AppLogic.getScanner().nextLine();
        // check if medicine exist
        if (InventoryManagementService.findMedicine(medicineName) == null) {
            throw new MedicineDoesNotExistException(
                    "Medicine does not exist, can't remove");
        }

        int amount = Integer.valueOf(AppLogic.getScanner().nextLine());

        // check if<< amount is not infeasible
        if (amount <= 0) {
            throw new InvalidAmountException("please enter more than 0 to remove from shelf");
        }

        InventoryManagementService.getReplenishmentRequests()
                .add(new ReplenishmentRequest(id, medicineName, amount));
    }
}
