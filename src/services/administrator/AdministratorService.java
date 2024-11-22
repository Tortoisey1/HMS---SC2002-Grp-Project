package services.administrator;

import app.Global;
import enums.UserType;
import enums.AppointmentStatus;
import enums.Gender;
import entities.Staff;
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

/**
 * AdministratorService
 * Provides functionalities for administrators to manage hospital operations,
 * including staff management,
 * appointment handling, and inventory control. This class interacts with
 * various data managers to perform
 * CRUD operations and retrieve relevant information.
 */

public class AdministratorService {
    private InventoryDataManager inventoryDataManager;
    private StaffDataManager staffDataManager;
    private AppointmentDataManager appointmentDataManager;

    /**
     * Constructor to initialize the AdministratorService.
     * Retrieves instances of data managers for staff, inventory, and appointments.
     */

    public AdministratorService() {
        this.inventoryDataManager = InventoryDataManager.getInstance();
        this.staffDataManager = (StaffDataManager) StaffDataManager.getInstance();
        this.appointmentDataManager = (AppointmentDataManager) AppointmentDataManager.getInstance();
    }

    /**
     * Manages hospital staff with options to add, remove, update, or filter staff.
     * Presents a menu for administrators to choose an operation.
     */

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

    /**
     * Filters and displays a list of staff based on role, gender, or age.
     * Prompts the user for filter criteria and displays matching staff records.
     */

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

    /**
     * Adds a new staff member to the hospital system.
     * Collects staff information from the user and saves it in the staff data
     * manager.
     */

    private void addStaff() {
        Scanner scanner = Global.getScanner();

        // Collect staff details
        System.out.print("Enter Staff Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Date of Birth (e.g., DD/MM/YYYY): ");
        String dateOfBirth = scanner.nextLine();
        System.out.print("Enter Gender (MALE or FEMALE): ");
        Gender gender = Gender.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("Enter Contact Phone Number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter Contact Email: ");
        String email = scanner.nextLine();

        ContactInfo contactInfo = new ContactInfo(phoneNumber, email);
        PrivateInformation privateInfo = new PrivateInformation(name, dateOfBirth, gender, contactInfo);

        // System.out.print("Enter Staff Role (e.g., ADMINISTRATOR): ");
        UserID userId = null;
        UserType userType = null;
        try {
            // Get user input for staff role
            System.out.print("Enter Staff Role (e.g., ADMINISTRATOR): ");
            String role = scanner.nextLine().toUpperCase();

            // Convert role to UserType enum
            userType = UserType.valueOf(role);

            // Generate the class name for the corresponding ID
            String idClassName = userType.name().substring(0, 1).toUpperCase()
                    + userType.name().substring(1).toLowerCase() + "ID";

            // Use reflection to load the class and create an instance
            Class<?> idClass = Class.forName("information.id." + idClassName); // Assuming IDs are in the "ids" package
            userId = (UserID) idClass.getDeclaredConstructor().newInstance();

            // Print the user ID details
            System.out.println("Created ID: " + userId.toString());

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid role entered. Please ensure the role matches the defined UserType enum.");
        } catch (ClassNotFoundException e) {
            System.out.println("No matching ID class found for the entered role.");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }

        // UserType userType = UserType.valueOf(scanner.nextLine().toUpperCase());
        // UserID userId = new AdministratorID();
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

    /**
     * Removes an existing staff member by their ID.
     * Prompts the user to enter the ID and removes the staff record if found.
     */

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

    /**
     * Updates an existing staff member's information.
     * Allows modification of user type or password based on user input.
     */

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

    /**
     * Displays a filtered list of staff members based on specified criteria.
     * Filters staff by role, gender, and/or age.
     * 
     * @param role   Role to filter by, or null for no role filtering.
     * @param gender Gender to filter by, or null for no gender filtering.
     * @param age    Age to filter by, or null for no age filtering.
     */

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

    /**
     * Views details of all hospital appointments.
     * Retrieves and displays data for each appointment, including completed
     * outcomes and medications prescribed.
     */

    public void viewAppointmentDetails() {
        int j = 1;
        List<Appointment> appointments = appointmentDataManager.getList();
        System.out.println("Appointment Details:");
        for (Appointment appointment : appointments) {
            System.out.println("Appointment " + (j++));
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
                String notes = appointment.getAppointmentOutcomeRecord().getConsultationNotes().getCriticalDetails();
                System.out.print("Critical Details: ");
                if (notes != null)
                    System.out.println(notes);
                else
                    System.out.println("None");

                notes = appointment.getAppointmentOutcomeRecord().getConsultationNotes().getComplaints();
                System.out.print("Complaints: ");
                if (notes != null)
                    System.out.println(notes);
                else
                    System.out.println("None");

                notes = appointment.getAppointmentOutcomeRecord().getConsultationNotes().getFurtherInfo();
                System.out.print("Further information: ");
                if (notes != null)
                    System.out.println(notes);
                else
                    System.out.println("None");

                System.out.println();

                if (appointment.getAppointmentOutcomeRecord().getMedications().size() == 0) {
                    System.out.println("No prescription(s)");
                    System.out.println();
                    return;
                }

                System.out.println("Medication prescribed: ");
                int k = 1;

                for (Medication prescription : appointment.getAppointmentOutcomeRecord().getMedications()) {
                    System.out.println("Prescription " + (k++) + ": " + prescription.getName());
                }

            }
            System.out.println("-----------------------------");
        }
    }

    /**
     * Manages hospital inventory, including viewing and updating medication stock.
     * Prompts the user to select an action for inventory management.
     */

    public void manageInventory() {
        System.out.println("Manage Inventory:");
        System.out.println("1. View Inventory");
        System.out.println("2. Add or Update Medication Stock");
        // System.out.println("3. Update Low Stock Alert Level");
        System.out.print("Choose an option: ");

        int choice = Integer.parseInt(Global.getScanner().nextLine());

        switch (choice) {
            case 1:
                displayInventory();
                break;
            case 2:
                addOrUpdateMedicationStock();
                break;
            // case 3:
            // updateLowStockAlertLevel();
            // break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    /**
     * Displays the current medication inventory.
     * Lists details of each medication, including stock levels.
     */

    private void displayInventory() {
        List<Medication> inventoryList = inventoryDataManager.getList();
        System.out.println("Current Medication Inventory:");
        for (Medication medication : inventoryList) {
            System.out.println("Medication ID: " + medication.getMedicationId());
            System.out.println("Name: " + medication.getName());
            System.out.println("Stock: " + medication.getStock());
            // System.out.println("Low Stock Alert Level: " +
            // medication.getLowStockLevel());
            System.out.println("-----------------------------");
        }
    }

    /**
     * Adds or updates the stock quantity of a medication.
     * Prompts the user to enter the medication ID and stock quantity.
     */

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

    /**
     * Initializes system data by loading staff and inventory data from files.
     * Handles errors if data retrieval fails.
     */

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
