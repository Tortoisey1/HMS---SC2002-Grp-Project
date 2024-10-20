package information;

import enums.BloodType;
import information.medical.Diagnosis;
import information.medical.Treatment;

import java.util.ArrayList;
import java.util.List;

public class MedicalInformation {
    private final BloodType bloodType; // Blood Type
    private List<Diagnosis> pastDiagnoses; // Past Diagnoses
    private List<Treatment> pastTreatments; // Past Treatments

    public MedicalInformation(BloodType bloodType) {
        this.bloodType = bloodType;
        this.pastDiagnoses = new ArrayList<>();
        this.pastTreatments = new ArrayList<>();
    }

    // Getters and Setters

    public BloodType getBloodType() {
        return bloodType;
    }

    public List<Diagnosis> getPastDiagnoses() {
        return pastDiagnoses;
    }

    public void addDiagnosis(Diagnosis diagnosis) {
        pastDiagnoses.add(diagnosis);
    }

    public List<Treatment> getPastTreatments() {
        return pastTreatments;
    }

    public void addTreatment(Treatment treatment) {
        pastTreatments.add(treatment);
    }
}
