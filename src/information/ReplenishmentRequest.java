package information;

import entities.Pharmacist;
import information.id.AdministratorID;
import information.id.PharmacistID;

public class ReplenishmentRequest {
    private AdministratorID id;
    private String medicineName;
    private int amount;
    private String dateOfRequest;
    private boolean approvalResult;

    public ReplenishmentRequest(String medicineName,
                                int amount,
                                String dateOfRequest,
                                boolean approvalResult) {
        this.medicineName = medicineName;
        this.amount = amount;
        this.dateOfRequest = dateOfRequest;
        this.approvalResult = approvalResult;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
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
}