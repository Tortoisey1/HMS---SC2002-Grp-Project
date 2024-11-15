package management;

import enums.TransactionStatus;
import information.Appointment;
import information.MedicalBill;
import information.id.PatientID;

import java.io.*;
import java.util.ArrayList;


/**
 * A DataManager for Medical Bill or Transaction Receipts that implements interface {@link DataManager}
 * Collect data from CSV file with {@link BufferedReader} and write back to CSV file with {@link BufferedWriter}
 * {@param MedicalBill} for the data collected
 * {@param String} for the Transaction Reference in String
 * Holds all the data type {@link MedicalBill}
 */
public class MedicalBillDataManager implements DataManager<MedicalBill,String> {
    private ArrayList<MedicalBill> transactionList;
    private static DataManager<MedicalBill,String> transactionDataManager;
    private String filePath;


    /**
     * Constructs an MedicalBillDataManager
     * {@code filePath} for the directory of the CSV
     * {@code retrieveAll()} to retrieve all the data from CSV and
     * instantiate each of the data to type {@link MedicalBill} when app begin
     * and finally, add each data to {@code transactionList}
     */
    public MedicalBillDataManager(){
        filePath = "src/data/transactions.csv";
        transactionList = new ArrayList<>();
        try {
            retrieveAll();
        }catch (IOException e){
            System.out.println(e);
        }
    }

    /**
     * Singleton for the MedicalBillDataManager
     * Declared and initialized the Constructor to {@code transactionDataManager} when app begin
     * Type {@link DataManager}
     * Down casting when needed
     */
    public static DataManager<MedicalBill, String> getInstance(){
        if(transactionDataManager == null){
            return transactionDataManager = new MedicalBillDataManager();
        }
        return  transactionDataManager;
    }

    /**
     * Retrieve a medical bill based on the {@param transactionRef} from {@code transactionList}
     * @return the {@link MedicalBill} if found else null
     */
    @Override
    public MedicalBill retrieve(String transactionRef) {
        for(MedicalBill bill : transactionList) {
            if (bill.getTransactionRef().equals(transactionRef)) {
                return bill;
            }
        }
        return null;
    }

    /**
     * Retrieve all medical bill based on the {@param patientId} from {@code transactionList}
     * @return {@link ArrayList} of type {@link MedicalBill} for the current patient
     */
    public ArrayList<MedicalBill> retrieveAllBillForPatient(String patientId){
        ArrayList<MedicalBill> filteredList = new ArrayList<>();
        for(MedicalBill bill : transactionList) {
            if (bill.getPatientID().getId().equals(patientId)) {
                filteredList.add(bill);
            }
        }
        return filteredList;
    }

    /**
     * Retrieve all medical bill based on the {@param patientId} from {@code transactionList}
     * @return {@link ArrayList} of type {@link MedicalBill} based on {@link TransactionStatus}
     * UNPAID for the current patient
     */
    public ArrayList<MedicalBill> retrieveUnpaidBillForPatient(String patientId){
        ArrayList<MedicalBill> filteredList = new ArrayList<>();
        for(MedicalBill bill : transactionList) {
            if (bill.getPatientID().getId().equals(patientId)
                    && bill.getTransactionStatus() == TransactionStatus.UNPAID) {
                filteredList.add(bill);
            }
        }
        return filteredList;
    }

    /**
     * update details of Medical Bill with {@param medicalBill}
     * search through the {@code transactionList} and update it based on the Transaction Reference on that index
     * @return true if update is successful else false
     */
    @Override
    public boolean update(MedicalBill medicalBill) {
        int count = 0;
        for(MedicalBill temp : transactionList){
            if(temp.getTransactionRef().equals(medicalBill.getTransactionRef())){
                transactionList.set(count,medicalBill);
                return true;
            }
            count++;
        }
        return false;
    }

    /**
     * delete the medical Bill from {@code transactionList} based on the {@param transactionRef}
     * retrieve the memory reference of Medical Bill and if not null
     * {@code transactionList} removes it
     * @return true if delete is successful else false
     */
    @Override
    public boolean delete(String transactionRef) {
        MedicalBill medicalBill = retrieve(transactionRef);
        if(medicalBill != null){
            return transactionList.remove(medicalBill);
        }
        return false;
    }

    /**
     * add the Medical Bill {@param medicalBill} to {@code transactionList }
     * @return true if add is successful else false
     */
    @Override
    public boolean add(MedicalBill medicalBill) {
        return transactionList.add(medicalBill);
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
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        int count = 0;
        while((line = br.readLine()) != null){
            if (count > 0) {
                String[] data = line.split(",");
                PatientID patientID = new PatientID();
                patientID.setId(data[1]);
                transactionList.add(
                        new MedicalBill(
                                data[0],
                                patientID,
                                TransactionStatus.valueOf(data[2]),
                                Long.parseLong(data[4]),
                                data[3],
                                data[5]
                        )
                );

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
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
        String[] header = {"Transaction_Ref", "Patient_ID", "Transaction_Status","Appointment_ID","Total_Fee",
                "DateOfAppointment"};
        for(String h : header){
            bw.write(h);
            bw.write(",");
        }

        for(MedicalBill temp : transactionList){
            bw.newLine();
            bw.write(temp.getTransactionRef());
            bw.write(",");
            bw.write(temp.getPatientID().getId());
            bw.write(",");
            bw.write(temp.getTransactionStatus().toString());
            bw.write(",");
            bw.write(temp.getAppointmentId());
            bw.write(",");
            bw.write(String.valueOf(temp.getTotalFee()));
            bw.write(",");
            bw.write(String.valueOf(temp.getDateOfAppointment()));
            bw.write(",");
        }
        bw.flush();
        bw.close();
    }

    /**
     * Retrieve {@link ArrayList} of all Appointments with type {@link Appointment}
     * @return {@code appointmentList}
     */
    @Override
    public ArrayList<MedicalBill> getList() {
        return this.transactionList;
    }
}
