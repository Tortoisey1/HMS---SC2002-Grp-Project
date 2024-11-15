package information;

import enums.BloodType;
import java.util.ArrayList;
import java.util.List;


/**
 * This class represents an MedicalInformation that holds fields: past treatments and the patient's blood type
 */
public class MedicalInformation {
    private final BloodType bloodType; // Blood Type// Past Diagnoses
    private final List<Appointment> pastTreatments; // Past Treatments

    /**
     * Constructs an MedicalInformation
     * @param bloodType type {@link BloodType} of Patient
     * @code pastTreatments type {@link Appointment} for appointments that have been COMPLETED
     */
    public MedicalInformation(BloodType bloodType) {
        this.bloodType = bloodType;
        this.pastTreatments = new ArrayList<>();
    }


    /**
     * @return bloodType of patient
     */
    public BloodType getBloodType() {
        return bloodType;
    }

    /**
     * @return the list of Appointments that have been COMPLETED on patient
     */
    public List<Appointment> getPastTreatments() {
        return pastTreatments;
    }

    /**
     * Add {@code pastTreatments} with new {@param treatment} for appointment that have been COMPLETED
     */
    public void addTreatment(Appointment treatment) {
        pastTreatments.add(treatment);
    }

    /**
     * @return A string representation of the {@code pastTreatments}
     * {@code bloodType}
     */
    @Override
    public String toString() {
        return "MedicalInformation{" +
                "bloodType=" + bloodType +
                ", pastTreatments=" + pastTreatments.stream().toList() +
                '}';
    }
}
