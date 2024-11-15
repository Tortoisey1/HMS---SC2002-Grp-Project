package menu;

import entities.Patient;
import entities.User;
import exceptions.InvalidChoiceException;
import information.Appointment;
import services.patient.AppointmentManagementServicePatient;
import services.patient.InformationAccessServicePatient;
import app.Global;
import services.patient.MedicalBillingServicePatient;


/**
 * Menu for Patient only that inherits basic behaviours from Menu
 * Display menu to patient that logged in
 */
public class PatientMenu extends Menu {

    /**
     * Main function for the menu to run the relevant services
     */
    @Override
    public void printMenu(User user) {
        while (true) {
            printOptions();

            try {
                int choice = Integer.parseInt(Global.getScanner().next());
                // can cast since checked when logging in, this menu only called when type is
                // matched
                callService(choice, (Patient) user);
            } catch (Exception e) {
                // TODO: handle exception
            }

        }
    }

    /**
     * Print the options available for patient
     */
    private void printOptions() {
        System.out.println("Choice 1: View Medical Record\n" +
                "Choice 2: Update Personal Information\n" +
                "Choice 3: Schedule an Appointment\n" +
                "Choice 4: Reschedule an Appointment\n" +
                "Choice 5: Cancel an Appointment\n" +
                "Choice 6: View Scheduled Appointments\n" +
                "Choice 7: View Past Apointment Outcome Records\n" +
                "Choice 8: View Outstanding Billing\n" +
                "Choice 9: Logout");
    }

    /**
     * Function for each service required for its respective business logics
     * @param choice is the input given by user
     * @param patient is Patient that logged in
     * Used to pass in to the services {@link InformationAccessServicePatient},
     * {@link AppointmentManagementServicePatient}, {@link MedicalBillingServicePatient},
     */
    private void callService(int choice, Patient patient) throws InvalidChoiceException {
        switch (choice) {
            case 1:
                InformationAccessServicePatient.viewMedicalRecord(patient);
                break;
            case 2:
                InformationAccessServicePatient.updatePersonalInformation(patient);
                break;
            case 3:
                AppointmentManagementServicePatient.getInstance(patient).scheduleAppt();
                break;
            case 4:
                AppointmentManagementServicePatient.getInstance(patient).updateAppointmentForPatient();
                break;
            case 5:
                AppointmentManagementServicePatient.getInstance(patient).cancelAppointment();
                break;
            case 6:
                AppointmentManagementServicePatient.getInstance(patient).displayAppointmentsForThisPatient();
                break;
            case 7:
                AppointmentManagementServicePatient.getInstance(patient).displayOutcomeRecordsForThisPatient();
                break;
            case 8:
                MedicalBillingServicePatient.getInstance(patient).retrieveUnPaidBills();
                break;
            case 9:
                System.out.println("Successful logout");
                System.exit(0);
                break;

            default:
                throw new InvalidChoiceException("No such choice please select another option");
        }
    }




}
