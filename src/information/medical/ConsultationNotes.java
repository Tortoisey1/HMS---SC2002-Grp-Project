package information.medical;

/**
 * This class represents an ConsultationNotes
 * It defines the getter/setters for additional fields {@code criticalDetails}, {@code complaints} and
 * {@code furtherInfo}
 */

public class ConsultationNotes {
    private String criticalDetails;
    private String complaints;
    private String furtherInfo;

    /**
     * Constructor define needed with ENCAPSULATED fields: {@param criticalDetails},
     * {@param complaints} and {@param furtherInfo} with type {@link String}
     * Abstraction performed for all fields in {@code ConsultationNotes}
     */
    public ConsultationNotes(String criticalDetails, String complaints, String furtherInfo){
        this.criticalDetails = criticalDetails;
        this.complaints = complaints;
        this.furtherInfo = furtherInfo;
    }

    /**
     * Default Constructor defined to set {@code criticalDetails} to "None"
     * when appointment status is not COMPLETED
     * Required for DataManager to check if {@code criticalDetails}.equals("None") to perform certain format
     * to be written to CSV
     */
    public ConsultationNotes(){
        this.criticalDetails = "None";
    }

    /**
     * Retrieves complaints by patient for the illness
     * @return {@code complaints} {@link String}
     */
    public String getComplaints() {
        return complaints;
    }


    /**
     * Sets {@code complaints} with the new String {@param complaints}
     */
    public void setComplaints(String complaints) {
        this.complaints = complaints;
    }

    /**
     * Retrieves criticalDetails that cannot be ignored
     * @return {@code criticalDetails} {@link String}
     */
    public String getCriticalDetails() {
        return criticalDetails;
    }

    /**
     * Sets {@code criticalDetails} with the new String {@param criticalDetails}
     */
    public void setCriticalDetails(String criticalDetails) {
        this.criticalDetails = criticalDetails;
    }

    /**
     * Retrieves furtherInfo on this patient
     * @return {@code furtherInfo} {@link String}
     */
    public String getFurtherInfo() {
        return furtherInfo;
    }
    /**
     * Sets {@code furtherInfo} with the new String {@param furtherInfo}
     */
    public void setFurtherInfo(String furtherInfo) {
        this.furtherInfo = furtherInfo;
    }
}
