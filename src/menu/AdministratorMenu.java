package menu;

import entities.User;
import services.AdministratorService;
import app.Global;

public class AdministratorMenu implements Menu {
    private AdministratorService adminService;

    public AdministratorMenu() {
        this.adminService = new AdministratorService();
    }

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

            choice = Integer.parseInt(Global.getScanner().nextLine());

            switch (choice) {
                case 1:
                    viewAndManageStaff();
                    break;
                case 2:
                    viewAppointmentDetails();
                    break;
                case 3:
                    manageMedicationInventory();
                    break;
                case 4:
                    approveReplenishmentRequests();
                    break;
                case 5:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    private void viewAndManageStaff() {
        System.out.println("Viewing and managing hospital staff...");
        adminService.manageStaff();
    }

    private void viewAppointmentDetails() {
        System.out.println("Viewing appointment details...");
        adminService.viewAppointmentDetails();
    }

    private void manageMedicationInventory() {
        System.out.println("Managing medication inventory...");
        adminService.manageInventory();
    }

    private void approveReplenishmentRequests() {
        System.out.println("Approving replenishment requests...");
        adminService.approveReplenishmentRequest();
    }
}
