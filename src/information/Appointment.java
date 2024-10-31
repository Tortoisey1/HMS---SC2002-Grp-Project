package information;

import java.time.LocalDate;
import enums.AppointmentStatus;
import enums.MedicalService;

public class Appointment implements Comparable<Appointment> {
    private String appointmentId;
    private String dateOfTreatment;
    private String patientId;
    private AppointmentStatus status;
    private MedicalService medicalService;
    private String timeOfTreatment;
    private String doctorID;

    public Appointment(String appointmentId, String dateOfTreatment, String patientId, AppointmentStatus status,
            MedicalService medicalService, String timeOfTreatment, String doctorID) {
        this.appointmentId = appointmentId;
        this.dateOfTreatment = dateOfTreatment;
        this.patientId = patientId;
        this.status = status;
        this.medicalService = medicalService;
        this.timeOfTreatment = timeOfTreatment;
        this.doctorID = doctorID;
    }

    public String getDateOfTreatment() {
        return dateOfTreatment;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setDateOfTreatment(String dateOfTreatment) {
        this.dateOfTreatment = dateOfTreatment;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getTimeOfTreatment() {
        return timeOfTreatment;
    }

    public void setTimeOfTreatment(String timeOfTreatment) {
        this.timeOfTreatment = timeOfTreatment;
    }

    public int getMonthOfAppointment() {
        return LocalDate.parse(dateOfTreatment).getMonthValue();
    }

    public int getDateOfAppointment() {
        return LocalDate.parse(dateOfTreatment).getDayOfMonth();
    }

    @Override
    public int compareTo(Appointment other) {
        if (this.getDateOfAppointment() > other.getDateOfAppointment()) {
            return 1;
        }
        return -1;
    }

    public MedicalService getMedicalService() {
        return medicalService;
    }

    public void setMedicalService(MedicalService medicalService) {
        this.medicalService = medicalService;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

}
