package information;

import enums.BloodType;
import java.util.ArrayList;
import java.util.List;

public class MedicalInformation {
    private BloodType bloodType; // Blood Type
    private List<String> pastDiagnoses; // Past Diagnoses
    private List<String> pastTreatments; // Past Treatments

    public MedicalInformation(BloodType bloodType) {
        this.bloodType = bloodType;
        this.pastDiagnoses = new ArrayList<>();
        this.pastTreatments = new ArrayList<>();
    }

    // Getters and Setters

    public BloodType getBloodType() {
        return bloodType;
    }

    public List<String> getPastDiagnoses() {
        return pastDiagnoses;
    }

    public void addDiagnosis(String diagnosis) {
        pastDiagnoses.add(diagnosis);
    }

    public List<String> getPastTreatments() {
        return pastTreatments;
    }

    public void addTreatment(String treatment) {
        pastTreatments.add(treatment);
    }
}
