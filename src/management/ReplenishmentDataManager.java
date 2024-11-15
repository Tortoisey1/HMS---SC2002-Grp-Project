package management;


import entities.Administrator;
import entities.Doctor;
import entities.Pharmacist;
import entities.Staff;
import enums.Gender;
import enums.UserType;
import information.*;
import information.id.AdministratorID;
import information.id.DoctorID;
import information.id.PharmacistID;
import information.id.UserID;
import information.medical.Medication;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReplenishmentDataManager implements DataManager<ReplenishmentRequest,String> {

    private static DataManager<ReplenishmentRequest, String> replenishmentDataManager;
    private final String inputFilePath = "src/data/replenishment_request.csv";
    private static final ArrayList<ReplenishmentRequest> replenishmentRequests = new ArrayList<>();

    public ReplenishmentDataManager() {
        try {
            retrieveAll();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static DataManager<ReplenishmentRequest, String> getInstance() {
        if (replenishmentDataManager == null) {
            replenishmentDataManager = new ReplenishmentDataManager();
        }
        return replenishmentDataManager;
    }

    @Override
    public ReplenishmentRequest retrieve(String replenishmentId) {
        for (ReplenishmentRequest request : replenishmentRequests) {
            if (request.getReplenishmentId().equals(replenishmentId)) {
                return request;
            }
        }
        return null;
    }
    @Override
    public boolean update(ReplenishmentRequest replenishmentRequest) {
        for (int i = 0; i < replenishmentRequests.size(); i++){
            if (replenishmentRequests.get(i).getReplenishmentId().equals(replenishmentRequest.getReplenishmentId())){
                replenishmentRequests.set(i, replenishmentRequest);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(String replenishmentId) {
        return replenishmentRequests.removeIf(request -> request.getReplenishmentId().equals(replenishmentId));
    }

    @Override
    public boolean add(ReplenishmentRequest replenishmentRequest) {
        return replenishmentRequests.add(replenishmentRequest);
    }

    @Override
    public void retrieveAll() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath))){
            String line;
            int count = 0;
            while ((line = br.readLine()) != null){
                if (count++ == 0) continue;
                String[] data = line.split(",");
                if (data.length >= 6){
                    AdministratorID adminID = new AdministratorID();
                    adminID.setId(data[4]);
                    ReplenishmentRequest request = new ReplenishmentRequest(data[0],
                            data[1], data[2], Integer.parseInt(data[3]), adminID,
                            data[4], data[5]);
                    replenishmentRequests.add(request);
                }else {
                    System.out.println("Skipping Invalid row: " + line);
                }
            }
        }
    }

    @Override
    public void writeAll() throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(inputFilePath))){
            bw.write("ReplenishmentID,Medication_ID,Medication_name,Requested_Amount,Endorsed_By,Date_of_Request,");
            bw.newLine();
            for (ReplenishmentRequest request: replenishmentRequests){
                bw.write(String.join(",", request.getReplenishmentId(),
                        request.getMedicationId(), request.getMedicationName(),
                        String.valueOf(request.getAmount()), request.getEndorsedby(),
                        request.getDateOfRequest()));
                bw.newLine();
            }
        }
    }

    @Override
    public ArrayList<ReplenishmentRequest> getList() {
        return replenishmentRequests;
    }

    public void logreqtocsv(ReplenishmentRequest request) throws IOException {
        try (FileWriter fw = new FileWriter(inputFilePath, true);
             PrintWriter pw = new PrintWriter(fw)){
            pw.printf("%s,%s,%s,%d,%s,%s%n", request.getReplenishmentId(),
                    request.getMedicationId(), request.getMedicationName(),
                    request.getAmount(), request.getEndorsedby(),
                    new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        }
    }
}