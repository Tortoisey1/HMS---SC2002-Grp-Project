package merged;
import java.util.Arrays;

public class Patient extends Person {
    private String patientId;
    private String bloodType;
    private Appointment[] appointments;
    private ContactInfo contactInfo;
    private String DOB;
    private String[] illnesses;

    Patient(){}

    Patient(String patientId, int age, String name, String dob, Gender gender, Role role, ContactInfo contactInfo, String bloodType, String[] illnesses){
        super(age,gender,role,name);
        this.DOB = dob;
        this.patientId = patientId;
        this.contactInfo = contactInfo;
        this.bloodType = bloodType;
        this.illnesses = illnesses;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getBloodType() {
        return this.bloodType;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Appointment[] getTreatments() {
        return appointments;
    }

    public void setTreatments(Appointment[] appointments) {
        this.appointments = appointments;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String[] getIllnesses() {
        return illnesses;
    }

    public void setIllnesses(String[] illnesses) {
        this.illnesses = illnesses;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    @Override
    public String toString() {
        return this.patientId + "and " + this.bloodType + "and " + this.contactInfo.getEmail() + Arrays.toString(this.illnesses);
    }
}