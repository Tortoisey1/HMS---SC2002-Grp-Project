package ui;

import java.util.ArrayList;

import information.Appointment;;

public interface AppointmentInterface {
    boolean bookAppointment(int dateOfTreament, int month, int option, String patientId, String treatment,
            String doctor);

    ArrayList<Appointment> getAllAppointments();

    ArrayList<Appointment> filterAppointmentsFromOption(int option, int month);

    ArrayList<Appointment> filterAppointmentsFromField(String time, int month);

    String optionForTime(int option);

    String optionForDoctor(int option);

    int timeForOption(String time);

    boolean validAppointment(int date, int month, String time);

    ArrayList<Appointment> getAppointmentsForPatient(String patientId, int filterOption);

    boolean updateAppointment(Appointment appointment, String newInput, int choice);

    boolean deleteAppointment(Appointment appointment);

    Appointment retrieveAppointment(String appointmentId, String patientId);
}
