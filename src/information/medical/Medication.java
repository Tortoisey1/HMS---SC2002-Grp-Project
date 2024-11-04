package information.medical;

import enums.MedicationStatus;

public class Medication {
    private String name;
    private MedicationStatus status;
    private int stock;
    private int stockAlertLevel;


    public Medication(){}

    public Medication(String name, MedicationStatus status){
        this.name = name;
        this.status = status;
    }

    public Medication(String name,MedicationStatus status, int stock){
        this.name = name;
        this.status = status;
        this.stock = stock;
    }

    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public MedicationStatus getStatus() {
        return status;
    }
    public void setStatus(MedicationStatus status) {
        this.status = status;
    }
    
}
