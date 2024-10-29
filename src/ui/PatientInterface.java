package ui;
import merged.Patient;

public interface PatientInterface {
    void updateDetails(Patient patient, int choice, String newInput);
    Patient getDetails(String patientId);
}
