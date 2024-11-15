package management;

import java.io.*;
import java.util.ArrayList;

import entities.*;
import enums.Gender;
import enums.UserType;
import information.ContactInfo;
import information.PrivateInformation;
import information.ReplenishmentRequest;
import information.UserInformation;
import information.id.*;


/**
 * A DataManager for Staffs that implements interface {@link DataManager}
 * Collect data from CSV file with {@link BufferedReader} and write back to CSV file with {@link BufferedWriter}
 * {@param Staff} for the data collected
 * {@param String} for the Staff ID in String
 * Holds all the data type {@link Staff}
 */
public class StaffDataManager implements DataManager<Staff, String> {

    private static DataManager<Staff, String> staffDataManager;
    private final String inputFilePath = "src/data/staffs.csv";
    private static ArrayList<Staff> staffs = new ArrayList<Staff>();

    /**
     * Constructs an StaffDataManager
     * {@code filePath} for the directory of the CSV
     * {@code retrieveAll()} to retrieve all the data from CSV and
     * instantiate each of the data to type {@link Staff} when app begin
     * and finally, add each data to {@code staffs}
     */
    public StaffDataManager() {
        try {
            retrieveAll();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Singleton for the StaffDataManager
     * Declared and initialized the Constructor to {@code staffDataManager} when app begin
     * Type {@link DataManager}
     * Down casting when needed
     */
    public static DataManager<Staff, String> getInstance() {
        if (staffDataManager == null) {
            staffDataManager = new StaffDataManager();
        }
        return staffDataManager;
    }

    /**
     * Retrieve a Staff based on the {@param id} from {@code staffs}
     * @return the {@link Staff} if found else null
     */
    @Override
    public Staff retrieve(String id) {
        for (Staff s : staffs) {
            if (s.getUserInformation().getID().getId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Retrieve all the Staff with {@link BufferedReader} from CSV of path {@code filePath}
     * Start collecting when count == 1 as count = 0 is the headers
     * Add each data and instantiate them to type {@link Staff}
     * Add it to {@code staffs}
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
                UserInformation info = new UserInformation();
                Staff newStaff = null;
                    if(UserType.valueOf(data[1]).equals(UserType.DOCTOR)){
                        UserID doctorID = new DoctorID();
                        doctorID.setId(data[0]);
                        info.setUserType(UserType.DOCTOR);
                        info.setID(doctorID);
                        info.setFirstLogin(Boolean.parseBoolean(data[8]));
                        info.setPassword(data[2]);
                        info.setPrivateInformation(
                                new PrivateInformation(
                                        data[3],
                                        data[4],
                                        Gender.valueOf(data[5]),
                                        new ContactInfo(data[6], data[7])
                                )
                        );
                        newStaff = new Doctor(info);
                    }
                    if(UserType.valueOf(data[1]).equals(UserType.PHARMACIST)){
                        UserID pharmacistID = new PharmacistID();
                        pharmacistID.setId(data[0]);
                        info.setUserType(UserType.PHARMACIST);
                        info.setID(pharmacistID);
                        info.setFirstLogin(Boolean.parseBoolean(data[8]));
                        info.setPassword(data[2]);
                        info.setPrivateInformation(
                                new PrivateInformation(
                                        data[3],
                                        data[4],
                                        Gender.valueOf(data[5]),
                                        new ContactInfo(data[6], data[7])
                                )
                        );
                        newStaff = new Pharmacist(info);
                    }
                    if(UserType.valueOf(data[1]).equals(UserType.ADMINISTRATOR)){
                        UserID administratorID = new AdministratorID();
                        administratorID.setId(data[0]);
                        info.setUserType(UserType.ADMINISTRATOR);
                        info.setID(administratorID);
                        info.setFirstLogin(Boolean.parseBoolean(data[8]));
                        info.setPassword(data[2]);
                        info.setPrivateInformation(
                                new PrivateInformation(
                                        data[3],
                                        data[4],
                                        Gender.valueOf(data[5]),
                                        new ContactInfo(data[6], data[7])
                                )
                        );
                        newStaff = new Administrator(info);
                    }
                    staffs.add(newStaff);
            }
            count++;
        }
        br.close();
    }

    /**
     * Write all the data of type {@link Staff} back into CSV with {@link BufferedWriter}
     * path {@code filePath}
     * For each Staff from {@code staffs }
     * Parse each fields from {@link Staff} to String before adding into the CSV
     * @throws IOException when file not found
     */
    @Override
    public void writeAll() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(inputFilePath));
        String[] header = { "Staff_id", "Staff_Type", "password", "Name", "DOB",
                "Gender", "PhoneNumber", "Email", "First_login" };

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
            bw.write(String.valueOf(temp.getUserInformation().isFirstLogin()));
            bw.write(",");
        }
        bw.flush();
        bw.close();
    }

    /**
     * Retrieve {@link ArrayList} of all Staff with type {@link Staff}
     * @return {@code staffs}
     */
    @Override
    public ArrayList<Staff> getList() {
        return staffs;
    }

    /**
     * @return true if staff exists compared with {@param id}
     */
    public boolean checkStaffExist(String id) {
        for (Staff s : staffs) {
            if (s.getUserInformation().getID().getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * update details of Staff with {@param newStaff}
     * search through the {@code staffs} and update it based on the Staff ID on that index
     * @return true if update is successful else false
     */
    @Override
    public boolean update(Staff newStaff) {
        int count = 0;
        for(Staff temp : staffs){
            System.out.println(staffs);
            if(temp.getUserInformation().getID().getId().equals(newStaff.getUserInformation().getID().getId())){
                staffs.set(count,newStaff);
                return true;
            }
            count++;
        }
        return false;
    }

    /**
     * delete the Staff from {@code staffs} based on the {@param t1}
     * @return true if delete is successful else false
     */
    @Override
    public boolean delete(String t1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    /**
     * add the Staff {@param newStaff} to {@code staffs }
     * @return true if add is successful else false
     */
    @Override
public boolean add(Staff newStaff) {
    if (retrieve(newStaff.getUserInformation().getID().getId()) == null) { // Check if staff doesn't already exist
        staffs.add(newStaff); // Add the new staff to the list
        try {
            writeAll(); // Save the updated staff list to the file
        } catch (IOException e) {
            System.out.println("Error saving new staff data: " + e.getMessage());
            return false; // Return false if saving fails
        }
        return true; // Return true if successfully added
    } else {
        System.out.println("Staff with this ID already exists.");
        return false; // Return false if staff already exists
    }
}


    

}
