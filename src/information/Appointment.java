package information;

import enums.AppointmentStatus;
import enums.MedicalService;
import information.id.DoctorID;
import information.id.PatientID;
import information.medical.AppointmentOutcomeRecord;
import java.time.LocalDate;


/**
 * This class represents an Appointment that implements {@link Comparable} to compare dates.
 * Appointment is always created when PATIENT schedules appointment
 */

public class Appointment implements Comparable<Appointment> {
    private PatientID patientId;
    private DoctorID doctorId;
    private AppointmentStatus appointmentStatus;
    private String timeOfTreatment;
    private MedicalService medicalService;
    private String dateOfTreatment;
    private AppointmentOutcomeRecord appointmentOutcomeRecord;
    private String appointmentID;
    private String doctorName;

    /**
     * Constructs an Appointment
     * @param patientId type {@link PatientID} for ID of Patient
     * @param doctorId type {@link DoctorID} for ID of Doctor
     * @param appointmentStatus type {@link AppointmentStatus} for Status
     * @param appointmentOutcomeRecord type {@link AppointmentOutcomeRecord} for Consultation Notes
     * @param timeOfTreatment for the time slot selected
     * @param dateOfTreatment type {@link String} for the date of appointment
     * @param medicalService type {@link MedicalService} for the type of service
     * @param appointmentId for ID of Appointment
     * @param doctorName for name of Doctor
     */

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

    /**
     * @return name of doctor
     */
    public String getDoctorName() {
        return doctorName;
    }

    /**
     * Sets {@code doctorName} with new {@param doctorName}
     */
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    /**
     * @return id of Patient for this appointment
     */
    public PatientID getPatientId() {
        return patientId;
    }

    /**
     * Sets {@code patientId} with new {@param patientId}
     */
    public void setPatientId(PatientID patientId) {
        this.patientId = patientId;
    }

    /**
     * @return id of Doctor
     */
    public DoctorID getDoctorId() {
        return doctorId;
    }

    /**
     * Sets {@code doctorId} with new {@param doctorId}
     */
    public void setDoctorId(DoctorID doctorId) {
        this.doctorId = doctorId;
    }

    /**
     * @return status of this Appointment
     */
    public AppointmentStatus getAppointmentStatus() {
        return appointmentStatus;
    }

    /**
     * Sets {@code appointmentStatus} with new {@param appointmentStatus}
     */
    public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    /**
     * @return medical service for this Appointment
     */
    public MedicalService getTreatmentTitle() {
        return medicalService;
    }

    /**
     * @return time slot of this Appointment
     */
    public String getTimeOfTreatment() {
        return timeOfTreatment;
    }

    /**
     * Sets {@code timeOfTreatment} with new {@param timeOfTreatment}
     */

    public void setTimeOfTreatment(String timeOfTreatment) {
        this.timeOfTreatment = timeOfTreatment;
    }

    /**
     * @return AppointmentOutcome of this Appointment
     */
    public AppointmentOutcomeRecord getAppointmentOutcomeRecord() {
        return appointmentOutcomeRecord;
    }

    /**
     * Sets {@code appointmentOutcomeRecord} with new {@param appointmentOutcomeRecord}
     */
    public void setAppointmentOutcomeRecord(AppointmentOutcomeRecord appointmentOutcomeRecord) {
        this.appointmentOutcomeRecord = appointmentOutcomeRecord;
    }
    /**
     * @return date of this Appointment
     */
    public String getDateOfTreatment() {
        return dateOfTreatment;
    }

    /**
     * Sets {@code dateOfTreatment} with new {@param dateOfTreatment}
     */
    public void setDateOfTreatment(String dateOfTreatment) {
        this.dateOfTreatment = dateOfTreatment;
    }

    /**
     * @return id of this Appointment
     */
    public String getAppointmentID() {
        return appointmentID;
    }

    /**
     * Sets {@code appointmentID} with new {@param appointmentID}
     */
    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
    }


    /**
     * Uses {@link LocalDate} to parse the {@code dateOfTreatment} and get the month
     * @return month in type {@link int}
     * Used in {@link menu.CustomCalendar}
     */
    public int getMonthOfAppointment() {
        return LocalDate.parse(dateOfTreatment).getMonthValue();
    }

    /**
     * Uses {@link LocalDate} to parse the {@code dateOfTreatment} and get the date of the month
     * @return date in type {@link int}
     * Used in {@link menu.CustomCalendar}
     */
    public int getDateOfAppointment() {
        return LocalDate.parse(dateOfTreatment).getDayOfMonth();
    }


    /**
     * Compare date of Appointment of current appointment to date from another Appointment
     * @return 1 to sort in ascending order
     * to sort the dates in a list used in Calendar {@link menu.CustomCalendar}
     */
    @Override
    public int compareTo(Appointment other) {
        if (this.getDateOfAppointment() > other.getDateOfAppointment()) {
            return 1;
        }
        return -1;
    }

    /**
     * Sets {@code medicalService} with new {@param medicalService}
     */
    public void setMedicalService(MedicalService medicalService) {
        this.medicalService = medicalService;
    }

    /**
     * @return A string representation of the {@code patientId}
     * {@code doctorId}, {@code appointmentStatus},
     * {@code timeOfTreatment}, {@code medicalService},
     * {@code dateOfTreatment}, {@code appointmentOutcomeRecord},
     * {@code appointmentID}, {@code doctorName},
     */
    @Override
    public String toString() {
        return "Appointment{" +
                "patientId=" + patientId +
                ", doctorId=" + doctorId +
                ", appointmentStatus=" + appointmentStatus +
                ", timeOfTreatment='" + timeOfTreatment + '\'' +
                ", medicalService=" + medicalService +
                ", dateOfTreatment='" + dateOfTreatment + '\'' +
                ", appointmentOutcomeRecord=" + appointmentOutcomeRecord +
                ", appointmentID='" + appointmentID + '\'' +
                ", doctorName='" + doctorName + '\'' +
                '}';
    }
}
