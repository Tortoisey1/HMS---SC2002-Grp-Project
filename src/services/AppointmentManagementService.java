package services;

import enums.AppointmentStatus;
import information.Appointment;
import management.DataManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.stream.StreamSupport;

public abstract class AppointmentManagementService {

    private final DataManager<Appointment,String> appointmentDataManager;

    public AppointmentManagementService(DataManager<Appointment,String> appointmentDataManager){
        this.appointmentDataManager = appointmentDataManager;
    }


    public DataManager<Appointment,String> getAppointmentDataManager(){
        return appointmentDataManager;
    }


    public ArrayList<Appointment> getAllAppointments() {
        return appointmentDataManager.getList();
    }


    public ArrayList<Appointment> filterPendingAppointments(){
        ArrayList<Appointment> originalList = getAllAppointments();
        ArrayList<Appointment> filteredList = new ArrayList<>();
        for(Appointment temp : originalList){
                if(temp.getAppointmentStatus() == AppointmentStatus.PENDING){
                    filteredList.add(temp);
                }
        }
        return filteredList;
    }


    public ArrayList<Appointment> filterPendingAppointmentsForPatient(String patientId){
        ArrayList<Appointment> originalList = filterPendingAppointments();
        ArrayList<Appointment> filteredList = new ArrayList<>();
        for(Appointment temp : originalList){
            if(temp.getPatientId().getId().equals(patientId)){
                filteredList.add(temp);
            }
        }
        Collections.sort(filteredList);
        return filteredList;
    }

    public ArrayList<Appointment> getPendingAppointmentList(){
        return filterPendingAppointments();
    }

    public Appointment getSpecificPendingAppointment(String appointmentId){
        ArrayList<Appointment> filteredList = filterPendingAppointments();
        for(Appointment temp : filteredList){
            if(temp.getAppointmentID().equals(appointmentId)){
                return temp;
            }
        }
        return null;
    }

    public ArrayList<Appointment> filterConfirmedAppointment(){
        ArrayList<Appointment> originalList = getAllAppointments();
        ArrayList<Appointment> filteredList = new ArrayList<>();
        for(Appointment temp : originalList){
            if(temp.getAppointmentStatus() == AppointmentStatus.CONFIRMED){
                filteredList.add(temp);
            }
        }
        return filteredList;
    }

    public ArrayList<Appointment> filterConfirmedAppointmentsForPatient(String patientId){
        ArrayList<Appointment> originalList = filterConfirmedAppointment();
        ArrayList<Appointment> filteredList = new ArrayList<>();
        for(Appointment temp : originalList){
            if(temp.getPatientId().getId().equals(patientId)){
                filteredList.add(temp);
            }
        }
        Collections.sort(filteredList);
        return filteredList;
    }

    public ArrayList<Appointment> getConfirmedAppointmentList(){
        return filterConfirmedAppointment();
    }

    public Appointment getSpecificConfirmedAppointment(String appointmentId){
        ArrayList<Appointment> filteredList = filterConfirmedAppointment();
        for(Appointment temp : filteredList){
            if(temp.getAppointmentID().equals(appointmentId)){
                return temp;
            }
        }
        return null;
    }

    public ArrayList<Appointment> filterCancelledAppointment(){
        ArrayList<Appointment> originalList = getAllAppointments();
        ArrayList<Appointment> filteredList = new ArrayList<>();
        for(Appointment temp : originalList){
            if(temp.getAppointmentStatus() == AppointmentStatus.CANCELLED){
                filteredList.add(temp);
            }
        }
        return filteredList;
    }

    public ArrayList<Appointment> filterCancelledAppointmentsForPatient(String patientId){
        ArrayList<Appointment> originalList = filterCancelledAppointment();
        ArrayList<Appointment> filteredList = new ArrayList<>();
        for(Appointment temp : originalList){
            if(temp.getPatientId().getId().equals(patientId)){
                filteredList.add(temp);
            }
        }
        Collections.sort(filteredList);
        return filteredList;
    }

    public ArrayList<Appointment> getCancelledAppointmentList(){
        return this.filterCancelledAppointment();
    }

    public Appointment getSpecificCancelledAppointment(String appointmentId){
        ArrayList<Appointment> filteredList = filterCancelledAppointment();
        for(Appointment temp : filteredList){
            if(temp.getAppointmentID().equals(appointmentId)){
                return temp;
            }
        }
        return null;
    }

    public HashMap<Integer,Appointment> filterPendingConfirmedForPatient(String patientId){
        ArrayList<Appointment> originalList = getAllAppointments();
        ArrayList<Appointment> filteredList = new ArrayList<>();
        HashMap<Integer,Appointment> appointmentHashMap = new HashMap<>();
        for(Appointment temp : originalList){
            if((temp.getAppointmentStatus() == AppointmentStatus.CONFIRMED && temp.getPatientId().getId().equals(patientId))
                    || (temp.getAppointmentStatus() == AppointmentStatus.PENDING && temp.getPatientId().getId().equals(patientId))
            ){
                filteredList.add(temp);
            }
        }
        int count = 1;
        for(Appointment temp : filteredList){
            appointmentHashMap.put(count++, temp);
        }
        return appointmentHashMap;
    }

    public boolean updateAppointment(Appointment appointment, String newInput, int choice){
        switch (choice){
            case 1 -> {
                appointment.setTimeOfTreatment(newInput);
            }
            case  2 -> {
                LocalDate date = LocalDate.of(
                        LocalDate.now().getYear(),
                        appointment.getMonthOfAppointment(),
                        Integer.parseInt(newInput));
                appointment.setDateOfTreatment(date.toString());
            }
            case 3 -> {
                appointment.setAppointmentStatus(AppointmentStatus.valueOf(newInput));
            }

        }
        return appointmentDataManager.update(appointment);
    }
}
