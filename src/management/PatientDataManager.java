package management;

import java.io.*;
import java.util.ArrayList;

import entities.MedicalRecord;
import entities.Patient;
import enums.BloodType;
import enums.Gender;
import enums.UserType;
import information.ContactInfo;
import information.PrivateInformation;
import information.UserInformation;
import information.id.PatientID;

public class PatientDataManager implements DataManager<Patient, String> {

    private static DataManager<Patient, String> patientDataManager;
    private final String inputFilePath = "data/patients.csv";
    private final String outputFilePath = "data/newpatients.csv";
    private static ArrayList<Patient> patients = new ArrayList<Patient>();

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
    public void update(Patient newDetails) {
        // int count = 0;
        // try {
        // for (Patient temp : patients) {
        // if (temp.getPatientId().equals(newDetails.getPatientId())) {
        // patients.set(count, newDetails);
        // // writeAll();
        // System.out.println("Updated your details");
        // }
        // count++;
        // }
        // } catch (IOException e) {
        // System.out.println(e);
        // }
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
        BufferedReader br = new BufferedReader(new FileReader(inputFilePath));
        int count = 0;
        while ((line = br.readLine()) != null) {
            if (count > 0) {
                String[] data = line.split(",");

                patients.add(new Patient(
                        new UserInformation(UserType.PATIENT, new PatientID(), data[0],
                                new PrivateInformation(data[1], data[2], Gender.valueOf(data[3]),
                                        new ContactInfo(data[4], data[5]))),
                        BloodType.valueOf(data[6])));
            }
            count++;
        }
        br.close();
    }

    @Override
    public void writeAll() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath));
        String[] header = { "Patient_id", "password", "Name", "DOB",
                "Gender", "PhoneNumber", "Email", "BloodType", "MedicalRecords" };

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
            for (MedicalRecord record : temp.getMedicalRecords()) {
                for (String string : record.getPastDiagnoses()) {
                    bw.write(string);
                    bw.write(":");
                }
            }
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
