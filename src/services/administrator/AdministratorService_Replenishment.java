package services.administrator;

import information.ReplenishmentRequest;
import management.InventoryDataManager;
import management.ReplenishmentDataManager;

import java.io.IOException;
import java.util.List;

/**
 * AdministratorService_Replenishment
 * Provides functionalities for administrators to manage replenishment requests.
 * This service allows approving or rejecting requests and updating inventory
 * accordingly.
 */
public class AdministratorService_Replenishment {

    private InventoryDataManager inventoryDataManager;
    private ReplenishmentDataManager replenishmentDataManager;

    /**
     * Constructor to initialize the AdministratorService_Replenishment.
     * Retrieves instances of the inventory and replenishment data managers.
     */
    public AdministratorService_Replenishment() {
        this.inventoryDataManager = InventoryDataManager.getInstance();
        this.replenishmentDataManager = (ReplenishmentDataManager) ReplenishmentDataManager.getInstance();
    }

    /**
     * Approves or rejects a replenishment request based on the given ID and
     * approval status.
     * Updates the inventory stock if the request is approved and saves the updated
     * status.
     *
     * @param replenishmentId The ID of the replenishment request to process.
     * @param isApproved      True to approve the request, false to reject it.
     */
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
                saveReplenishmentData();
            } else {
                // Reject the replenishment request
                request.setApprovalResult(false);
                System.out.println("Replenishment request for medication: " + request.getMedicationName() +
                        " has been rejected.");

                // Update the request status in the data manager
                replenishmentDataManager.update(request);
                saveReplenishmentData();
            }
        } else {
            System.out.println("No replenishment request found with ID: " + replenishmentId);
        }
    }

    /**
     * Saves the updated replenishment requests to persistent storage.
     * Handles exceptions during the saving process.
     */
    private void saveReplenishmentData() {
        try {
            replenishmentDataManager.writeAll();
            System.out.println("Replenishment request status has been saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving replenishment request: " + e.getMessage());
        }
    }
}
