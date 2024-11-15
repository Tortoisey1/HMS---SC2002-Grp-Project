package information;
import enums.AppointmentStatus;
import enums.MedicalService;
import enums.TransactionStatus;
import information.id.DoctorID;
import information.id.PatientID;
import information.medical.AppointmentOutcomeRecord;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * This class represents a Medical Bill or Transaction Receipt
 * Used when issuing bills to patient for payment
 */
public class MedicalBill {
    private String transactionRef;
    private TransactionStatus transactionStatus;
    private long totalFee;
    private String appointmentId;
    private String dateOfAppointment;
    private boolean moreThanOneMonth;
    private PatientID patientID;

    /**
     * Constructs a Transaction Receipt
     * @param patientID type {@link PatientID} for ID of Patient
     * @param ref for the transaction reference
     * @param transactionStatus type {@link TransactionStatus} for Status
     * @param totalFee for total fee: Consultation + medications
     * @param dateOfAppointment for the date of appointment
     * @param appointmentId for ID of Appointment
     */
    public MedicalBill(String ref,
                       PatientID patientID,
                       TransactionStatus transactionStatus,
                       long totalFee,
                       String appointmentId,
                       String dateOfAppointment
                       ){
        this.transactionRef = ref;
        this.patientID = patientID;
        this.transactionStatus = transactionStatus;
        this.totalFee = totalFee;
        this.appointmentId = appointmentId;
        this.dateOfAppointment = dateOfAppointment;
    }

    /**
     * @return id of this Appointment
     */
    public String getAppointmentId() {
        return appointmentId;
    }

    /**
     * Sets {@code appointmentId} with new {@param appointmentId}
     */
    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * @return transaction reference of this bill
     */
    public String getTransactionRef() {
        return transactionRef;
    }

    /**
     * Sets {@code transactionRef} with new {@param transactionRef}
     */
    public void setTransactionRef(String transactionRef) {
        this.transactionRef = transactionRef;
    }

    /**
     * @return total fee of this bill
     */
    public long getTotalFee() {
        return totalFee;
    }

    /**
     * Sets {@code totalFee} with new {@param totalFee}
     */
    public void setTotalFee(long totalFee) {
        this.totalFee = totalFee;
    }

    /**
     * @return date of this Appointment
     */
    public String getDateOfAppointment() {
        return dateOfAppointment;
    }

    /**
     * Sets {@code dateOfAppointment} with new {@param dateOfAppointment}
     */
    public void setDateOfAppointment(String dateOfAppointment) {
        this.dateOfAppointment = dateOfAppointment;
    }


    /**
     * @return Transaction Status of the bill
     */
    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    /**
     * Sets {@code transactionStatus} with new {@param transactionStatus}
     */
    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }


    /**
     * Sets {@code moreThanOneMonth} with new {@param moreThanOneMonth}
     */
    public void setMoreThanOneMonth(boolean moreThanOneMonth) {
        this.moreThanOneMonth = moreThanOneMonth;
    }

    /**
     * @return true if the {@code dateOfAppointment} is more than 30 days else false
     */
    public boolean getMoreThanOneMonth(){
        return this.moreThanOneMonth;
    }

    /**
     *  Parse {@code dateOfAppointment} with {@link LocalDate}
     *  Check if no. of days is more than 30 between dateOfAppointment and now with {@link ChronoUnit}
     *  setMoreThanOneMonth(true) if more than 30 days.
     */
    public void checkMoreThanOneMonth(){
        long noOfDaysBetween = ChronoUnit.DAYS.between(LocalDate.parse(dateOfAppointment), LocalDate.now());
        if(noOfDaysBetween > 30){
            setMoreThanOneMonth(true);
        }

    }

    /**
     * Sets {@code patientId} with new {@param patientId}
     */
    public void setPatientID(PatientID patientID) {
        this.patientID = patientID;
    }

    /**
     * @return id of Patient to pay for this bill
     */
    public PatientID getPatientID() {
        return patientID;
    }

    /**
     * @return A string representation of the {@code transactionRef}
     * {@code transactionStatus}, {@code totalFee},
     * {@code appointmentId}, {@code dateOfAppointment},
     * {@code moreThanOneMonth}, {@code patientID},
     */
    @Override
    public String toString() {
        return "MedicalBill{" +
                "transactionRef='" + transactionRef + '\'' +
                ", transactionStatus=" + transactionStatus +
                ", totalFee=" + totalFee +
                ", appointmentId='" + appointmentId + '\'' +
                ", dateOfAppointment='" + dateOfAppointment + '\'' +
                ", moreThanOneMonth=" + moreThanOneMonth +
                ", patientID=" + patientID.getId()+
                '}';
    }
}
