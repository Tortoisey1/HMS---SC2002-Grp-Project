package services;

import exceptions.InvalidAmountException;
import exceptions.MedicineDoesNotExistException;
import information.Medicine;

public class MedicineUpdateService {

    // Update stock levels
    public void updateStock(String medicineName, int amount)
            throws MedicineDoesNotExistException, InvalidAmountException {

        // check if medicine already exist in the list
        Medicine checkedMedicine = InventoryManagementService.findMedicine(medicineName);
        if (checkedMedicine != null) {
            int newStockLevel = checkedMedicine.getCurrentStock() + amount;
            if (newStockLevel < 0) { // cant update the stock since not enough if removing
                throw new InvalidAmountException("There is not enough medicine to be removed");
            }

            checkedMedicine.setCurrentStock(newStockLevel);
            InventoryManagementService.isBelowLowStock(medicineName);
        } else {
            throw new MedicineDoesNotExistException(
                    "Medicine does not exist, can't remove");
        }
    }

    // Update the low stock level for a medication
    public void updateLowStockLevelAlert(String medicineName, int newLowStockLevel)
            throws InvalidAmountException, MedicineDoesNotExistException {
        if (newLowStockLevel <= 0) {
            throw new InvalidAmountException("Can't change stock level to 0 or below");
        }

        // check if medicine already exist in the list
        Medicine checkedMedicine = InventoryManagementService.findMedicine(medicineName);
        if (checkedMedicine != null) {
            checkedMedicine.setAlertStockLvl(newLowStockLevel);
        } else {
            throw new MedicineDoesNotExistException(
                    "Medicine does not exist, can't remove");
        }
    }
}
