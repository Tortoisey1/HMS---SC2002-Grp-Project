package management;

import enums.MedicationStatus;
import information.MedicalBill;
import information.medical.Medication;
import java.io.*;
import java.util.ArrayList;

/**
 * A DataManager for Medical Request that implements interface {@link DataManager}
 * Collect data from CSV file with {@link BufferedReader} and write back to CSV file with {@link BufferedWriter}
 * {@param MedicalBill} for the data collected
 * {@param String} for the ID of medication in String
 * Holds all the data type {@link MedicalBill}
 */
public class MedicationRequestDataManager implements DataManager<Medication, String> {

    private static DataManager<Medication, String> medicineDataManager;
    private final String inputFilePath = "src/data/medication_request.csv";;
    private static ArrayList<Medication> mediationRequest = new ArrayList<Medication>();

    /**
     * Constructs an MedicationRequestDataManager
     * {@code filePath} for the directory of the CSV
     * {@code retrieveAll()} to retrieve all the data from CSV and
     * instantiate each of the data to type {@link MedicalBill} when app begin
     * and finally, add each data to {@code mediationRequest}
     */
    public MedicationRequestDataManager() {
        try {
            retrieveAll();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Singleton for the MedicalBillDataManager
     * Declared and initialized the Constructor to {@code transactionDataManager} when app begin
     * Type {@link DataManager}
     * Down casting when needed
     */
    public static DataManager<Medication, String> getInstance() {
        if (medicineDataManager == null) {
            medicineDataManager = new MedicationRequestDataManager();
        }
        return medicineDataManager;
    }

    @Override
    public Medication retrieve(String appointmentId) {
        for (Medication m : mediationRequest) {
            if (m.getAppointmentId().equals(appointmentId)) {
                return m;
            }
        }
        return null;
    }

    @Override
    public boolean update(Medication medication) {
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

                mediationRequest.add(new Medication(
                        data[0],
                        data[1],
                        MedicationStatus.valueOf(data[2]),
                        data[3]
                ));
            }
            count++;
        }
        br.close();
    }

    @Override
    public void writeAll() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(inputFilePath));
        String[] header = { "Appointment_ID", "Medication_ID","status","Medication_name" };

        for (String h : header) {
            bw.write(h);
            bw.write(",");
        }
        for (Medication m : mediationRequest) {
            bw.newLine();
            bw.write(m.getAppointmentId());
            bw.write(",");
            bw.write(m.getMedicationId());
            bw.write(",");
            bw.write(m.getStatus().toString()); //will have to update this also since administrator can change or whoever it is
            bw.write(",");
            bw.write(m.getName());
        }
        bw.flush();
        bw.close();
    }

    @Override
    public ArrayList<Medication> getList() {
        return mediationRequest;
    }

    public boolean checkMedicineExist(String medicineName) {
        for (Medication m : mediationRequest) {
            if (m.getName().equals(medicineName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(String t1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public boolean add(Medication request) {
        return mediationRequest.add(request);
    }

}
