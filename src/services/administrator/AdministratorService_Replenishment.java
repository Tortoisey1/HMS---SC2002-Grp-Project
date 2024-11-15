package services.administrator;

import java.util.List;

import information.medical.Medication;
import management.InventoryDataManager;

public class AdministratorService_Replenishment {
  private InventoryDataManager inventoryDataManager;

  public AdministratorService_Replenishment() {
    this.inventoryDataManager = InventoryDataManager.getInstance();
  }

  public void approveReplenishmentRequest(String replenishmentId, boolean isapproved) {
    System.out.println("Checking inventory for low stock items...");
    List<Medication> inventoryList = inventoryDataManager.getList();
    for (Medication medication : inventoryList) {
      if (medication.getStock() < 10) {
        System.out.println("Replenishment request submitted for: " + medication.getName() +
            ", Current Stock: " + medication.getStock());
      }
    }
  }
}
