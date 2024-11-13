package menu;

import app.Global;
import entities.Doctor;
import entities.User;
import exceptions.InvalidChoiceException;
import services.doctor.AppointmentManagementServiceDoctor;
import services.doctor.MedicalRecordManagementServiceDoctor;

public class DoctorMenu extends Menu {

    @Override
    public void printMenu(User currentUser) {
        int choice = -1;
        do {
            printOptions();
            try {
                choice = Integer.valueOf(Global.getScanner().nextLine());

                callService(choice, (Doctor) currentUser);
            } catch (InvalidChoiceException e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (choice != 7); // 7 = Logout
    }

    private void printOptions() {
        System.out.println("Doctor Menu:");
        System.out.println("1. View Patient Medical Records");
        System.out.println("2. Update Patient Medical Records");
        System.out.println("3. View Personal Schedule"); // same as viewing upcoming appointment
        // System.out.println("4. Set Availability for Appointments"); //same as
        // accepting or declining
        System.out.println("5. Accept or Decline Appointment Requests");
        // System.out.println("6. Record Appointment Outcome"); //the moment you accept
        // i assume u update straight away
        System.out.println("7. Logout");
        System.out.println("Enter choice:");
    }

    private void callService(int choice, Doctor doctor) throws InvalidChoiceException {
        switch (choice) {
            case 1 -> MedicalRecordManagementServiceDoctor.viewMedicalRecords(doctor);
            case 2 -> MedicalRecordManagementServiceDoctor.updateMedicalRecords(doctor);
            case 3 -> AppointmentManagementServiceDoctor.getInstance(doctor).viewPersonalSchedule();
            // case 4 -> AppointmentManagementServiceDoctor.setAvailability(doctor);
            case 5 -> AppointmentManagementServiceDoctor.getInstance(doctor).handleAppointmentRequests();
            // case 6 ->
            // AppointmentManagementServiceDoctor.recordAppointmentOutcome(doctor);
            case 7 -> System.out.println("Logging out...");
            default -> throw new InvalidChoiceException("Invalid choice, please try again.");
        }
    }
}
