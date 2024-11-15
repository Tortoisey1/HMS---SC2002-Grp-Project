package app;
import entities.Patient;
import entities.Staff;
import information.Appointment;
import information.MedicalBill;
import information.medical.Medication;
import management.*;
import menu.AppMenu;

import java.io.IOException;




/**
 * HMSApp class will be the entire interface of this app.
 * From the beginning to the end when terminated from logout.
 */

public class HMSApp {
    /**
     * Main method to begin the application.
     * Beginning: Initialized all the required databases with retrieved data
     * Ending: Save all the data back to the respective CSV
     */
    public static void main(String[] args) {
        DataManager<Appointment,String> appointmentData = AppointmentDataManager.getInstance();
        DataManager<Patient,String> patientData = PatientDataManager.getInstance();
        DataManager<MedicalBill,String> medicalBillData = MedicalBillDataManager.getInstance();
        DataManager<Medication,String> medicationRequestData = MedicationRequestDataManager.getInstance();
        DataManager<Staff,String> staffData =  StaffDataManager.getInstance();
        InventoryDataManager inventoryData = InventoryDataManager.getInstance();

        AppMenu menu = new AppMenu();
        menu.printMenu();

        
        Thread saveData = new Thread(() -> {
            try {
                System.out.println("Writing appointment data...");
                appointmentData.writeAll();

                System.out.println("Writing patient data...");
                patientData.writeAll();

                System.out.println("Writing medical bill data...");
                medicalBillData.writeAll();

                System.out.println("Writing inventory data...");
                inventoryData.writeAll();

                System.out.println("Writing staff data...");
                staffData.writeAll();

                System.out.println("Writing medicine data...");
                medicationRequestData.writeAll();


                System.out.println("ENDING...");
            } catch (IOException e) {
                System.err.println("Error writing data during shutdown: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Unexpected error during shutdown: " + e.getMessage());
            }
        });


        Runtime.getRuntime().addShutdownHook(saveData);

        try {
            int choice = Integer.parseInt(Global.getScanner().nextLine());
            // if it wants to login will go into the login interface
            AppLogic mainLogic = new AppLogic();
            mainLogic.mainMenu(choice);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

        // output back into the csv

    }
}