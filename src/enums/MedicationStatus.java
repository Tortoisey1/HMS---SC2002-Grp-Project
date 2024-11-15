package enums;


/**
 * All the MedicationStatus for the MedicationRequest
 */
public enum MedicationStatus {
    /**
     * PENDING indicates Medication has yet to be DISPENSED
     */
    PENDING,
    /**
     * DISPENSED indicates requested Medication has been DISPENSED
     */
    DISPENSED,
    /**
     * COMPLETED indicates completion of request
     */
    COMPLETED
}
