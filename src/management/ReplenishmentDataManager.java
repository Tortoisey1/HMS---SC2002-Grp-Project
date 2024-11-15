package management;



import entities.Patient;
import information.*;
import information.id.AdministratorID;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A DataManager for Patients that implements interface {@link DataManager}
 * Collect data from CSV file with {@link BufferedReader} and write back to CSV file with {@link BufferedWriter}
 * {@param ReplenishmentRequest} for the data collected
 * {@param String} for the Patient ID in String
 * Holds all the data type {@link ReplenishmentRequest}
 */
public class ReplenishmentDataManager implements DataManager<ReplenishmentRequest,String> {

    private static DataManager<ReplenishmentRequest, String> replenishmentDataManager;
    private final String inputFilePath = "src/data/replenishment_request.csv";
    private static final ArrayList<ReplenishmentRequest> replenishmentRequests = new ArrayList<>();

    /**
     * Constructs an ReplenishmentDataManager
     * {@code filePath} for the directory of the CSV
     * {@code retrieveAll()} to retrieve all the data from CSV and
     * instantiate each of the data to type {@link ReplenishmentRequest} when app begin
     * and finally, add each data to {@code replenishmentRequests}
     */
    public ReplenishmentDataManager() {
        try {
            retrieveAll();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Singleton for the ReplenishmentDataManager
     * Declared and initialized the Constructor to {@code replenishmentDataManager} when app begin
     * Type {@link DataManager}
     * Down casting when needed
     */
    public static DataManager<ReplenishmentRequest, String> getInstance() {
        if (replenishmentDataManager == null) {
            replenishmentDataManager = new ReplenishmentDataManager();
        }
        return replenishmentDataManager;
    }

    /**
     * Retrieve a Replenishment Request based on the {@param replenishmentId} from {@code replenishmentRequests}
     * @return the {@link ReplenishmentRequest} if found else null
     */
    @Override
    public ReplenishmentRequest retrieve(String replenishmentId) {
        for (ReplenishmentRequest request : replenishmentRequests) {
            if (request.getReplenishmentId().equals(replenishmentId)) {
                return request;
            }
        }
        return null;
    }

    /**
     * update details of Replenishment Request with {@param replenishmentRequest}
     * search through the {@code replenishmentRequests} and update it based on the Replenishment Request ID on that index
     * @return true if update is successful else false
     */
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

    /**
     * delete the Replenishment Request from {@code replenishmentRequests} based on the {@param replenishmentId}
     * retrieve the memory reference of Replenishment Request and if not null
     * {@code replenishmentRequests} removes it
     * @return true if delete is successful else false
     */
    @Override
    public boolean delete(String replenishmentId) {
        return replenishmentRequests.removeIf(request -> request.getReplenishmentId().equals(replenishmentId));
    }

    /**
     * add the replenishmentRequests {@param replenishmentRequest} to {@code replenishmentRequests }
     * @return true if add is successful else false
     */
    @Override
    public boolean add(ReplenishmentRequest replenishmentRequest) {
        return replenishmentRequests.add(replenishmentRequest);
    }

    /**
     * Retrieve all the Replenishment Request with {@link BufferedReader} from CSV of path {@code filePath}
     * Start collecting when count == 1 as count = 0 is the headers
     * Add each data and instantiate them to type {@link ReplenishmentRequest}
     * Add it to {@code replenishmentRequests}
     * @throws IOException when file not found
     */
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

    /**
     * Write all the data of type {@link ReplenishmentRequest} back into CSV with {@link BufferedWriter}
     * path {@code filePath}
     * For each Replenishment Request from {@code replenishmentRequests}
     * Parse each fields from {@link ReplenishmentRequest} to String before adding into the CSV
     * @throws IOException when file not found
     */
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

    /**
     * Retrieve {@link ArrayList} of all Patients with type {@link ReplenishmentRequest}
     * @return {@code replenishmentRequests}
     */
    @Override
    public ArrayList<ReplenishmentRequest> getList() {
        return replenishmentRequests;
    }

    /**
     * add the {@param request} to CSV
     */
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