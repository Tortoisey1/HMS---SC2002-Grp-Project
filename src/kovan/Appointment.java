import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class Appointment implements Comparable<Appointment> {
    private String appointmentId;
    private String dateOfTreatment;
    private String patientId;
    private AppointmentStatus status;
    private String treatmentTitle;
    private String timeOfTreatment;
    private String doctor;


    public Appointment(String appointmentId,
                       String patientId,
                       AppointmentStatus status,
                       String dateOfTreatment,
                       String treatmentTitle,
                       String timeOfTreatment,
                       String doctor){
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.status = status;
        this.dateOfTreatment = dateOfTreatment;
        this.treatmentTitle = treatmentTitle;
        this.timeOfTreatment = timeOfTreatment;
        this.doctor = doctor;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getDateOfTreatment() {
        return dateOfTreatment;
    }

    public String getPatientId() {
        return patientId;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setDateOfTreatment(String dateOfTreatment) {
        this.dateOfTreatment = dateOfTreatment;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public String getTreatmentTitle() {
        return treatmentTitle;
    }

    public void setTreatmentTitle(String treatmentTitle) {
        this.treatmentTitle = treatmentTitle;
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

    public int getMonthOfAppointment(){
        return LocalDate.parse(dateOfTreatment).getMonthValue();
    }
    public int getDateOfAppointment(){
        return LocalDate.parse(dateOfTreatment).getDayOfMonth();
    }

    @Override
    public int compareTo(Appointment other) {
        if(this.getDateOfAppointment() > other.getDateOfAppointment()){
            return 1;
        }
        return -1;
    }
}

