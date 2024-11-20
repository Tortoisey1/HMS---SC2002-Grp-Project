package services.administrator;

import app.Global;
import enums.UserType;
import enums.AppointmentStatus;
import enums.Gender;
import entities.Staff;
import exceptions.StaffExistException;
import exceptions.StaffDoesNotExistException;
import information.ContactInfo;
import information.PrivateInformation;
import information.UserInformation;
import information.id.AdministratorID;
import information.id.UserID;
import information.Appointment;
import information.medical.Medication;
import management.AppointmentDataManager;
import management.InventoryDataManager;
import management.StaffDataManager;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class AdministratorService {
    private InventoryDataManager inventoryDataManager;
    private StaffDataManager staffDataManager;
    private AppointmentDataManager appointmentDataManager;

    public AdministratorService() {
        this.inventoryDataManager = InventoryDataManager.getInstance();
        this.staffDataManager = (StaffDataManager) StaffDataManager.getInstance();
        this.appointmentDataManager = (AppointmentDataManager) AppointmentDataManager.getInstance();
    }

    // Method for managing staff, providing options for adding, removing, or
    // updating staff
    public void manageStaff() {
        System.out.println("Manage Staff:");
        System.out.println("1. Add Staff");
        System.out.println("2. Remove Staff");
        System.out.println("3. Update Staff");
        System.out.println("4. Display Filtered Staff List");
        System.out.print("Choose an option: ");

        int choice = Integer.parseInt(Global.getScanner().nextLine());

        switch (choice) {
            case 1:
                addStaff();
                break;
            case 2:
                removeStaff();
                break;
            case 3:
                updateStaff();
                break;
            case 4:
                filterAndDisplayStaff();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void filterAndDisplayStaff() {
        Scanner scanner = Global.getScanner();
        System.out.print("Enter role to filter by (e.g., DOCTOR, ADMINISTRATOR) or leave blank: ");
        String role = scanner.nextLine().trim();
        if (role.isEmpty())
            role = null;

        System.out.print("Enter gender to filter by (e.g., MALE, FEMALE) or leave blank: ");
        String gender = scanner.nextLine().trim();
        if (gender.isEmpty())
            gender = null;

        System.out.print("Enter age to filter by or leave blank: ");
        String ageInput = scanner.nextLine().trim();
        Integer age = ageInput.isEmpty() ? null : Integer.parseInt(ageInput);

        displayFilteredStaff(role, gender, age);
    }

    private void addStaff() {
        Scanner scanner = Global.getScanner();

        // Collect staff details
        System.out.print("Enter Staff Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Date of Birth (e.g., YYYY-MM-DD): ");
        String dateOfBirth = scanner.nextLine();
        System.out.print("Enter Gender (MALE or FEMALE): ");
        Gender gender = Gender.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("Enter Contact Phone Number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter Contact Email: ");
        String email = scanner.nextLine();

        ContactInfo contactInfo = new ContactInfo(phoneNumber, email);
        PrivateInformation privateInfo = new PrivateInformation(name, dateOfBirth, gender, contactInfo);

        System.out.print("Enter Staff Role (e.g., ADMINISTRATOR): ");
        UserType userType = UserType.valueOf(scanner.nextLine().toUpperCase());
        UserID userId = new AdministratorID();
        System.out.print("Enter Staff Password: ");
        String password = scanner.nextLine();

        UserInformation userInfo = new UserInformation(userType, userId, password, privateInfo);
        Staff staff = new Staff(userInfo);

        if (staffDataManager.retrieve(userId.getId()) == null) {
            staffDataManager.add(staff);
            System.out.println("Staff added successfully.");
        } else {
            System.out.println("Staff with this ID already exists.");
        }
    }

    private void removeStaff() {
        System.out.print("Enter Staff ID to remove: ");
        String staffId = Global.getScanner().nextLine();
        Staff staff = staffDataManager.retrieve(staffId);

        if (staff != null) {
            staffDataManager.getList().remove(staff);
            System.out.println("Staff removed successfully.");
        } else {
            System.out.println("Staff not found.");
        }
    }

    private void updateStaff() {
        System.out.print("Enter Staff ID to update: ");
        String staffId = Global.getScanner().nextLine();
        Staff staff = staffDataManager.retrieve(staffId);

        if (staff != null) {
            System.out.println("Select the information you want to update:");
            System.out.println("1. User Type");
            System.out.println("2. Password");
            System.out.print("Enter your choice: ");

            int choice = Integer.parseInt(Global.getScanner().nextLine());

            switch (choice) {
                case 1:
                    System.out.print("Enter new User Type (e.g., ADMIN, DOCTOR): ");
                    UserType newUserType = UserType.valueOf(Global.getScanner().nextLine().toUpperCase());
                    staff.getUserInformation().setUserType(newUserType);
                    System.out.println("User Type updated successfully.");
                    break;

                case 2:
                    System.out.print("Enter new Password: ");
                    String newPassword = Global.getScanner().nextLine();
                    staff.getUserInformation().setPassword(newPassword);
                    System.out.println("Password updated successfully.");
                    break;

                default:
                    System.out.println("Invalid choice.");
                    break;
            }

            staffDataManager.update(staff);
            System.out.println("Staff information updated successfully.");
        } else {
            System.out.println("Staff not found.");
        }
    }

    // public void approveReplenishmentRequest() {
    //     System.out.println("Checking inventory for low stock items...");
    //     List<Medication> inventoryList = inventoryDataManager.getList();
    //     for (Medication medication : inventoryList) {
    //         if (medication.getStock() < 10) {
    //             System.out.println("Replenishment request submitted for: " + medication.getName() +
    //                     ", Current Stock: " + medication.getStock());
    //         }
    //     }
    // }

    private void displayFilteredStaff(String role, String gender, Integer age) {
        List<Staff> staffList = staffDataManager.getList();
        for (Staff staff : staffList) {
            boolean matchesRole = role == null
                    || staff.getUserInformation().getUserType().toString().equalsIgnoreCase(role);
            boolean matchesGender = gender == null || staff.getUserInformation().getPrivateInformation().getGender()
                    .toString().equalsIgnoreCase(gender);
            boolean matchesAge = age == null || staff.getAge() == age;

            if (matchesRole && matchesGender && matchesAge) {
                System.out.println("Staff ID: " + staff.getUserInformation().getID().getId());
                System.out.println("Name: " + staff.getUserInformation().getPrivateInformation().getName());
                System.out.println("Role: " + staff.getUserInformation().getUserType());
                System.out.println("Gender: " + staff.getUserInformation().getPrivateInformation().getGender());
                System.out.println("Age: " + staff.getAge());
                System.out.println("-----------------------------");
            }
        }
    }

    // View all appointments
    public void viewAppointmentDetails() {
    	int j=1;
        List<Appointment> appointments = appointmentDataManager.getList();
        System.out.println("Appointment Details:");
        for (Appointment appointment : appointments) {
        	System.out.println("Appointment "+(j++));
            System.out.println("Appointment ID: " + appointment.getAppointmentID());
            System.out.println("Patient ID: " + appointment.getPatientId().getId());
            System.out.println("Doctor ID: " + appointment.getDoctorId().getId());
            System.out.println("Status: " + appointment.getAppointmentStatus());
            System.out.println("Date: " + appointment.getDateOfTreatment());
            System.out.println("Time: " + appointment.getTimeOfTreatment());
            if (appointment.getAppointmentStatus() == AppointmentStatus.COMPLETED) {
                System.out.println("Outcome Record:");
                System.out.println();
                
                
                System.out.println("Consultation notes: ");
                String notes=appointment.getAppointmentOutcomeRecord().getConsultationNotes().getCriticalDetails();
                System.out.print("Critical Details: ");
                if(notes!=null)System.out.println(notes);
                else System.out.println("None");
                
                notes=appointment.getAppointmentOutcomeRecord().getConsultationNotes().getComplaints();
                System.out.print("Complaints: ");
                if(notes!=null)System.out.println(notes);
                else System.out.println("None");
                
                notes=appointment.getAppointmentOutcomeRecord().getConsultationNotes().getFurtherInfo();
                System.out.print("Further information: ");
                if(notes!=null)System.out.println(notes);
                else System.out.println("None");            
                
                System.out.println();
                
                if(appointment.getAppointmentOutcomeRecord().getMedications().size()==0) 
                {
                	System.out.println("No prescription(s)");
                	System.out.println();
                	return;
                }
                
                System.out.println("Medication prescribed: ");
                int k=1;
                
                for (Medication prescription : appointment.getAppointmentOutcomeRecord().getMedications()) {
                    System.out.println("Prescription " + (k++) + ": " + prescription.getName());
                }
                
                
                
            }
            System.out.println("-----------------------------");
        }
    }

    // Manage inventory by viewing, adding, updating stock levels
    public void manageInventory() {
        System.out.println("Manage Inventory:");
        System.out.println("1. View Inventory");
        System.out.println("2. Add or Update Medication Stock");
        System.out.println("3. Update Low Stock Alert Level");
        System.out.print("Choose an option: ");

        int choice = Integer.parseInt(Global.getScanner().nextLine());

        switch (choice) {
            case 1:
                displayInventory();
                break;
            case 2:
                addOrUpdateMedicationStock();
                break;
            case 3:
                updateLowStockAlertLevel();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void displayInventory() {
        List<Medication> inventoryList = inventoryDataManager.getList();
        System.out.println("Current Medication Inventory:");
        for (Medication medication : inventoryList) {
            System.out.println("Medication ID: " + medication.getMedicationId());
            System.out.println("Name: " + medication.getName());
            System.out.println("Stock: " + medication.getStock());
          //  System.out.println("Low Stock Alert Level: " + medication.getLowStockLevel());
            System.out.println("-----------------------------");
        }
    }

    private void addOrUpdateMedicationStock() {
        System.out.print("Enter Medication ID: ");
        String medicationId = Global.getScanner().nextLine();
        System.out.print("Enter Stock Quantity: ");
        int stock = Integer.parseInt(Global.getScanner().nextLine());

        Medication medication = inventoryDataManager.retrieve(medicationId);
        if (medication != null) {
            medication.setStock(stock);
            System.out.println("Stock updated for medication: " + medication.getName());
        } else {
            System.out.println("Medication not found.");
        }
    }

    private void updateLowStockAlertLevel() {
        System.out.print("Enter Medication ID: ");
        String medicationId = Global.getScanner().nextLine();
        System.out.print("Enter new Low Stock Alert Level: ");
        int alertLevel = Integer.parseInt(Global.getScanner().nextLine());

        Medication medication = inventoryDataManager.retrieve(medicationId);
        if (medication != null) {
          //  medication.setLowStockLevel(alertLevel);
            System.out.println("Low stock alert level updated for medication: " + medication.getName());
        } else {
            System.out.println("Medication not found.");
        }
    }

    public void addOrUpdateMedicationStock(String medicationId, int stock) {
        Medication medication = inventoryDataManager.retrieve(medicationId);
        if (medication != null) {
            medication.setStock(stock);
            System.out.println("Stock updated for medication: " + medication.getName());
            try {
                inventoryDataManager.writeAll();
            } catch (IOException e) {
                System.out.println("Error updating inventory: " + e.getMessage());
            }
        } else {
            System.out.println("Medication not found in inventory.");
        }
    }

    // Update low stock level alert line
    public void updateLowStockAlertLevel(String medicationId, int newAlertLevel) {
        Medication medication = inventoryDataManager.retrieve(medicationId);
        if (medication != null) {
            //   medication.setLowStockLevel(newAlertLevel);
            System.out.println("Low stock alert level updated for medication: " + medication.getName());
            try {
                inventoryDataManager.writeAll();
            } catch (IOException e) {
                System.out.println("Error updating inventory: " + e.getMessage());
            }
        } else {
            System.out.println("Medication not found in inventory.");
        }
    }

    // Approve replenishment requests from pharmacists
    public void approveReplenishmentRequest(String medicationId, int requestedStock) {
        Medication medication = inventoryDataManager.retrieve(medicationId);
        if (medication != null) {
            medication.setStock(medication.getStock() + requestedStock);
            System.out.println("Replenishment approved for medication: " + medication.getName());
            try {
                inventoryDataManager.writeAll();
            } catch (IOException e) {
                System.out.println("Error saving replenishment: " + e.getMessage());
            }
        } else {
            System.out.println("Medication not found.");
        }
    }

    // Initialize system data from files
    public void initializeSystemData() {
        try {
            staffDataManager.retrieveAll();
            System.out.println("Staff data loaded successfully.");
            inventoryDataManager.retrieveAll();
            System.out.println("Inventory data loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading system data: " + e.getMessage());
        }
    }
}
