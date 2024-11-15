package information.medical;

import enums.MedicationStatus;


/**
 * This class represents a Medication and also for request
 * It defines the getter/setters for fields {@code name}, {@code status}, {@code stock},
 * {@code appointmentId}, {@code medicationId}, {@code price}
 */
public class Medication {
    private String name;
    private MedicationStatus status;
    private int stock;
    private String appointmentId;
    private String medicationId;
    private double price;

    /**
     * Constructs a Default Constructor if there is no fields to initialize
     */
    public Medication() {
    }

    /**
     * Constructs a Default Constructor if there is no fields to initialize
     */
    public Medication(String medicationName, MedicationStatus status) {
        this.name = medicationName;
        this.status = status;
    }

    /**
     * Constructs a Medication that represents a request made to PHARMACIST by the DOCTOR after patient has been seen
     * for required medications for this appointment. {@param appointmentId} , {@param medicationId},
     * {@param status}, {@param name} is required as params for a Medication Request
     * This will be added to MedicationRequestDataManager {@link management.MedicalBillDataManager} list
     */
    public Medication(String appointmentId, String medicationId, MedicationStatus status, String name) {
        this.name = name;
        this.status = status;
        this.appointmentId = appointmentId;
        this.medicationId = medicationId;
    }

    /**
     * Constructs a Medication that represents an inventory entry gathered from {@link management.InventoryDataManager}
     * required entry for medication. {@param medicationId} , {@param name},
     * {@param stock}, {@param price} is required as params for the inventory entry.
     */
    public Medication(String medicationId, String name, int stock, double price) {
        this.medicationId = medicationId;
        this.name = name;
        this.stock = stock;
        this.price = price;
    }

    /**
     * Retrieves stock for Medication
     * @return {@code stock}
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets the {@code stock} with new {@param stock} for medication
     */
    public void setStock(int stock) {
        this.stock = stock;
    }


    /**
     * Retrieves name of Medication
     * @return {@code name}
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the {@code name} with new {@param name} for medication
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves status of Medication : DISPENSED, PENDING, COMPLETED
     * @return {@code status} with {@link MedicationStatus}
     */
    public MedicationStatus getStatus() {
        return status;
    }

    /**
     * Sets the {@code status} with new {@param status } for medication
     */
    public void setStatus(MedicationStatus status) {
        this.status = status;
    }

    /**
     * Retrieves ID of appointment that require this medication
     * @return {@code appointmentId}
     */
    public String getAppointmentId() {
        return appointmentId;
    }

    /**
     * Sets the {@code appointmentId} with new {@param appointmentId } that require this medication
     */
    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Retrieves ID of Medication
     * @return {@code medicationId}
     */
    public String getMedicationId() {
        return medicationId;
    }

    /**
     * Sets the {@code medicationId} with new {@param medicationId } for medication
     */
    public void setMedicationId(String medicationId) {
        this.medicationId = medicationId;
    }

    /**
     * Retrieves price of Medication
     * @return {@code price}
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the {@code price} with new {@param price} for medication
     */
    public void setPrice(double price) {
        this.price = price;
    }
}
