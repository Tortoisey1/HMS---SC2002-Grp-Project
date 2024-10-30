package services;

import app.AppLogic;

import entities.Doctor;
import enums.AppointmentStatus;
import exceptions.InvalidAmountException;
import information.AppointmentInformation;
import information.medical.AppointmentOutcomeRecord;

public class AppointmentManagementServicePatient {
    // Patients can:
    // ● View available appointment slots with doctors.
    public void viewAppointmentSlots(){
        //call the main doctor holder
        system.appoint
        
        //then for each doctor in the list print the appointment slots
    }

    // ● Schedule Appointments: Choose a doctor, date, and available time slot
    // to schedule an appointment.

    public void scheduleAppointment() {
        // print out all the doctors first
        system.doctors.print();

        System.out.println("Which doctor would you like?");
        int choice = Integer.valueOf(AppLogic.getScanner().nextLine());
        if (choice <= 0 || choice > system.doctors.length) {
            throw new InvalidAmountException(
                    "please choose a number within the range shown for available doctors, Dont play me");
        }

        Doctor doctor = system.doctors[choice - 1];

        // then view the available time slot for the doctor
        doctor.viewAppointmentSlots();

        System.out.println("Which slot do you want?");

        // must set the doctor to be taken

        // add to the appointments of the doctor
        mainAppointmentList
                .addAppointment(new AppointmentInformation(null, null, AppointmentStatus.PENDING, null, null));

        System.out.println("");

    }

    // ● Reschedule Appointments: Change an existing appointment to a new
    // slot, ensuring no conflicts. Upon rescheduling, slot availability will be
    // updated automatically.

    public void rescheduleAppointment() {
        // show from the main appointment list to filter only for this patient

        if (appointmentList.length == 0) {
            System.out.println("You have no appointments made how can you reschedule anything");
            return;
        }

        System.out.println("Which appointment would you like to reschedule?");
        int choice = Integer.valueOf(AppLogic.getScanner().nextLine());

        if (choice <= 0 || choice > appointments) {
            throw new InvalidAmountException(
                    "Please choose a number within the range of appointments you have already made");
        }

        // print the available timing for the doctor

        // choose an available timing

        // then change the appointment date time

    }

    // ● Cancel Appointments: Cancel an existing appointment. Upon successful
    // cancellation, slot availability will be updated automatically.
    public void cancelAppointment() {
        // print from the main appointment list to filter only for this patient
        if (appointmentList.length == 0) {
            System.out.println("You have no appointments made how can you reschedule anything");
            return;
        }

        System.out.println("Which appointment would you like to reschedule?");
        int choice = Integer.valueOf(AppLogic.getScanner().nextLine());

        if (choice <= 0 || choice > appointments) {
            throw new InvalidAmountException(
                    "Please choose a number within the range of appointments you have already made");
        }

    }

    // ○ Patients can view the status of their scheduled appointments.

    public void statusScheduledAppointments() {
        // from main appointmentlist filter to only the appointment of this patient
        // for each in the appointment list print out the status
    }

    // ○ Patients can also view their Appointment Outcome Records of past
    // appointments.

    public void viewAppointmentOutcomeRecords() {
        // ask if you want to view all or a specific date time
        AppointmentOutcomeRecord records = patient.

                System.out.println("Which records would you like to see?");
        int choice = Integer.valueOf(AppLogic.getScanner().nextLine());

        if (choice <= 0 || choice > records) {
            throw new InvalidAmountException(
                    "Please choose a number within the range of records you have already");
        }
    }
}
