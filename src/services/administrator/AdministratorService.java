package services.administrator;

import entities.Staff;
import enums.UserType;
import exceptions.StaffExistException;
import exceptions.StaffDoesNotExistException;
import information.PrivateInformation;
import information.UserInformation;
import information.id.AdministratorID;
import information.id.UserID;
import app.Global;
import services.UserService;
import services.InventoryManagementService;
import services.administrator.ConcreteStaffManagementService;
import information.ContactInfo;
import enums.Gender;



public class AdministratorService extends UserService {
    private InventoryManagementService inventoryManagementService;
    private ConcreteStaffManagementService staffManagementService;

    public AdministratorService() {
        inventoryManagementService = new InventoryManagementService();
        staffManagementService = new ConcreteStaffManagementService(); // Use the concrete class here
    }

    // Method for managing staff, providing options for adding, updating, or
    // removing staff
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
        // Collect staff details
        System.out.print("Enter Staff Name: ");
        String name = Global.getScanner().nextLine();
    
        System.out.print("Enter Date of Birth (e.g., YYYY-MM-DD): ");
        String dateOfBirth = Global.getScanner().nextLine();
    
        System.out.print("Enter Gender (MALE or FEMALE): ");
        String genderStr = Global.getScanner().nextLine();
        Gender gender = Gender.valueOf(genderStr.toUpperCase()); // Assuming Gender enum has MALE and FEMALE
    
        System.out.print("Enter Contact Phone Number: ");
        String phoneNumber = Global.getScanner().nextLine();
    
        System.out.print("Enter Contact Email: ");
        String email = Global.getScanner().nextLine();
    
        // Assuming ContactInfo has a constructor that accepts phone number and email
        ContactInfo contactInfo = new ContactInfo(phoneNumber, email);
    
        // Create PrivateInformation object with all necessary parameters
        PrivateInformation privateInfo = new PrivateInformation(name, dateOfBirth, gender, contactInfo);
    
        // Other UserInformation details
        System.out.print("Enter Staff Role (e.g., ADMINISTRATOR): ");
        String role = Global.getScanner().nextLine();
        UserType userType = UserType.valueOf(role.toUpperCase());
    
        UserID userId = new AdministratorID(); // Assuming appropriate UserID implementation
        System.out.print("Enter Staff Password: ");
        String password = Global.getScanner().nextLine();
    
        // Create UserInformation object
        UserInformation userInfo = new UserInformation(userType, userId, password, privateInfo);
    
        // Create the Staff object
        Staff staff = new Staff(userInfo);
    
        // Add the new staff to the management service
        try {
            staffManagementService.addStaff(staff);
            System.out.println("Staff added successfully.");
        } catch (StaffExistException e) {
            System.out.println(e.getMessage());
        }
    }
    

    private void removeStaff() {
        System.out.print("Enter Staff ID to remove: ");
        String staffId = Global.getScanner().nextLine();
        UserID userId = new AdministratorID(staffId); // Or any appropriate UserID implementation

        try {
            Staff staff = staffManagementService.findStaff(userId);
            if (staff != null) {
                staffManagementService.removeStaff(staff);
                System.out.println("Staff removed successfully.");
            } else {
                System.out.println("Staff not found.");
            }
        } catch (StaffDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateStaff() {
        System.out.print("Enter Staff ID to update: ");
        String staffId = Global.getScanner().nextLine();
        UserID userId = new AdministratorID(staffId); // Convert to UserID

        Staff staff = staffManagementService.findStaff(userId);
        if (staff != null) {
            System.out.println("Select the information you want to update:");
            System.out.println("1. User Type");
            System.out.println("2. Password");
            System.out.print("Enter your choice: ");

            int choice = Integer.parseInt(Global.getScanner().nextLine());

            switch (choice) {
                case 1:
                    System.out.print("Enter new User Type (e.g., ADMIN, DOCTOR): ");
                    String userTypeStr = Global.getScanner().nextLine();
                    try {
                        UserType newUserType = UserType.valueOf(userTypeStr.toUpperCase());
                        staff.getUserInformation().setUserType(newUserType);
                        System.out.println("User Type updated successfully.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid User Type. No changes were made.");
                    }
                    break;

                case 2:
                    System.out.print("Enter new Password: ");
                    String newPassword = Global.getScanner().nextLine();
                    staff.getUserInformation().setPassword(newPassword);
                    System.out.println("Password updated successfully.");
                    break;

                default:
                    System.out.println("Invalid choice. No changes were made.");
                    break;
            }

            // Save the updated staff information
            staffManagementService.updateStaffList(staff);
            System.out.println("Staff information updated successfully.");

        } else {
            System.out.println("Staff not found.");
        }
    }

    public void viewAppointmentDetails() {
        System.out.println("Viewing appointment details...");
        // Logic to retrieve and display appointment details
        // Placeholder implementation
        // Example: appointmentService.getAllAppointments()
    }

    public void manageInventory() {
        System.out.println("Managing inventory...");
        // Example: Use inventoryManagementService to manage stock levels
        // Placeholder implementation for managing inventory
    }

    public void approveReplenishmentRequest() {
        System.out.println("Approving replenishment requests...");
        // Logic to approve replenishment requests
        // Placeholder implementation for approval
    }

    @Override
    public boolean logout() {
        System.out.println("Administrator logged out.");
        return true;
    }
}
