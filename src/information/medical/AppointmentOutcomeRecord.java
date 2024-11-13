package information.medical;

import java.util.List;

import enums.AppointmentStatus;
import enums.MedicationStatus;

public class AppointmentOutcomeRecord {
    private ConsultationNotes consultationNotes;
    private List<Medication> medications;

    public AppointmentOutcomeRecord(ConsultationNotes consultationNotes,
            List<Medication> medications) {
        this.consultationNotes = consultationNotes;
        this.medications = medications;
    }

    public List<Medication> getMedications() {
        return medications;
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }

    public void addMedications(Medication medication) {
        this.medications.add(medication);
    }

    public ConsultationNotes getConsultationNotes() {
        return consultationNotes;
    }

    public void setConsultationNotes(ConsultationNotes consultationNotes) {
        this.consultationNotes = consultationNotes;
    }
}