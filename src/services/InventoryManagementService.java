

import java.util.ArrayList;
import java.util.List;

import javax.naming.InsufficientResourcesException;

import exceptions.MedicineDoesNotExistException;
import exceptions.MedicineExistException;
import information.Medicine;
import information.ReplenishmentRequest;

public class InventoryManagementService {
    //whoever is touching this do realise that i shift the list into a medicinedata management so help me change thx
    private static List<Medicine> medicineList;
    private static List<ReplenishmentRequest> replenishmentRequests;

    public InventoryManagementService() {
        InventoryManagementService.medicineList = new ArrayList<Medicine>();
    }

    // Add a new medication to the inventory
    public static void addMedication(Medicine medicine) throws MedicineExistException {
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
    public static void removeMedication(String medicineName) throws MedicineDoesNotExistException {
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
    public static void approveReplenishmentRequest()
            throws InsufficientResourcesException, MedicineDoesNotExistException {
        // approve the first one first
        ReplenishmentRequest request = InventoryManagementService.replenishmentRequests.get(0);

        Medicine medicine = InventoryManagementService.findMedicine(request.getMedicineName());

        int newStockAmount = medicine.getCurrentStock() + request.getAmount();

        if (newStockAmount <= 0) {
            throw new InsufficientResourcesException("Not enough medicine");
        } else {
            medicine.setCurrentStock(newStockAmount);
            InventoryManagementService.isBelowLowStock(medicine.getName());
        }

        InventoryManagementService.getReplenishmentRequests().remove(0);
    }

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

    public static void display() {
        // no medicine in the hospital
        if (InventoryManagementService.getMedicineList() == null) {
            System.out.println("No medicine");
            return;
        }

        StringBuilder str = new StringBuilder();
        for (Medicine medicine : InventoryManagementService.medicineList) {
            str.append(medicine + "\n");
        }
        System.out.println(str.toString());
    }

    public static List<Medicine> getMedicineList() {
        return medicineList;
    }

    public static List<ReplenishmentRequest> getReplenishmentRequests() {
        return replenishmentRequests;
    }
}
