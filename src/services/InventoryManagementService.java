package services;

import java.util.ArrayList;
import java.util.List;

import exceptions.InvalidAmountException;
import exceptions.MedicineDoesNotExistException;
import exceptions.MedicineExistException;
import information.Medicine;

public class InventoryManagementService {
    private List<Medicine> medicineList;

    public InventoryManagementService() {
        this.medicineList = new ArrayList<Medicine>();
    }

    // Add a new medication to the inventory
    public void addMedication(Medicine medicine) throws MedicineExistException {
        // check if medicine already exist in the list
        Medicine checkedMedicine = findMedicine(medicine.getName());
        if (checkedMedicine == null) {
            this.medicineList.add(medicine);
        } else {
            throw new MedicineExistException(
                    "Medicine already exists please choose update stock to increase stock level");
        }
    }

    // Remove medication from the inventory
    public void removeMedication(String medicineName) throws MedicineDoesNotExistException {
        // check if medicine already exist in the list
        Medicine checkedMedicine = findMedicine(medicineName);
        if (checkedMedicine != null) {
            this.medicineList.remove(checkedMedicine);
        } else {
            throw new MedicineDoesNotExistException(
                    "Medicine does not exist, can't remove");
        }
    }

    // Update stock levels
    public void updateStock(String medicineName, int amount)
            throws MedicineDoesNotExistException, InvalidAmountException {

        // check if medicine already exist in the list
        Medicine checkedMedicine = findMedicine(medicineName);
        if (checkedMedicine != null) {
            int newStockLevel = checkedMedicine.getCurrentStock() + amount;
            if (newStockLevel < 0) { // cant update the stock since not enough if removing
                throw new InvalidAmountException("There is not enough medicine to be removed");
            }

            checkedMedicine.setCurrentStock(newStockLevel);
            isBelowLowStock(medicineName);
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
        Medicine checkedMedicine = findMedicine(medicineName);
        if (checkedMedicine != null) {
            checkedMedicine.setAlertStockLvl(newLowStockLevel);
        } else {
            throw new MedicineDoesNotExistException(
                    "Medicine does not exist, can't remove");
        }
    }

    // Approve replenishment request and update stock
    // public void approveReplenishmentRequest(String medicationName, int quantity)
    // {
    // if (stock.containsKey(medicationName)) {
    // int currentStock = stock.get(medicationName);
    // stock.put(medicationName, currentStock + quantity);
    // } else {
    // System.out.println("Medication not found in inventory");
    // }
    // }

    // Check if medication is below low stock level
    public boolean isBelowLowStock(String medicineName) throws MedicineDoesNotExistException {
        // check if medicine already exist in the list
        Medicine checkedMedicine = findMedicine(medicineName);
        if (checkedMedicine != null) {
            if (checkedMedicine.getCurrentStock() <= checkedMedicine.getAlertStockLvl()) {
                System.out.println("Stock level is running low please add more medicine");
                return true;
            }
        } else {
            throw new MedicineDoesNotExistException(
                    "Medicine does not exist, can't remove");
        }

        return false;
    }

    public Medicine findMedicine(String name) {
        for (Medicine medicine : this.medicineList) {
            if (medicine.getName().equals(name)) {
                return medicine;
            }
        }
        return null;
    }
}
