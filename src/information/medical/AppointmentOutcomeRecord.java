package information.medical;

import java.util.List;
import enums.MedicalService;
import enums.MedicationStatus;
import information.ConsultationNotes;

public class AppointmentOutcomeRecord {
    private MedicalService medicalService;
    private ConsultationNotes consultationNotes;
    private List<Medication> medications;

    public AppointmentOutcomeRecord(MedicalService medicalService, ConsultationNotes consultationNotes,
            List<Medication> medications) {
        this.medicalService = medicalService;
        this.consultationNotes = consultationNotes;
        this.medications = medications;
        for (Medication medication : this.medications) {
            medication.setStatus(MedicationStatus.PENDING);
        }

    }

    public MedicalService getMedicalService() {
        return medicalService;
    }

    public void setMedicalService(MedicalService medicalService) {
        this.medicalService = medicalService;
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
