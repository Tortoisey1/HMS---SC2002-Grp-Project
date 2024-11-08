package information.medical;

import enums.MedicationStatus;

public class Medication {
    private String name;
    private MedicationStatus status;
    private int stock;
    private int stockAlertLevel;
    private int lowStockLevel;
    private String appointmentId;
    private String medicationId;

    public Medication(){}

    public Medication(String appointmentId, String medicationId, MedicationStatus status, String name){
        this.name = name;
        this.status = status;
        this.appointmentId = appointmentId;
        this.medicationId = medicationId;
    }

    public Medication(String medicationId, String name, int stock){
        this.medicationId = medicationId;
        this.name = name;
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
    public String getAppointmentId() {return appointmentId;}
    public void setAppointmentId(String appointmentId) {this.appointmentId = appointmentId;}
    public String getMedicationId() {return medicationId;}
    public void setMedicationId(String medicationId) {this.medicationId = medicationId;}
    public int getLowStockLevel() {
        return lowStockLevel;
    }

    public void setLowStockLevel(int lowStockLevel) {
        this.lowStockLevel = lowStockLevel;
    }
}
