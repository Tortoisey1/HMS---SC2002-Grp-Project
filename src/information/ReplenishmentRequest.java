package information;

import entities.Pharmacist;

public class ReplenishmentRequest {
    private Pharmacist pharmacist;
    private String medicineName;
    private int amount;

    public ReplenishmentRequest(Pharmacist pharmacist, String medicineName, int amount) {
        this.pharmacist = pharmacist;
        this.medicineName = medicineName;
        this.amount = amount;
    }

    public Pharmacist getPharmacist() {
        return pharmacist;
    }

    public void setPharmacist(Pharmacist pharmacist) {
        this.pharmacist = pharmacist;
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

}
