package management;

import java.io.*;
import java.util.ArrayList;

import entities.*;
import enums.Gender;
import enums.UserType;
import information.ContactInfo;
import information.PrivateInformation;
import information.UserInformation;
import information.id.*;

public class StaffDataManager implements DataManager<Staff, String> {

    private static DataManager<Staff, String> staffDataManager;
    private final String inputFilePath = "src/data/staffs.csv";
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

    @Override
    public boolean delete(String t1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public boolean add(Staff T1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

}
