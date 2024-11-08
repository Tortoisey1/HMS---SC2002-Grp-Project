package services.administrator;

import app.Global;
import enums.UserType;
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
            default:
                System.out.println("Invalid choice.");
        }
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

    public void viewAppointmentDetails() {
        List<Appointment> appointments = appointmentDataManager.getList();
        System.out.println("Appointment Details:");
        for (Appointment appointment : appointments) {
            System.out.println("Appointment ID: " + appointment.getAppointmentID());
            System.out.println("Date: " + appointment.getDateOfTreatment()); // Changed from getDate() to
                                                                             // getDateOfTreatment()
            System.out.println("Status: " + appointment.getAppointmentStatus());
            System.out.println("-----------------------------");
        }
    }

    public void manageInventory() {
        System.out.println("Current Medication Inventory:");
        List<Medication> inventoryList = inventoryDataManager.getList();
        for (Medication medication : inventoryList) {
            System.out.println("Medication ID: " + medication.getMedicationId() +
                    ", Name: " + medication.getName() +
                    ", Stock: " + medication.getStock());
        }
    }

    public void approveReplenishmentRequest() {
        System.out.println("Checking inventory for low stock items...");
        List<Medication> inventoryList = inventoryDataManager.getList();
        for (Medication medication : inventoryList) {
            if (medication.getStock() < 10) {
                System.out.println("Replenishment request submitted for: " + medication.getName() +
                        ", Current Stock: " + medication.getStock());
            }
        }
    }
}
