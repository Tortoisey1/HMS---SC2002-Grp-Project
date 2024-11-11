package management;

import entities.Patient;
import enums.MedicationStatus;
import information.medical.Medication;

import java.io.*;
import java.util.ArrayList;

public class InventoryDataManager implements DataManager<Medication, String>{

    private static InventoryDataManager inventoryDataManager;
    private final String inputFilePath = "src/data/inventory.csv";;
    private static ArrayList<Medication> inventory = new ArrayList<Medication>();

    public InventoryDataManager() {
        try {
            retrieveAll();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    public static InventoryDataManager getInstance() {
        if (inventoryDataManager == null) {
            inventoryDataManager = new InventoryDataManager();
        }
        return inventoryDataManager;
    }


    @Override
    public Medication retrieve(String medicineId) {
        for (Medication m : inventory) {
            if (m.getMedicationId().equals(medicineId)) {
                return m;
            }
        }
        return null;
    }

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

    @Override
    public boolean delete(String t1) {
        return false;
    }

    @Override
    public boolean add(Medication medication) {
        return inventory.add(medication);
    }

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

    @Override
    public ArrayList<Medication> getList() {
        return inventory;
    }
}
