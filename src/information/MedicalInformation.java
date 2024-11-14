package information;

import enums.BloodType;
import java.util.ArrayList;
import java.util.List;

public class MedicalInformation {
    private final BloodType bloodType; // Blood Type// Past Diagnoses
    private final List<Appointment> pastTreatments; // Past Treatments

    public MedicalInformation(BloodType bloodType) {
        this.bloodType = bloodType;
        this.pastTreatments = new ArrayList<>();
    }

    // Getters and Setters

    public BloodType getBloodType() {
        return bloodType;
    }

    public List<Appointment> getPastTreatments() {
        return pastTreatments;
    }

    public void addTreatment(Appointment treatment) {
        pastTreatments.add(treatment);
    }

    @Override
    public String toString() {
        return "MedicalInformation{" +
                "bloodType=" + bloodType +
                ", pastTreatments=" + pastTreatments.stream().toList() +
                '}';
    }
}
