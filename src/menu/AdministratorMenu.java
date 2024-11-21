package menu;

import entities.User;
import services.administrator.AdministratorService;
import services.administrator.AdministratorService_Replenishment;

import java.util.Scanner;

import app.Global;

/**
 * AdministratorMenu
 * Represents the menu interface for administrators, allowing them to perform
 * various management tasks
 * such as managing hospital staff, viewing appointment details, handling
 * medication inventory, and
 * approving replenishment requests. This class extends the `Menu` class and
 * provides administrator-specific functionalities.
 */
public class AdministratorMenu extends Menu {

    private AdministratorService adminService;

    /**
     * Constructor to initialize the AdministratorMenu.
     * Sets up the `AdministratorService` to handle hospital staff and inventory
     * management.
     */
    public AdministratorMenu() {
        this.adminService = new AdministratorService();
    }

    /**
     * Displays the Administrator menu and allows interaction with the available
     * options.
     * The administrator can manage staff, view appointments, handle inventory,
     * approve replenishment requests, or log out.
     * 
     * @param currentUser The current user who is logged in, expected to be an
     *                    administrator.
     */
    @Override
    public void printMenu(User currentUser) {
        int choice;
        do {
            System.out.println("Administrator Menu:");
            System.out.println("1. View and Manage Hospital Staff");
            System.out.println("2. View Appointment Details");
            System.out.println("3. View and Manage Medication Inventory");
            System.out.println("4. Approve Replenishment Requests");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(Global.getScanner().nextLine());
                handleChoice(choice); // Handle the administrator's choice of action
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                choice = -1; // Invalid input to continue the loop
            }
        } while (choice != 5); // 5 = Logout
    }

    /**
     * Handles the chosen option from the Administrator menu.
     * Routes the choice to the appropriate service or function.
     * 
     * @param choice The administrator's choice of menu option.
     */
    private void handleChoice(int choice) {
        switch (choice) {
            case 1 -> viewAndManageStaff(); // Manage hospital staff
            case 2 -> viewAppointmentDetails(); // View appointment details
            case 3 -> manageMedicationInventory(); // Manage medication inventory
            case 4 -> approveReplenishmentRequests(); // Approve replenishment requests
            case 5 -> System.out.println("Logging out..."); // Log out
            default -> System.out.println("Invalid choice. Please try again."); // Handle invalid input
        }
    }

    /**
     * Handles the viewing and management of hospital staff.
     * Uses the `AdministratorService` to provide staff-related functionalities.
     */
    private void viewAndManageStaff() {
        System.out.println("Viewing and managing hospital staff...");
        adminService.manageStaff();
    }

    /**
     * Displays details of hospital appointments.
     * Uses the `AdministratorService` to fetch and display appointment-related
     * data.
     */
    private void viewAppointmentDetails() {
        System.out.println("Viewing appointment details...");
        adminService.viewAppointmentDetails();
    }

    /**
     * Manages the hospital's medication inventory.
     * Uses the `AdministratorService` to view and update inventory data.
     */
    private void manageMedicationInventory() {
        System.out.println("Managing medication inventory...");
        adminService.manageInventory();
    }

    /**
     * Approves or rejects replenishment requests for the hospital's inventory.
     * Interacts with the `AdministratorService_Replenishment` for request
     * processing.
     */
    private void approveReplenishmentRequests() {
        Scanner scanner = Global.getScanner();
        System.out.print("Enter the replenishment request ID: ");
        String replenishmentId = scanner.nextLine();

        System.out.print("Approve this request? (yes/no): ");
        String input = scanner.nextLine();
        boolean isApproved = input.equalsIgnoreCase("yes");

        AdministratorService_Replenishment replenishmentService = new AdministratorService_Replenishment();
        replenishmentService.approveReplenishmentRequest(replenishmentId, isApproved);
    }
}
