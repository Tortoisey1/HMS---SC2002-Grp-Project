import java.io.*;
import java.util.ArrayList;

public class PatientDataManager implements DataManager<Patient,String> {

    private static DataManager<Patient,String> patientDataManager;
    private final String filePath;
    private ArrayList<Patient> patients;
    PatientDataManager(){
        filePath = "patients.csv";
        patients = new ArrayList<>();
        try {
            retrieveAll();
        }catch (IOException e){
            System.out.println(e);
        }
    }

    public static DataManager<Patient,String> getInstance(){
        if(patientDataManager == null){
            patientDataManager = new PatientDataManager();
        }
        return patientDataManager;
    }


    @Override
    public Patient retrieve(String patientId) {
        for(Patient p : patients){
            if(p.getPatientId().equals(patientId)){
                return p;
            }
        }
        return null;
    }

    @Override
    public void update(Patient newDetails) {
        int count = 0;
        try {
            for(Patient temp : patients){
                if(temp.getPatientId().equals(newDetails.getPatientId())){
                    patients.set(count,newDetails);
                    writeAll();
                    System.out.println("Updated your details");
                }
                count++;
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public boolean delete(Patient patient) {
        return false;
    }

    @Override
    public boolean add(Patient T1) {
        return false;
    }

    @Override
    public void retrieveAll() throws IOException {
        String line = "";
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        int count = 0;
        while((line = br.readLine()) != null){
            if (count > 0) {
                String[] data = line.split(",");
                String[] illnesses = data[9].split(":");

                patients.add(new Patient(
                        data[0],
                        Integer.parseInt(data[1]),
                        data[2],
                        data[3],
                        Gender.valueOf(data[4]),
                        Role.valueOf(data[5].trim()),
                        new ContactInfo(data[6], data[7]),
                        data[8],
                        illnesses
                ));
            }
            count++;
        }
        br.close();
    }

    @Override
    public void writeAll() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
        String[] header = {"Patient_id","Age","Name","DOB",
                "Gender", "Role", "Email",
                "Contact", "BloodType","Illnesses"};

        for(String h : header){
            bw.write(h);
            bw.write(",");
        }
        for(Patient temp : patients){
            bw.newLine();
            bw.write(temp.getPatientId());
            bw.write(",");
            bw.write(String.valueOf(temp.getAge()));
            bw.write(",");
            bw.write(temp.getName());
            bw.write(",");
            bw.write(temp.getDOB());
            bw.write(",");
            bw.write(temp.getGender().toString());
            bw.write(",");
            bw.write(temp.getRole().toString());
            bw.write(",");
            bw.write(temp.getContactInfo().getEmail());
            bw.write(",");
            bw.write(temp.getContactInfo().getPhoneNumber());
            bw.write(",");
            bw.write(temp.getBloodType());
            bw.write(",");
            for(String illness : temp.getIllnesses()){
                bw.write(illness);
                bw.write(":");
            }
        }
        bw.flush();
        bw.close();
    }

    @Override
    public ArrayList<Patient> getList() {
        return patients;
    }

}
