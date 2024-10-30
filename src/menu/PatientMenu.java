package menu;

import entities.Patient;
import entities.User;
import exceptions.InvalidChoiceException;
import services.InformationAccessServicePatient;
import app.Global;

public class PatientMenu implements Menu {

    @Override
    public void printMenu(User user) {
        while (true) {
            printOptions();

            try {
                int choice = Integer.valueOf(Global.getScanner().nextLine());
                int selection = Global.getScanner().nextInt();
                // can cast since checked when logging in, this menu only called when type is
                // matched
                callService(choice, (Patient) user);
            } catch (Exception e) {
                // TODO: handle exception
            }

        }
    }

    private void printOptions() {
        System.out.println("Choice 1: View Medical Record\n" +
                "Choice 2: Update Personal Information\n" +
                "Choice 3: View Available Appointment Slots\n" +
                "Choice 4: Schedule an Appointment\n" +
                "Choice 5: Reschedule an Appointment\n" +
                "Choice 6: Cancel an Appointment\n" +
                "Choice 7: View Scheduled Appointments\n" +
                "Choice 8: View Past Apointment Outcome Records\n" +
                "Choice 9: Logout\n");
    }

    private void callService(int choice, Patient patient) throws InvalidChoiceException {
        switch (choice) {
            case 1:
                InformationAccessServicePatient.viewMedicalRecord(patient);
                break;
            case 2:
                InformationAccessServicePatient.updatePersonalInformation(patient);
                break;
            case 3:
                InformationAccessServicePatient.

                break;
            case 4:

                break;
            case 5:

                break;
            case 6:

                break;
            case 7:

                break;
            case 8:

                break;
            case 9:
                System.out.println("Successful logout");
                System.exit(0);

            default:
                throw new InvalidChoiceException("No such choice please select another option");
        }
    }
}
