package management;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import entities.Staff;
import entities.User;
import enums.Gender;
import enums.UserType;
import information.ContactInfo;
import information.Medicine;
import information.PrivateInformation;
import information.UserInformation;
import information.id.UserID;

public class StaffDataManager implements DataManager<Staff, String> {

    private static DataManager<Staff, String> staffDataManager;
    private final String inputFilePath = "data/staffs.csv";
    private final String outputFilePath = "data/newstaffs.csv";
    private static ArrayList<Staff> staffs = new ArrayList<Staff>();

    public StaffDataManager() {
        try {
            retrieveAll();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static DataManager<Staff, String> getInstance() {
        if (staffDataManager == null) {
            staffDataManager = new StaffDataManager();
        }
        return staffDataManager;
    }

    @Override
    public Staff retrieve(String id) {
        for (Staff s : staffs) {
            if (s.getUserInformation().getID().getId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    @Override
    public void retrieveAll() throws IOException {
        String line = "";
        BufferedReader br = new BufferedReader(new FileReader(inputFilePath));
        int count = 0;
        while ((line = br.readLine()) != null) {
            if (count > 0) {
                String[] data = line.split(",");

                // get the staff type
                String type = data[0].substring(0, 1).toUpperCase() + data[0].substring(1).toLowerCase();
                String idName = type + "ID";

                try {
                    // Use reflection to create an instance
                    Class<?> clazz = Class.forName(idName); // Get the Class object
                    UserID newId = (UserID) clazz.getDeclaredConstructor().newInstance(); // Create a new instance

                    staffs.add(new Staff(
                            new UserInformation(UserType.valueOf(data[0]), newId, data[1], new PrivateInformation(
                                    data[2], data[3], Gender.valueOf(data[4]), new ContactInfo(data[5], data[6])))));
                } catch (ClassNotFoundException e) {
                    throw new IllegalArgumentException("Unknown type: " + type, e);
                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException
                        | InvocationTargetException e) {
                    e.printStackTrace(); // Handle any other exceptions
                }

            }
            count++;
        }
        br.close();
    }

    @Override
    public void writeAll() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath));
        String[] header = { "Staff_id", "Staff_Type", "password", "Name", "DOB",
                "Gender", "PhoneNumber", "Email" };

        for (String temp : header) {
            bw.write(temp);
            bw.write(",");
        }
        for (Staff temp : staffs) {
            bw.newLine();
            bw.write(temp.getUserInformation().getID().getId());
            bw.write(",");
            bw.write(temp.getUserInformation().getUserType().toString());
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
        }
        bw.flush();
        bw.close();
    }

    @Override
    public ArrayList<Staff> getList() {
        return staffs;
    }

    public boolean checkMedicineExist(String id) {
        for (Staff s : staffs) {
            if (s.getUserInformation().getID().getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void update(Staff t1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public boolean delete(Staff t1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public boolean add(Staff T1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

}
