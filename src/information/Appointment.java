package information;

import enums.AppointmentStatus;
import enums.MedicalService;
import information.id.DoctorID;
import information.id.PatientID;
import information.medical.AppointmentOutcomeRecord;
import java.time.LocalDate;

public class Appointment implements Comparable<Appointment> {
    private PatientID patientId;
    private DoctorID doctorId;
    private AppointmentStatus appointmentStatus;
    private String timeOfTreatment;
    private MedicalService medicalService;
    private String dateOfTreatment;
    private AppointmentOutcomeRecord appointmentOutcomeRecord;
    // from kovan
    private String appointmentID;
    private String doctorName;



    public Appointment(PatientID patientId,
                       DoctorID doctorId,
                       AppointmentStatus appointmentStatus,
                       AppointmentOutcomeRecord appointmentOutcomeRecord,
                       String timeOfTreatment,
                       String dateOfTreatment,
                       MedicalService medicalService,
                       String appointmentId,
                       String doctorName

    ) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentStatus = appointmentStatus;
        this.appointmentOutcomeRecord = appointmentOutcomeRecord;
        this.timeOfTreatment = timeOfTreatment;
        this.dateOfTreatment = dateOfTreatment;
        this.medicalService = medicalService;
        this.appointmentID = appointmentId;
        this.doctorName = doctorName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public PatientID getPatientId() {
        return patientId;
    }

    public void setPatientId(PatientID patientId) {
        this.patientId = patientId;
    }

    public DoctorID getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(DoctorID doctorId) {
        this.doctorId = doctorId;
    }

    public AppointmentStatus getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public MedicalService getTreatmentTitle() {
        return medicalService;
    }

    public String getTimeOfTreatment() {
        return timeOfTreatment;
    }

    public void setTimeOfTreatment(String timeOfTreatment) {
        this.timeOfTreatment = timeOfTreatment;
    }

    public AppointmentOutcomeRecord getAppointmentOutcomeRecord() {
        return appointmentOutcomeRecord;
    }

    public void setAppointmentOutcomeRecord(AppointmentOutcomeRecord appointmentOutcomeRecord) {
        this.appointmentOutcomeRecord = appointmentOutcomeRecord;
    }

    public String getDateOfTreatment() {
        return dateOfTreatment;
    }

    public void setDateOfTreatment(String dateOfTreatment) {
        this.dateOfTreatment = dateOfTreatment;
    }
    public String getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
    }

    public int getMonthOfAppointment(){
        return LocalDate.parse(dateOfTreatment).getMonthValue();
    }

    public int getDateOfAppointment(){
        return LocalDate.parse(dateOfTreatment).getDayOfMonth();
    }
    @Override
    public int compareTo(Appointment other) {
        if (this.getDateOfAppointment() > other.getDateOfAppointment()) {
            return 1;
        }
        return -1;
    }

}
