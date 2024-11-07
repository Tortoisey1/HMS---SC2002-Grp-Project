package services.pharmacist;

import app.Global;
import enums.AppointmentStatus;
import enums.MedicationStatus;
import information.Appointment;
import information.medical.Medication;
import management.AppointmentDataManager;
import management.DataManager;
import management.InventoryDataManager;
import management.MedicationRequestDataManager;
import java.io.IOException;
import java.util.*;

public class AppointmentOutcomeRecordPharmacistService {
    private InventoryDataManager inventoryDataManager;
    private DataManager<Medication,String> requestDataManager;
    private DataManager<Appointment, String> appointmentDataManager;
    public AppointmentOutcomeRecordPharmacistService(){
        this.inventoryDataManager = InventoryDataManager.getInstance();
        this.requestDataManager = MedicationRequestDataManager.getInstance();
        this.appointmentDataManager = AppointmentDataManager.getInstance();
    }

    public void viewAppointmentOutcomeRecord() {
        ArrayList<Appointment> originalRecords = appointmentDataManager.getList();
        for (Appointment record : originalRecords) {
            if (record.getAppointmentStatus() == AppointmentStatus.COMPLETED) {
                System.out.println("Appointment ID: " + record.getAppointmentID());
                System.out.println("Medications:");
                Medication medication = requestDataManager.retrieve(record.getAppointmentID());
                if (medication != null) {
                    if (medication.getStatus() == MedicationStatus.PENDING) {
                        System.out.println("MedicationID: " + medication.getMedicationId());
                        System.out.println("Name: " + medication.getName());
                        System.out.println("Status: " + medication.getStatus());
                    }
                } else {
                    System.out.println("Medication has already been DISPENSED");
                }
            }else {
                System.out.println("No medications found in this appointment.");
            }
        }
    }

    public void updatePrescriptionStatus(){
        Scanner sc = Global.getScanner();
        System.out.println("Enter the appointment Id to update the status of prescription: ");
        String appointmentid = sc.nextLine();
        Medication medication = requestDataManager.retrieve(appointmentid);
        if (medication != null){
            if (medication.getStatus() == MedicationStatus.PENDING){
                medication.setStatus(MedicationStatus.DISPENSED);
                System.out.println("Prescription for medication ID: " + medication.getMedicationId() + " has been updated to the state DISPENSED.");
                inventoryDataManager.decrementStock(medication.getMedicationId(), 1);
                try {
                    ((MedicationRequestDataManager) requestDataManager).writeAll();
                    System.out.println("Status is updated and has been succesfully saved.");
                }catch (IOException e){
                    System.out.println("Error saving the updated status of prescription: " + e.getMessage());
                }
            }else {
                System.out.println("The prescription status is not PENDING, so it is impossible for it to get updated.");
            }
        }else {
            System.out.println("No medication is found with the given Appointment ID.");
        }
    }

    public void viewMedicationInventory(){
        System.out.println("Current Medication Inventory:");
        List<Medication> invlist = inventoryDataManager.getList();
        for (Medication medication: invlist){
            System.out.println("ID: " + medication.getMedicationId() + ", Name: " + medication.getName() + ", Stock: " + medication.getStock());
        }
    }
    public void submitReplenishmentRequest(){
        System.out.println("Checking inventory for low stock items..");
        List<Medication> invlist = inventoryDataManager.getList();
        for (Medication medication: invlist){
            if (medication.getStock() < 10){
                System.out.println("Replenishment request submitted for: " + medication.getName() + ", Current Stock: " + medication.getStock() + ".");
            }
        }
    }
}
