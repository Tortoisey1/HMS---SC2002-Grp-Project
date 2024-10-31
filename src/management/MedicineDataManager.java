package management;

import java.io.*;
import java.util.ArrayList;

import information.Medicine;

public class MedicineDataManager implements DataManager<Medicine, String> {

    private static DataManager<Medicine, String> medicineDataManager;
    private final String inputFilePath = "data/medicine.csv";
    private final String outputFilePath = "data/newmedicine.csv";
    private static ArrayList<Medicine> medicines = new ArrayList<Medicine>();

    public MedicineDataManager() {
        try {
            retrieveAll();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static DataManager<Medicine, String> getInstance() {
        if (medicineDataManager == null) {
            medicineDataManager = new MedicineDataManager();
        }
        return medicineDataManager;
    }

    @Override
    public Medicine retrieve(String medicineName) {
        for (Medicine m : medicines) {
            if (m.getName().equals(medicineName)) {
                return m;
            }
        }
        return null;
    }

    @Override
    public void retrieveAll() throws IOException {
        String line = "";
        BufferedReader br = new BufferedReader(new FileReader(inputFilePath));
        int count = 0;

        medicines.clear();

        while ((line = br.readLine()) != null) {
            if (count > 0) {
                String[] data = line.split(",");

                medicines.add(new Medicine(data[0], Integer.valueOf(data[1]), Integer.valueOf(data[2])));
            }
            count++;
        }
        br.close();
    }

    @Override
    public void writeAll() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath, false));
        String[] header = { "name", "currentStock", "alertStockLvl" };

        for (String h : header) {
            bw.write(h);
            bw.write(",");
        }
        for (Medicine m : medicines) {
            bw.newLine();
            bw.write(m.getName());
            bw.write(",");
            bw.write(Integer.toString(m.getCurrentStock()));
            bw.write(",");
            bw.write(Integer.toString(m.getAlertStockLvl())); // will have to update this also since administrator can
                                                              // change or whoever
            // it is
            bw.write(",");
        }
        bw.flush();
        bw.close();
    }

    @Override
    public ArrayList<Medicine> getList() {
        return medicines;
    }

    public boolean checkMedicineExist(String medicineName) {
        for (Medicine m : medicines) {
            if (m.getName().equals(medicineName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void update(Medicine t1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public boolean delete(Medicine t1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public boolean add(Medicine T1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

}
