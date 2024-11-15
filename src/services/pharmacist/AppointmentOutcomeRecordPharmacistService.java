package services.pharmacist;

import app.Global;
import enums.AppointmentStatus;
import enums.MedicationStatus;
import enums.TransactionStatus;
import information.Appointment;
import information.MedicalBill;
import information.ReplenishmentRequest;
import information.id.AdministratorID;
import information.id.PatientID;
import information.medical.Medication;
import management.*;
import services.helper.GenerateIdHelper;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class AppointmentOutcomeRecordPharmacistService {
    private InventoryDataManager inventoryDataManager;
    private DataManager<Medication,String> requestDataManager;
    private DataManager<Appointment, String> appointmentDataManager;
    private DataManager<MedicalBill, String> medicalBillDataManager;
    private List<ReplenishmentRequest> replenishmentRequests;
    public AppointmentOutcomeRecordPharmacistService(){
        this.inventoryDataManager = InventoryDataManager.getInstance();
        this.requestDataManager = MedicationRequestDataManager.getInstance();
        this.appointmentDataManager = AppointmentDataManager.getInstance();
        this.medicalBillDataManager = MedicalBillDataManager.getInstance();
        this.replenishmentRequests = new ArrayList<>();
    }

    public void viewAppointmentOutcomeRecord() {
        ArrayList<Appointment> originalRecords = appointmentDataManager.getList();
        for (Appointment record : originalRecords) {
            if( ChronoUnit.DAYS.between(LocalDate.parse(record.getDateOfTreatment()), LocalDate.now()) == 0) {
                if(record.getAppointmentStatus() == AppointmentStatus.COMPLETED){
                    System.out.println("Appointment ID: " + record.getAppointmentID());
                    System.out.println("Date Completed: " + record.getDateOfTreatment());
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
    }

    public void updatePrescriptionStatus(){
        Scanner sc = Global.getScanner();
        System.out.println("Enter the appointment Id to update the status of prescription: ");
        String appointmentid = sc.nextLine();
        Medication medication = requestDataManager.retrieve(appointmentid);
        if (medication != null){
            if (medication.getStatus() == MedicationStatus.PENDING){
                medication.setStatus(MedicationStatus.DISPENSED);
                double priceOfMedication = inventoryDataManager.retrieve(medication.getMedicationId()).getPrice();
                PatientID patientId = appointmentDataManager.retrieve(appointmentid).getPatientId();
                // once dispensed then calculate the total bill (medication dispensed + 50 consultation fee)
                // receipt/bill to be sent to patient

                MedicalBill receipt = new MedicalBill(
                        GenerateIdHelper.generateId("TR"),
                        patientId,
                        TransactionStatus.UNPAID,
                        (long) (50 + priceOfMedication),
                        appointmentid,
                        LocalDate.now().toString()
                );

                medicalBillDataManager.add(receipt);
                System.out.println("Prescription for medication ID: " + medication.getMedicationId() + " has been updated to the state DISPENSED.");
                System.out.println("Receipt Transaction ref: " + receipt.getTransactionRef() + " generated");
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
        ReplenishmentDataManager replenishmentDataManager = (ReplenishmentDataManager) ReplenishmentDataManager.getInstance();
        for (Medication medication: invlist){
            if (medication.getStock() < 10){
                System.out.println("Low stock detected for: " + medication.getName() + ", Current Stock: " + medication.getStock() + ".");
                System.out.println("Low stock detected for: " +
                        medication.getName() + ", Current Stock: " +
                        medication.getStock() + ".");
                int reqamt = 10 - medication.getStock();
                AdministratorID administratorID = new AdministratorID();
                String dateofreq = LocalDate.now().toString();
                ReplenishmentRequest replenishmentRequest = new ReplenishmentRequest(
                        GenerateIdHelper.generateId("RR"),
                        medication.getMedicationId(),
                        medication.getName(), reqamt, administratorID,
                        "Administrator", dateofreq);
                replenishmentRequest.setApprovalResult(false);
                try {
                    replenishmentDataManager.logreqtocsv(replenishmentRequest);
                    replenishmentDataManager.add(replenishmentRequest);
                    System.out.println("Replenishment request for " + medication.getName() +
                            "has been submitted for approval");
                }catch (IOException e){
                    System.out.println("Error creating/logging replenishment request: " +
                            e.getMessage());
                }
            }
        }
        System.out.println("Total replenishment requests submitted: " + replenishmentRequests.size());
    }
}
