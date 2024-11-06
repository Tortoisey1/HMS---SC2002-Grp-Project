package menu;

import entities.Patient;
import entities.User;
import exceptions.InvalidChoiceException;
import information.Appointment;
import information.CustomCalendar;
import management.AppointmentController;
import services.AppointmentManagementServicePatient;
import services.InformationAccessServicePatient;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import app.Global;

public class PatientMenu implements Menu {

    AppointmentController appointmentController = AppointmentController.getInstance();

    @Override
    public void printMenu(User user) {
        while (true) {
            printOptions();

            try {
                int choice = Integer.valueOf(Global.getScanner().nextLine());
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
                "Choice 3: Appointment Matters\n");
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
                showAppointmentUi(patient);
                break;
        }
    }

    private void showAppointmentUi(Patient patient) {
        int choice;
        boolean valid = true;

        while (true) {

            System.out.println("Press (1) to book appointment");
            System.out.println("Press (2) to view appointments");
            System.out.println("Press (3) to reschedule appointments");
            System.out.println("Press (4) to remove appointment");
            try {
                choice = Integer.valueOf(Global.getScanner().nextLine());
                if (choice >= 1 && choice <= 4) {
                    switch (choice) {
                        case 1:
                            bookingUi(patient);
                            break;
                        case 2:
                            viewApptUi(patient);
                            break;
                        case 3:
                            updateUi(patient.getUserInformation().getID().getId());
                            break;
                        case 4:
                            removeApptUi(patient.getUserInformation().getID().getId());
                            break;
                    }
                } else {
                    throw new InvalidChoiceException("No such choice");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.exit(0);
            }
        }

    }

    public void bookingUi(Patient patient) {
        boolean valid = true;
        CustomCalendar customCalendar = new CustomCalendar();
        int month, option;
        String treatmentTitle, dateOfTreatment;
        do {
            try {
                customCalendar.generateMonths();
                month = Integer.valueOf(Global.getScanner().nextLine());
                if (month > 12 || month < 1) {
                    System.out.println("Invalid input!");
                    valid = false;
                    continue;
                }
                customCalendar.generateSlots();
                option = Integer.valueOf(Global.getScanner().nextLine());
                if (option > 8 || option < 1) {
                    System.out.println("Invalid input!");
                    valid = false;
                    continue;
                }
                boolean innerValid = true;
                do {
                    try {
                        customCalendar.generateCalendar(LocalDate.now().getYear(), month,
                                appointmentController.filterAppointmentsFromOption(option, month));
                        System.out.println();
                        System.out.println("Which date do you want to pick? ");
                        System.out.println("or Press - to go back");
                        dateOfTreatment = Global.getScanner().nextLine();
                        if (dateOfTreatment.charAt(0) == '-') {
                            printOptions();
                            break;
                        }
                        if (Integer.parseInt(dateOfTreatment) < 1 || Integer.parseInt(dateOfTreatment) > 31) {
                            innerValid = false;
                            continue;
                        }
                        innerValid = appointmentController.validAppointment(Integer.parseInt(dateOfTreatment), month,
                                appointmentController.optionForTime(option));
                        System.out.println("What is the treatment that you want to book for? ");
                        treatmentTitle = Global.getScanner().nextLine();
                        do {
                            String doctor = appointmentController.optionForDoctor(showDoctorsUi());
                            if (!doctor.equals("invalid")) {
                                showBookingDialog(
                                        appointmentController.bookAppointment(Integer.parseInt(dateOfTreatment), month,
                                                option, patient.getUserInformation().getID().getId(), treatmentTitle,
                                                doctor));
                            } else {
                                System.out.println("Invalid input!");
                                innerValid = false;
                            }
                        } while (!innerValid);
                    } catch (InputMismatchException | NumberFormatException | DateTimeException e) {
                        System.out.println("Invalid input!");
                        innerValid = false;
                    }
                } while (!innerValid);
            } catch (InputMismatchException e) {
                System.out.println("Invalid input!");
                valid = false;
                Global.getScanner().nextLine();
            }
        } while (!valid);
    }

    public void viewApptUi(Patient patient) {
        ArrayList<Appointment> filteredList;
        int input;
        boolean valid = false;
        do {
            System.out.println("Press (1) to view your upcoming appointments");
            System.out.println("Press (2) to view your past appointments");
            input = Integer.valueOf(Global.getScanner().nextLine());
            if (input == 1 || input == 2) {
                valid = true;
                filteredList = appointmentController
                        .getAppointmentsForPatient(patient.getUserInformation().getID().getId(), input);
                if (input == 1) {
                    System.out.println("Your Upcoming appointments");
                } else {
                    System.out.println("Your History");
                }
                if (filteredList != null) {
                    for (Appointment appointment : filteredList) {
                        System.out.println("==========================================");
                        System.out.println("Appointment ID: " + appointment.getAppointmentId());
                        System.out.println("Your ID: " + appointment.getPatientId());
                        System.out.println("Date: " + appointment.getDateOfTreatment());
                        System.out.println("Appointment Status: " + appointment.getStatus());
                        System.out.println("Treatment: " + appointment.getMedicalService().toString());
                        System.out.println("Time:" + appointment.getTimeOfTreatment());
                        System.out.println("==========================================");
                    }
                } else {
                    System.out.println("No records!");
                }
            }
        } while (!valid);
    }

    public void updateUi(String patientId) {
        System.out.println("Key in your Appointment ID: ");
        Appointment selectedAppointment = appointmentController.retrieveAppointment(Global.getScanner().nextLine(),
                patientId);
        if (selectedAppointment != null) {
            System.out.println("What do you want to reschedule on?");
            System.out.println("Press (1) for Time");
            System.out.println("Press (2) for Date");
            switch (Integer.valueOf(Global.getScanner().nextLine())) {
                case 1:
                    updateTimeUi(selectedAppointment);
                    break;
                case 2:
                    updateDateUi(selectedAppointment);
                    break;
            }
        } else {
            System.out.println("Appointment is not found!");
        }
    }

    public int showDoctorsUi() throws InputMismatchException {
        int input;
        System.out.println("====================");
        ArrayList<String> doctors = new ArrayList<String>(List.of("Dr Mike", "Dr Phil", "Dr Vanessa"));
        for (int i = 0; i < doctors.size(); i++) {
            System.out.println("Press (" + i + ") " + "for " + doctors.get(i));
        }
        input = Integer.valueOf(Global.getScanner().nextLine());
        return input;
    }

    public void updateTimeUi(Appointment selectedAppointment) {
        String timeToUpdate;
        do {
            System.out.println("\n What time do you want to update it to? ");
            timeToUpdate = Global.getScanner().nextLine();
            if ((timeToUpdate != null)
                    && (appointmentController.validAppointment(selectedAppointment.getDateOfAppointment(),
                            selectedAppointment.getMonthOfAppointment(), timeToUpdate))) {
                showUpdateDialog(appointmentController.updateAppointment(selectedAppointment, timeToUpdate, 1));
                break;
            } else {
                System.out.println("Invalid input!");
            }
        } while (true);
    }

    public void removeApptUi(String patientId) {
        System.out.println("What is the Appointment ID? ");
        Appointment selectedAppointment = appointmentController.retrieveAppointment(Global.getScanner().nextLine(),
                patientId);
        if (selectedAppointment != null) {
            showDeleteDialog(appointmentController.deleteAppointment(selectedAppointment));
        } else {
            System.out.println("Appointment is not found!");
        }
    }

    public void updateDateUi(Appointment selectedAppointment) {
        CustomCalendar customCalendar = new CustomCalendar();
        String dateOfTreatment;
        int month = selectedAppointment.getMonthOfAppointment();
        do {
            customCalendar.generateCalendar(LocalDate.now().getYear(), month,
                    appointmentController.filterAppointmentsFromField(selectedAppointment.getTimeOfTreatment(), month));
            System.out.println("\n-----------------------------");
            System.out.println("\nWhich date do you want? ");
            dateOfTreatment = Global.getScanner().nextLine();
            if ((dateOfTreatment != null) && (appointmentController.validAppointment(Integer.parseInt(dateOfTreatment),
                    month, selectedAppointment.getTimeOfTreatment()))) {
                showUpdateDialog(appointmentController.updateAppointment(selectedAppointment, dateOfTreatment, 2));
                break;
            } else {
                System.out.println("Invalid input!");
            }
        } while (true);
    }

    public void showBookingDialog(boolean isSuccessful) {
        if (isSuccessful) {
            System.out.println("====================");
            System.out.println("Successfully booked!");
            System.out.println("====================");
        } else {
            System.out.println("Error with booking!");
        }
    }

    public void showUpdateDialog(boolean isSuccessful) {
        if (isSuccessful) {
            System.out.println("====================");
            System.out.println("Successfully updated!");
            System.out.println("====================");
        } else {
            System.out.println("Error with updating!");
        }
    }

    public void showDeleteDialog(boolean isSuccessful) {
        if (isSuccessful) {
            System.out.println("====================");
            System.out.println("Successfully deleted!");
            System.out.println("====================");
        } else {
            System.out.println("Error with deleting!");
        }
    }
}
