package management;
import java.io.*;
import java.util.ArrayList;

import merged.Appointment;
import merged.AppointmentStatus;

public class AppointmentDataManager implements DataManager<Appointment,String>{
    private ArrayList<Appointment> appointmentList;
    private static AppointmentDataManager appointmentDataManager;
    private final String filePath;

    AppointmentDataManager(){
        filePath = "appointments.csv";
        appointmentList = new ArrayList<>();
        try {
            retrieveAll();
        }catch (IOException e){
            System.out.println(e);
        }
    }


    public static AppointmentDataManager getInstance(){
        if(appointmentDataManager == null){
            return appointmentDataManager = new AppointmentDataManager();
        }
        return  appointmentDataManager;
    }

    @Override
    public Appointment retrieve(String AppointmentId) {
        for(Appointment appointment : appointmentList) {
            if (appointment.getAppointmentId().equals(AppointmentId)) {
                return appointment;
            }
        }
        return null;
    }

    @Override
    public void update(Appointment newAppointment) {
        int count = 0;
        try {
            for(Appointment temp : appointmentList){
                if(temp.getAppointmentId().equals(newAppointment.getAppointmentId())){
                    appointmentList.set(count,newAppointment);
                    writeAll();
                    System.out.println("Updated details");
                }
                count++;
            }

            writeAll();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public boolean delete(Appointment appointment) {
        try{
            appointmentList.remove(appointment);
            writeAll();
        }catch(IOException e){
            System.out.println(e);
        }
        return true;
    }

    @Override
    public boolean add(Appointment appointment) {
         try{
             appointmentList.add(appointment);
             writeAll();
             return true;
         }catch (IOException e){
             System.out.println(e);
         }
         return false;
    }

    @Override
    public void retrieveAll() throws IOException {
        String line = "";
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        int count = 0;
        while((line = br.readLine()) != null){
            if (count > 0) {
                String[] data = line.split(",");
                appointmentList.add(
                        new Appointment(
                                data[0],
                                data[1],
                                AppointmentStatus.valueOf(data[2]),
                                data[3],
                                data[4],
                                data[5],
                                data[6]
                        )
                );
            }
            count++;
        }
        br.close();
    }

    @Override
    public void writeAll() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
        String[] header = {"Appointment_id","Patient_id","Appointment_status","Date",
                "Treatment", "Time", "Doctor"};

        for(String h : header){
            bw.write(h);
            bw.write(",");
        }
        for(Appointment temp : appointmentList){
            bw.newLine();
            bw.write(temp.getAppointmentId());
            bw.write(",");
            bw.write(temp.getPatientId());
            bw.write(",");
            bw.write(temp.getStatus().toString());
            bw.write(",");
            bw.write(temp.getDateOfTreatment());
            bw.write(",");
            bw.write(temp.getTreatmentTitle());
            bw.write(",");
            bw.write(temp.getTimeOfTreatment());
            bw.write(",");
            bw.write(temp.getDoctor());
        }
        bw.flush();
        bw.close();
    }
    @Override
    public ArrayList<Appointment> getList() {
        return appointmentList;
    }

    public int returnNextId(){
       return appointmentList.size() + 1;
    }

}
