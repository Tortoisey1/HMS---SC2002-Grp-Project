package management;

import java.io.*;
import java.util.ArrayList;

import enums.MedicalService;
import information.Appointment;
import enums.AppointmentStatus;

public class AppointmentDataManager implements DataManager<Appointment, String> {
    private static ArrayList<Appointment> appointmentList = new ArrayList<Appointment>();
    private static AppointmentDataManager appointmentDataManager;
    private final String inputFilePath = "data/appointments.csv";
    private final String outputFilePath = "data/newappointments.csv";

    public AppointmentDataManager() {
        try {
            retrieveAll();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static AppointmentDataManager getInstance() {
        if (appointmentDataManager == null) {
            return appointmentDataManager = new AppointmentDataManager();
        }
        return appointmentDataManager;
    }

    @Override
    public Appointment retrieve(String AppointmentId) {
        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentId().equals(AppointmentId)) {
                return appointment;
            }
        }
        return null;
    }

    @Override
    public void update(Appointment newAppointment) {
        // int count = 0;
        // try {
        // for (Appointment temp : appointmentList) {
        // if (temp.getAppointmentId().equals(newAppointment.getAppointmentId())) {
        // appointmentList.set(count, newAppointment);
        // writeAll();
        // System.out.println("Updated details");
        // }
        // count++;
        // }

        // writeAll();
        // } catch (IOException e) {
        // System.out.println(e);
        // }
    }

    @Override
    public boolean delete(Appointment appointment) {
        try {
            appointmentList.remove(appointment);
            // writeAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    @Override
    public boolean add(Appointment appointment) {
        try {
            appointmentList.add(appointment);
            // writeAll();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public void retrieveAll() throws IOException {
        String line = "";
        BufferedReader br = new BufferedReader(new FileReader(inputFilePath));
        int count = 0;

        appointmentList.clear();

        while ((line = br.readLine()) != null) {
            if (count > 0) {
                String[] data = line.split(",");
                appointmentList.add(
                        new Appointment(
                                data[0],
                                data[1],
                                data[2],
                                AppointmentStatus.valueOf(data[3]),
                                MedicalService.valueOf(data[4]),
                                data[5],
                                data[6]));
            }
            count++;
        }
        br.close();
    }

    @Override
    public void writeAll() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath, false));
        String[] header = { "Appointment_id", "Date", "Patient_id", "Appointment_status",
                "Treatment", "Time", "Doctor" };

        for (String h : header) {
            bw.write(h);
            bw.write(",");
        }
        for (Appointment temp : appointmentList) {
            bw.newLine();
            bw.write(temp.getAppointmentId());
            bw.write(",");
            bw.write(temp.getDateOfTreatment());
            bw.write(",");
            bw.write(temp.getPatientId());
            bw.write(",");
            bw.write(temp.getStatus().toString());
            bw.write(",");
            bw.write(temp.getMedicalService().toString());
            bw.write(",");
            bw.write(temp.getTimeOfTreatment());
            bw.write(",");
            bw.write(temp.getDoctorID());
        }
        bw.flush();
        bw.close();
    }

    @Override
    public ArrayList<Appointment> getList() {
        return appointmentList;
    }

    public int returnNextId() {
        return appointmentList.size() + 1;
    }

}
