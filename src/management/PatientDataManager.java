package management;

import java.io.*;
import java.util.ArrayList;
import entities.Patient;
import enums.AppointmentStatus;
import enums.BloodType;
import enums.Gender;
import enums.UserType;
import information.*;
import information.id.PatientID;

/**
 * A DataManager for Patients that implements interface {@link DataManager}
 * Collect data from CSV file with {@link BufferedReader} and write back to CSV file with {@link BufferedWriter}
 * {@param Patient} for the data collected
 * {@param String} for the Patient ID in String
 * Holds all the data type {@link Patient}
 */
public class PatientDataManager implements DataManager<Patient, String> {

    private static DataManager<Patient, String> patientDataManager;
    private final String inputFilePath = "src/data/patients.csv";
    private ArrayList<Patient> patients = new ArrayList<Patient>();

    /**
     * Constructs an PatientDataManager
     * {@code filePath} for the directory of the CSV
     * {@code retrieveAll()} to retrieve all the data from CSV and
     * instantiate each of the data to type {@link Patient} when app begin
     * and finally, add each data to {@code patients}
     */
    public PatientDataManager() {
        try {
            retrieveAll();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Singleton for the MedicalBillDataManager
     * Declared and initialized the Constructor to {@code patientDataManager} when app begin
     * Type {@link DataManager}
     * Down casting when needed
     */
    public static DataManager<Patient, String> getInstance() {
        if (patientDataManager == null) {
            patientDataManager = new PatientDataManager();
        }
        return patientDataManager;
    }

    /**
     * Retrieve a patient bill based on the {@param patientId} from {@code patients}
     * @return the {@link Patient} if found else null
     */
    @Override
    public Patient retrieve(String patientId) {
        for (Patient p : patients) {
            if (p.getUserInformation().getID().getId().equals(patientId)) {
                return p;
            }
        }
        return null;
    }

    /**
     * update details of Patient with {@param newDetails}
     * search through the {@code patients} and update it based on the Patient ID on that index
     * @return true if update is successful else false
     */
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

    /**
     * delete the Patient from {@code patients} based on the {@param patientId}
     * retrieve the memory reference of Patient and if not null
     * {@code patients} removes it
     * @return true if delete is successful else false
     */
    @Override
    public boolean delete(String patientId) {
        Patient patient = retrieve(patientId);
        if(patient != null){
            return patients.remove(patient);
        }
        return false;
    }

    /**
     * add the Patient {@param patient} to {@code patients }
     * @return true if add is successful else false
     */
    @Override
    public boolean add(Patient patient) {
        return patients.add(patient);
    }

    /**
     * Retrieve all the Medical Bills with {@link BufferedReader} from CSV of path {@code filePath}
     * Start collecting when count == 1 as count = 0 is the headers
     * Add each data and instantiate them to type {@link MedicalBill}
     * Add it to {@code transactionList}
     * @throws IOException when file not found
     */
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

    /**
     * Write all the data of type {@link MedicalBill} back into CSV with {@link BufferedWriter}
     * path {@code filePath}
     * For each Medical Bill from {@code transactionList}
     * Parse each fields from {@link MedicalBill} to String before adding into the CSV
     * @throws IOException when file not found
     */
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

    /**
     * Retrieve {@link ArrayList} of all Appointments with type {@link Appointment}
     * @return {@code appointmentList}
     */
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
