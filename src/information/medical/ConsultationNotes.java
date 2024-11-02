package information.medical;

public class ConsultationNotes {
    private String criticalDetails;
    private String complaints;
    private String furtherInfo;

    public ConsultationNotes(String criticalDetails, String complaints, String furtherInfo){
        this.criticalDetails = criticalDetails;
        this.complaints = complaints;
        this.furtherInfo = furtherInfo;
    }

    public ConsultationNotes(){
        this.criticalDetails = "None";
    }

    public String getComplaints() {
        return complaints;
    }

    public void setComplaints(String complaints) {
        this.complaints = complaints;
    }

    public String getCriticalDetails() {
        return criticalDetails;
    }

    public void setCriticalDetails(String criticalDetails) {
        this.criticalDetails = criticalDetails;
    }

    public String getFurtherInfo() {
        return furtherInfo;
    }

    public void setFurtherInfo(String furtherInfo) {
        this.furtherInfo = furtherInfo;
    }
}
