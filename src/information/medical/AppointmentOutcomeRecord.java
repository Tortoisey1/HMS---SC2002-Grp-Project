package information.medical;

import java.util.List;


/**
 * This class represents an AppointmentOutcomeRecord
 * It defines the getter/setters for fields {@code consultationNotes} and {@code medications}
 */
public class AppointmentOutcomeRecord {
    private ConsultationNotes consultationNotes;
    private List<Medication> medications;

    /**
     * Constructor define needed with ENCAPSULATED fields: {@param consultationNotes} with type {@link ConsultationNotes}
     * {@param medications} with list of {@link Medication}
     * AppointmentOutcomeRecord allow DOCTOR to update the fields required when PATIENT visited them
     */
    public AppointmentOutcomeRecord(ConsultationNotes consultationNotes,
            List<Medication> medications) {
        this.consultationNotes = consultationNotes;
        this.medications = medications;
    }


    /**
     * Retrieves all the Medications for patient for this Appointment
     * @return List of type {@link Medication}
     */
    public List<Medication> getMedications() {
        return medications;
    }

    /**
     * Sets the list of Medication with the new list {@param medications}
     */
    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }


    /**
     * Add {@param medication} to the list of Medication {@code medications}
     */
    public void addMedications(Medication medication) {
        this.medications.add(medication);
    }


    /**
     * Retrieves the ConsultationNotes for patient for this Appointment
     * @return type {@link ConsultationNotes}
     */
    public ConsultationNotes getConsultationNotes() {
        return consultationNotes;
    }

    /**
     * Sets the list of ConsultationNotes with the new list {@param consultationNotes}
     */
    public void setConsultationNotes(ConsultationNotes consultationNotes) {
        this.consultationNotes = consultationNotes;
    }
}