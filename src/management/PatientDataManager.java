package management;

import java.io.*;
import java.util.ArrayList;

import entities.Patient;
import enums.AppointmentStatus;
import enums.BloodType;
import enums.Gender;
import enums.UserType;
import information.ContactInfo;
import information.PrivateInformation;
import information.UserInformation;
import information.id.PatientID;

public class PatientDataManager implements DataManager<Patient, String> {

    private static DataManager<Patient, String> patientDataManager;
    private final String inputFilePath = "src/data/patients.csv";
    private ArrayList<Patient> patients = new ArrayList<Patient>();

    public PatientDataManager() {
        try {
            retrieveAll();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static DataManager<Patient, String> getInstance() {
        if (patientDataManager == null) {
            patientDataManager = new PatientDataManager();
        }
        return patientDataManager;
    }

    @Override
    public Patient retrieve(String patientId) {
        for (Patient p : patients) {
            if (p.getUserInformation().getID().getId().equals(patientId)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public boolean update(Patient newDetails) {
        int count = 0;
        for(Patient temp : patients){
            if(temp.getUserInformation().getID().getId().equals(newDetails.getUserInformation().getID().getId())){
                patients.set(count,newDetails);
                return true;
            }
            count++;
        }
        return false;
    }

    @Override
    public boolean delete(String patientId) {
        Patient patient = retrieve(patientId);
        if(patient != null){
            return patients.remove(patient);
        }
        return false;
    }

    @Override
    public boolean add(Patient patient) {
        return patients.add(patient);
    }

    @Override
    public void retrieveAll() throws IOException {
        String line = "";
        BufferedReader br = new BufferedReader(new FileReader(inputFilePath));
        int count = 0;
        while ((line = br.readLine()) != null) {
            if (count > 0) {
                String[] data = line.split(",");
                PatientID patientId = new PatientID();
                patientId.setId(data[0]);
                UserInformation info =  new UserInformation(
                        UserType.PATIENT,
                        patientId,
                        data[1],
                        new PrivateInformation(
                                data[2],
                                data[3],
                                Gender.valueOf(data[4]),
                                new ContactInfo(data[5], data[6])
                        )
                );
                info.setFirstLogin(Boolean.parseBoolean(data[8]));
                Patient patient = new Patient(
                        info,
                        BloodType.valueOf(data[7]));
                AppointmentDataManager.getInstance().getList().forEach(
                        (appointment) -> {
                            if(appointment.getPatientId().getId().equals(patient.getUserInformation().getID().getId()
                            )){

                                if(appointment.getAppointmentStatus() == AppointmentStatus.CANCELLED
                                        || appointment.getAppointmentStatus() == AppointmentStatus.PENDING
                                        || appointment.getAppointmentStatus() == AppointmentStatus.CONFIRMED) {
                                    patient.getAppointments().add(appointment);
                                } else {
                                    patient.getMedicalInformation().getPastTreatments().add(appointment);
                                }
                            }
                        }
                );
                patients.add(patient);
            }
            count++;
        }
        br.close();
    }

    @Override
    public void writeAll() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(inputFilePath));
        String[] header = { "Patient_id", "password", "Name", "DOB",
                "Gender", "PhoneNumber", "Email", "BloodType", "First_login"};
        for (String h : header) {
            bw.write(h);
            bw.write(",");
        }
        for (Patient temp : patients) {
            bw.newLine();
            bw.write(temp.getUserInformation().getID().getId());
            bw.write(",");
            bw.write(temp.getUserInformation().getPassword());
            bw.write(",");
            bw.write(temp.getUserInformation().getPrivateInformation().getName());
            bw.write(",");
            bw.write(temp.getUserInformation().getPrivateInformation().getDateOfBirth());
            bw.write(",");
            bw.write(temp.getUserInformation().getPrivateInformation().getGender().toString());
            bw.write(",");
            bw.write(temp.getUserInformation().getPrivateInformation().getContactInfo().getPhoneNumber());
            bw.write(",");
            bw.write(temp.getUserInformation().getPrivateInformation().getContactInfo().getEmailAddress());
            bw.write(",");
            bw.write(temp.getMedicalInformation().getBloodType().toString());
            bw.write(",");
            bw.write(String.valueOf(temp.getUserInformation().isFirstLogin()));
        }
        bw.flush();
        bw.close();
    }

    @Override
    public ArrayList<Patient> getList() {
        return patients;
    }

    public boolean checkPatientExist(String id) {
        for (Patient patient : patients) {
            if (patient.getUserInformation().getID().getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

}
