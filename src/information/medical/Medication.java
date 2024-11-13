package information.medical;

import enums.MedicationStatus;

public class Medication {
    private String name;
    private MedicationStatus status;
    private int stock;
    private String appointmentId;
    private String medicationId;
    private double price;

    public Medication() {
    }

    public Medication(String medicationName, MedicationStatus status) {
        this.name = medicationName;
        this.status = status;
    }

    public Medication(String appointmentId, String medicationId, MedicationStatus status, String name) {
        this.name = name;
        this.status = status;
        this.appointmentId = appointmentId;
        this.medicationId = medicationId;
    }

    public Medication(String medicationId, String name, int stock, double price) {
        this.medicationId = medicationId;
        this.name = name;
        this.stock = stock;
        this.price = price;
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

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(String medicationId) {
        this.medicationId = medicationId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
