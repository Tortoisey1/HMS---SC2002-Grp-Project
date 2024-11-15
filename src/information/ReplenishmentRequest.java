package information;

import enums.AppointmentStatus;
import enums.MedicalService;
import information.id.AdministratorID;
import information.id.DoctorID;
import information.id.PatientID;
import information.medical.AppointmentOutcomeRecord;


/**
 * This class represents the ReplenishmentRequest sent by the Pharmacist if stock is low
 */

public class ReplenishmentRequest {
    private AdministratorID id;
    private String medicationName;
    private int amount;
    private String dateOfRequest;
    private boolean approvalResult;
    private String replenishmentId;
    private String medicationId;
    private String endorsedby;


    /**
     * Constructs using Default Constructor with no fields are needed
     */

    public ReplenishmentRequest(){}

    /**
     * Constructs an Appointment
     * @param replenishmentId for ID of the replenishment
     * @param medicationId for ID of the Medication to be replenished
     * @param medicationName for name of the Medication to be replenished
     * @param amount for the stock amount to be replenished
     * @param id type {@link AdministratorID} for the admin who endorsed
     * @param endorsedby for Staff
     * @param dateOfRequest date of the request of the replenishment
     */
    public ReplenishmentRequest(
            String replenishmentId,
            String medicationId,
            String medicationName,
            int amount,
            AdministratorID id,
            String endorsedby,
            String dateOfRequest){
        this.replenishmentId = replenishmentId;
        this.medicationId = medicationId;
        this.medicationName = medicationName;
        this.amount = amount;
        this.id = id;
        this.endorsedby = endorsedby;
        this.dateOfRequest = dateOfRequest;
    }



    /**
     * @return name of medication
     */
    public String getMedicationName() {
        return medicationName;
    }

    /**
     * Sets {@code medicationName} with new {@param medicationName}
     */
    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    /**
     * @return id of medication
     */
    public String getMedicationId() {
        return medicationId;
    }

    /**
     * Sets {@code medicationId} with new {@param medicationId}
     */
    public void setMedicationId(String medicationId) {
        this.medicationId = medicationId;
    }

    /**
     * @return stock of medication to replenish
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Sets {@code amount} with new {@param amount}
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * @return id of Administrator
     */
    public AdministratorID getId() {
        return id;
    }

    /**
     * Sets {@code id} with new {@param id}
     */
    public void setId(AdministratorID id) {
        this.id = id;
   }

    /**
     * @return date of request
     */
    public String getDateOfRequest() {
        return dateOfRequest;
    }

    /**
     * Sets {@code dateOfRequest} with new {@param dateOfRequest}
     */
    public void setDateOfRequest(String dateOfRequest) {
        this.dateOfRequest = dateOfRequest;
    }

    /**
     * @return true if approved else false
     */
    public boolean isApprovalResult() {
        return approvalResult;
    }

    /**
     * Sets {@code approvalResult} with new {@param approvalResult}
     */
    public void setApprovalResult(boolean approvalResult) {
        this.approvalResult = approvalResult;
    }

    /**
     * @return id of replenishment
     */
    public String getReplenishmentId() {
        return replenishmentId;
    }

    /**
     * Sets {@code replenishmentId} with new {@param replenishmentId}
     */
    public void setReplenishmentId(String replenishmentId) {
        this.replenishmentId = replenishmentId;
    }

    /**
     * @return endorsedBy
     */
    public String getEndorsedby() {
        return endorsedby;
    }

    /**
     * Sets {@code endorsedby} with new {@param endorsedby}
     */
    public void setEndorsedby(String endorsedby){
        this.endorsedby = endorsedby;
    }

    /**
     * @return A string representation of the {@code replenishmentId}
     * {@code medicationId}, {@code medicationName},
     * {@code amount}, {@code endorsedby},
     * {@code dateOfRequest}
     */
    @Override
    public String toString() {
        return "ReplenishmentRequest{" + "replenishmentId='" + replenishmentId + '\'' +
                ", medicationId='" + medicationId + '\'' + ", medicationName='" +
                medicationName + '\'' + ", amount=" + amount + ", endorsedby='" +
                endorsedby + '\'' + ", dateofRequest='" + dateOfRequest + '\'' + '}';
    }
}