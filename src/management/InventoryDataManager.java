package management;



import information.Appointment;
import information.ReplenishmentRequest;
import information.medical.Medication;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * A DataManager for Inventory that implements interface {@link DataManager}
 * Collect data from CSV file with {@link BufferedReader} and write back to CSV file with {@link BufferedWriter}
 * {@param Medication} for the data collected
 * {@param String} for the Medication ID in String
 * Holds all the data type {@link Medication}
 */
public class InventoryDataManager implements DataManager<Medication, String>{

    private static InventoryDataManager inventoryDataManager;
    private final String inputFilePath = "src/data/inventory.csv";;
    private static ArrayList<Medication> inventory = new ArrayList<Medication>();

    /**
     * Constructs an InventoryDataManager
     * {@code filePath} for the directory of the CSV
     * {@code retrieveAll()} to retrieve all the data from CSV and
     * instantiate each of the data to type {@link Medication} when app begin
     * and finally, add each data to {@code inventory}
     */
    public InventoryDataManager() {
        try {
            retrieveAll();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Singleton for the InventoryDataManager
     * Declared and initialized the Constructor to {@code inventoryDataManager} when app begin
     * Type {@link DataManager}
     * Down casting when needed
     */
    public static InventoryDataManager getInstance() {
        if (inventoryDataManager == null) {
            inventoryDataManager = new InventoryDataManager();
        }
        return inventoryDataManager;
    }

    /**
     * Retrieve an appointment based on the {@param medicineId} from {@code inventory}
     * @return the {@link Medication} if found else null
     */
    @Override
    public Medication retrieve(String medicineId) {
        for (Medication m : inventory) {
            if (m.getMedicationId().equals(medicineId)) {
                return m;
            }
        }
        return null;
    }

    /**
     * update details of Medication in the inventory with {@param newDetails}
     * search through the {@code inventory} and update it based on the Medication id on that index
     * @return true if update is successful else false
     */
    @Override
    public boolean update(Medication newDetails) {
        int count = 0;
        for(Medication temp : inventory){
            if(temp.getMedicationId().equals(newDetails.getMedicationId())){
                inventory.set(count,newDetails);
                return true;
            }
            count++;
        }
        return false;
    }

    /**
     * update number of stock by decrementing {@param stockToDispense} from stock for {@param medicationId}
     * search through the {@code inventory} and update it based on the Medication id on that index
     * decrement if {@code temp.getStock()} > {@param stockToDispense}
     * @return true if update is successful else false
     */
    public boolean decrementStock(String medicationId, int stockToDispense){
        int count = 0;
        for(Medication temp : inventory){
            if(temp.getMedicationId().equals(medicationId)){
                if(temp.getStock() > stockToDispense){
                    temp.setStock(temp.getStock() - stockToDispense);
                    inventory.set(count,temp);
                }
                return true;
            }
            count++;
        }
        return false;
    }

    /**
     * delete the Medication from {@code inventory}
     */
    @Override
    public boolean delete(String t1) {
        return false;
    }

    /**
     * add the Medication {@param medication} to {@code inventory }
     * @return true if add is successful else false
     */
    @Override
    public boolean add(Medication medication) {
        return inventory.add(medication);
    }


    /**
     * Retrieve all the Medications with {@link BufferedReader} from CSV of path {@code filePath}
     * Start collecting when count == 1 as count = 0 is the headers
     * Add each data and instantiate them to type {@link Medication}
     * Add it to {@code inventory}
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

                inventory.add(new Medication(
                        data[0],
                        data[1],
                        Integer.parseInt(data[2]),
                        Double.parseDouble(data[3])
                ));
            }
            count++;
        }
        br.close();
    }

    /**
     * Write all the data of type {@link Medication} back into CSV with {@link BufferedWriter}
     * path {@code filePath}
     * For each Medication from {@code inventory}
     * Parse each fields from Medication to String before adding into the CSV
     * @throws IOException when file not found
     */
    @Override
    public void writeAll() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(inputFilePath));
        String[] header = { "Medication_ID","Medication_name","stock", "price"};

        for (String h : header) {
            bw.write(h);
            bw.write(",");
        }
        for (Medication m : inventory) {
            bw.newLine();
            bw.write(m.getMedicationId());
            bw.write(",");
            bw.write(m.getName());
            bw.write(",");
            bw.write(String.valueOf(m.getStock()));
            bw.write(",");
            bw.write(String.valueOf(m.getPrice()));
            bw.write(",");
        }
        bw.flush();
        bw.close();
    }

    /**
     * Retrieve {@link ArrayList} of all Medication with type {@link Medication}
     * @return {@code inventory}
     */
    @Override
    public ArrayList<Medication> getList() {
        return inventory;
    }
}
