package services.administrator;

import information.ReplenishmentRequest;
import management.InventoryDataManager;
import management.ReplenishmentDataManager;

import java.io.IOException;
import java.util.List;

public class AdministratorService_Replenishment {
    private InventoryDataManager inventoryDataManager;
    private ReplenishmentDataManager replenishmentDataManager;

    public AdministratorService_Replenishment() {
        this.inventoryDataManager = InventoryDataManager.getInstance();
        this.replenishmentDataManager = (ReplenishmentDataManager) ReplenishmentDataManager.getInstance();
    }

    public void approveReplenishmentRequest(String replenishmentId, boolean isApproved) {
        ReplenishmentRequest request = replenishmentDataManager.retrieve(replenishmentId);
        if (request != null) {
            if (isApproved) {
                // Approve the replenishment request
                request.setApprovalResult(true);
                System.out.println("Replenishment request for medication: " + request.getMedicationName() +
                        " has been approved. Amount: " + request.getAmount());

                // Update inventory
                inventoryDataManager.incrementStock(request.getMedicationId(), request.getAmount());

                // Update the request status in the data manager
                replenishmentDataManager.update(request);
                try {
                    replenishmentDataManager.writeAll();
                    System.out.println("Replenishment request approval has been saved successfully.");
                } catch (IOException e) {
                    System.out.println("Error saving replenishment request: " + e.getMessage());
                }
            } else {
                // Reject the replenishment request
                request.setApprovalResult(false);
                System.out.println("Replenishment request for medication: " + request.getMedicationName() +
                        " has been rejected.");

                // Update the request status in the data manager
                replenishmentDataManager.update(request);
                try {
                    replenishmentDataManager.writeAll();
                    System.out.println("Replenishment request rejection has been saved successfully.");
                } catch (IOException e) {
                    System.out.println("Error saving replenishment request: " + e.getMessage());
                }
            }
        } else {
            System.out.println("No replenishment request found with ID: " + replenishmentId);
        }
    }
}
