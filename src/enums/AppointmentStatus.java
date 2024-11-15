package enums;


/**
 * All the statuses for the Appointment
 */
public enum AppointmentStatus {
    /**
     * CONFIRMED indicates appointment is accepted by Staff
     */
    CONFIRMED,
    /**
     * CANCELLED indicates appointment is deleted by Patient
     */
    CANCELLED,
    /**
     * COMPLETED indicates appointment has been completed and updated by Doctor
     */
    COMPLETED,
    /**
     * PENDING indicates appointment has yet to be accepted by Staff
     */
    PENDING
}
