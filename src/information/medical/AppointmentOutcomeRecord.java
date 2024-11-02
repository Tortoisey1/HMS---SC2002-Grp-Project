package information.medical;

import java.util.List;

import enums.MedicationStatus;

public class AppointmentOutcomeRecord {
    private ConsultationNotes consultationNotes;
    private List<Medication> medications;

    public AppointmentOutcomeRecord(ConsultationNotes consultationNotes,
            List<Medication> medications) {
        this.consultationNotes = consultationNotes;
        this.medications = medications;
        if(this.medications != null && this.medications.size() > 1){
            for (Medication medication : this.medications) {
                medication.setStatus(MedicationStatus.PENDING);
            }
        }
    }

    public List<Medication> getMedications() {
        return medications;
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }

    public ConsultationNotes getConsultationNotes() {
        return consultationNotes;
    }

    public void setConsultationNotes(ConsultationNotes consultationNotes) {
        this.consultationNotes = consultationNotes;
    }

}
