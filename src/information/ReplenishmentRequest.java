package information;

import entities.Pharmacist;
import information.id.AdministratorID;
import information.id.PharmacistID;

public class ReplenishmentRequest {
    private AdministratorID id;
    private String medicationName;
    private int amount;
    private String dateOfRequest;
    private boolean approvalResult;
    private String replenishmentId;
    private String medicationId;


    public ReplenishmentRequest(){}

    public ReplenishmentRequest(
            String replenishmentId,
            String medicationId,
            String medicationName,
            int amount,
            AdministratorID id,
            String dateOfRequest
    ){
        this.replenishmentId = replenishmentId;
        this.medicationId = medicationId;
        this.medicationName = medicationName;
        this.amount = amount;
        this.id = id;
        this.dateOfRequest = dateOfRequest;
    }


    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public String getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(String medicationId) {
        this.medicationId = medicationId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public AdministratorID getId() {
        return id;
    }

    public void setId(AdministratorID id) {
        this.id = id;
   }

    public String getDateOfRequest() {
        return dateOfRequest;
    }

    public void setDateOfRequest(String dateOfRequest) {
        this.dateOfRequest = dateOfRequest;
    }

    public boolean isApprovalResult() {
        return approvalResult;
    }

    public void setApprovalResult(boolean approvalResult) {
        this.approvalResult = approvalResult;
    }

    public String getReplenishmentId() {
        return replenishmentId;
    }

    public void setReplenishmentId(String replenishmentId) {
        this.replenishmentId = replenishmentId;
    }
}