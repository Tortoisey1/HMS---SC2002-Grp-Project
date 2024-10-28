package menu;

import app.AppLogic;

public class PatientMenu implements Menu {

    @Override
    public void printMenu() {
        while (true) {
            print();

            int choice = AppLogic.getScanner().nextLine();

            callService(choice);

        }
    }

    private void print() {
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

    private void callService(int choice) {

    }
}
