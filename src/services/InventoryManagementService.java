package services;

import java.util.ArrayList;
import java.util.List;

import exceptions.InvalidAmountException;
import exceptions.MedicineDoesNotExistException;
import exceptions.MedicineExistException;
import information.Medicine;

public class InventoryManagementService {
    private static List<Medicine> medicineList;

    public InventoryManagementService() {
        InventoryManagementService.medicineList = new ArrayList<Medicine>();
    }

    // Add a new medication to the inventory
    public void addMedication(Medicine medicine) throws MedicineExistException {
        // check if medicine already exist in the list
        Medicine checkedMedicine = findMedicine(medicine.getName());
        if (checkedMedicine == null) {
            InventoryManagementService.medicineList.add(medicine);
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
            InventoryManagementService.medicineList.remove(checkedMedicine);
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
    public static boolean isBelowLowStock(String medicineName) throws MedicineDoesNotExistException {
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

    public static Medicine findMedicine(String name) {
        for (Medicine medicine : InventoryManagementService.medicineList) {
            if (medicine.getName().equals(name)) {
                return medicine;
            }
        }
        return null;
    }
}
