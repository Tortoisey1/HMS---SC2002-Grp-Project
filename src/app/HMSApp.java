package app;

import management.MedicineDataManager;
import management.PatientDataManager;
import management.StaffDataManager;
import management.AppointmentDataManager;
import menu.AppMenu;

public class HMSApp {
    public static void main(String[] args) {
        // load the data files first
        PatientDataManager patientData = new PatientDataManager();
        MedicineDataManager medicineData = new MedicineDataManager();
        StaffDataManager staffData = new StaffDataManager();
        AppointmentDataManager appointmentData = new AppointmentDataManager();

        // /first menu
        AppMenu menu = new AppMenu();
        menu.printMenu();

        try {
            int choice = Integer.valueOf(Global.getScanner().nextLine());

            // if it wants to login will go into the login interface
            AppLogic mainLogic = new AppLogic();
            mainLogic.mainMenu(choice);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

        // output back into the csv

        try {
            patientData.writeAll();
            medicineData.writeAll();
            staffData.writeAll();
            appointmentData.writeAll();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

    }
}