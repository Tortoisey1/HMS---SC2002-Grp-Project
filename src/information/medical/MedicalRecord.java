package information.medical;

import java.util.List;

public class MedicalRecord {
    private String diagnosis;
    private String treatmentPlan;
    private List<String> medications;

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    @Override
    public String toString() {
        return "Diagnosis: " + diagnosis;
    }

    public String getTreatmentPlan() {
        return treatmentPlan;
    }

    public void setTreatmentPlan(String treatmentPlan) {
        this.treatmentPlan = treatmentPlan;
    }

    public List<String> getMedications() {
        return medications;
    }

    public void setMedications(List<String> medications) {
        this.medications = medications;
    }

    public void addMedication(String medicationName) {
        this.medications.add(medicationName);
    }

}
