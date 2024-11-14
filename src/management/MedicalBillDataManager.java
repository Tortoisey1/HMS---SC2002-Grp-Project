package management;

import enums.TransactionStatus;
import information.MedicalBill;
import information.id.PatientID;

import java.io.*;
import java.util.ArrayList;

public class MedicalBillDataManager implements DataManager<MedicalBill,String> {
    private ArrayList<MedicalBill> transactionList;
    private static DataManager<MedicalBill,String> transactionDataManager;
    private String filePath;


    public MedicalBillDataManager(){
        filePath = "src/data/transactions.csv";
        transactionList = new ArrayList<>();
        try {
            retrieveAll();
        }catch (IOException e){
            System.out.println(e);
        }
    }

    public static DataManager<MedicalBill, String> getInstance(){
        if(transactionDataManager == null){
            return transactionDataManager = new MedicalBillDataManager();
        }
        return  transactionDataManager;
    }


    @Override
    public MedicalBill retrieve(String transactionRef) {
        for(MedicalBill bill : transactionList) {
            if (bill.getTransactionRef().equals(transactionRef)) {
                return bill;
            }
        }
        return null;
    }

    public ArrayList<MedicalBill> retrieveAllBillForPatient(String patientId){
        ArrayList<MedicalBill> filteredList = new ArrayList<>();
        for(MedicalBill bill : transactionList) {
            if (bill.getPatientID().getId().equals(patientId)) {
                filteredList.add(bill);
            }
        }
        return filteredList;
    }

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

    @Override
    public boolean delete(String transactionRef) {
        MedicalBill medicalBill = retrieve(transactionRef);
        if(medicalBill != null){
            return transactionList.remove(medicalBill);
        }
        return false;
    }

    @Override
    public boolean add(MedicalBill medicalBill) {
        return transactionList.add(medicalBill);
    }

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

    @Override
    public ArrayList<MedicalBill> getList() {
        return this.transactionList;
    }
}
