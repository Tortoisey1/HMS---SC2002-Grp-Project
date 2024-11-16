package menu;

import app.Global;
import entities.Doctor;
import entities.User;
import exceptions.InvalidChoiceException;
import services.doctor.AppointmentManagementServiceDoctor;
import services.doctor.MedicalRecordManagementServiceDoctor;
import services.pharmacist.AppointmentOutcomeRecordPharmacistService;

public class DoctorMenu extends Menu {


    private MedicalRecordManagementServiceDoctor medicalRecordService;
    //private AppointmentManagementServiceDoctor appointmentManagementService;

    public DoctorMenu(){
        this.medicalRecordService = new MedicalRecordManagementServiceDoctor();
    }

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
        } while (choice != 5); // 5 = Logout
    }

    private void printOptions() {
        System.out.println();
        System.out.println("===== Doctor Menu =====");
        System.out.println("1. View Patient Medical Records");
        System.out.println("2. View Personal Schedule");
        System.out.println("3. Accept or Decline Appointment Requests");
        System.out.println("4. Record Appointment Outcome");
        System.out.println("5. Logout");
        System.out.println("Enter choice:");
    }

    private void callService(int choice, Doctor doctor) throws InvalidChoiceException {
        switch (choice) {
            case 1 -> medicalRecordService.viewMedicalRecords(doctor);
            case 2 -> AppointmentManagementServiceDoctor.getInstance(doctor).viewPersonalSchedule();
            case 3 -> AppointmentManagementServiceDoctor.getInstance(doctor).acceptDecline();
            case 4 -> AppointmentManagementServiceDoctor.getInstance(doctor).recordOutcome();
            case 5 -> System.out.println("Logging out...");
            default -> throw new InvalidChoiceException("Invalid choice, please try again.");
        }
    }
}
