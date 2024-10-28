public class PatientController implements PatientInterface{

    private static PatientController patientController;
    private final DataManager<Patient,String> patientDataManager;

    public static PatientController getInstance(){
        if(patientController == null){
            patientController = new PatientController(PatientDataManager.getInstance());
        }
        return patientController;
    }

    PatientController(DataManager<Patient,String> patientDataManager ){
        this.patientDataManager = patientDataManager;
    }


    @Override
    public Patient getDetails(String patientId) {
        return patientDataManager.retrieve(patientId);
    }

    @Override
    public void updateDetails(Patient patient, int choice, String newInput) {
        ContactInfo info = patient.getContactInfo();
        if(choice == 1){
            info.setEmail(newInput);
            patient.setContactInfo(info);
        }else{
            info.setPhoneNumber(newInput);
            patient.setContactInfo(info);
        }
         patientDataManager.update(patient);
    }

}
