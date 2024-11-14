package information;
import enums.TransactionStatus;
import information.id.PatientID;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class MedicalBill {
    private String transactionRef;
    private TransactionStatus transactionStatus;
    private long totalFee;
    private String appointmentId;
    private String dateOfAppointment;
    private boolean moreThanOneMonth;
    private PatientID patientID;

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


    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getTransactionRef() {
        return transactionRef;
    }

    public void setTransactionRef(String transactionRef) {
        this.transactionRef = transactionRef;
    }

    public long getTotalFee() {
        return totalFee;
    }
    public void setTotalFee(long totalFee) {
        this.totalFee = totalFee;
    }
    public String getDateOfAppointment() {
        return dateOfAppointment;
    }

    public void setDateOfAppointment(String dateOfAppointment) {
        this.dateOfAppointment = dateOfAppointment;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public void setMoreThanOneMonth(boolean moreThanOneMonth) {
        this.moreThanOneMonth = moreThanOneMonth;
    }

    public boolean getMoreThanOneMonth(){
        return this.moreThanOneMonth;
    }

    public void checkMoreThanOneMonth(){
        long noOfDaysBetween = ChronoUnit.DAYS.between(LocalDate.parse(dateOfAppointment), LocalDate.now());
        if(noOfDaysBetween > 30){
            setMoreThanOneMonth(true);
        }

    }
    public void setPatientID(PatientID patientID) {
        this.patientID = patientID;
    }

    public PatientID getPatientID() {
        return patientID;
    }

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
