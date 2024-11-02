package management;
import enums.AppointmentStatus;
import enums.MedicalService;
import information.Appointment;
import information.id.DoctorID;
import information.id.PatientID;
import information.medical.AppointmentOutcomeRecord;
import information.medical.ConsultationNotes;
import information.medical.Medication;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class AppointmentDataManager implements DataManager<Appointment,String> {
    private ArrayList<Appointment> appointmentList;
    private static DataManager<Appointment,String> appointmentDataManager;
    private final String filePath;

    AppointmentDataManager(){
        filePath = "src/data/appointments.csv";
        appointmentList = new ArrayList<>();
        try {
            retrieveAll();
        }catch (IOException e){
            System.out.println(e);
        }
    }


    public static DataManager<Appointment, String> getInstance(){
        if(appointmentDataManager == null){
            return appointmentDataManager = new AppointmentDataManager();
        }
        return  appointmentDataManager;
    }


    public Appointment retrieve(String AppointmentId) {
        for(Appointment appointment : appointmentList) {
            if (appointment.getAppointmentID().equals(AppointmentId)) {
                return appointment;
            }
        }
        return null;
    }


    public boolean update(Appointment newAppointment) {
        int count = 0;
        for(Appointment temp : appointmentList){
            if(temp.getAppointmentID().equals(newAppointment.getAppointmentID())){
                appointmentList.set(count,newAppointment);
                return true;
            }
        }
        return false;
    }

    public boolean delete(String appointmentId) {
        Appointment appointment = retrieve(appointmentId);
        if(appointment != null){
            return appointmentList.remove(appointment);
        }
        return false;
    }

    public boolean add(Appointment appointment) {
         return appointmentList.add(appointment);
    }

    public void retrieveAll() throws IOException {
        String line = "";
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        int count = 0;
        while((line = br.readLine()) != null){
            if (count > 0) {
                String[] data = line.split(",");
                PatientID patientId = new PatientID();
                patientId.setId(data[1]);
                DoctorID doctorId = new DoctorID();
                doctorId.setId(data[6]);
                List<Medication> medications = new ArrayList<>();
                ConsultationNotes consultationNotes = new ConsultationNotes();
                if(!data[8].equals("None")){
                    String [] splitMedicationField = data[8].split(":");
                    for(String temp : splitMedicationField){
                        Medication med = new Medication();
                        med.setName(temp);
                        medications.add(med);
                    }
                }

                if(!data[9].equals("None")){
                    String [] splitConsultationField = data[9].split(":");
                    consultationNotes.setCriticalDetails(splitConsultationField[0]);
                    consultationNotes.setComplaints(splitConsultationField[1]);
                    consultationNotes.setFurtherInfo(splitConsultationField[2]);
                }

                appointmentList.add(
                            new Appointment(
                                    patientId,
                                    doctorId,
                                    AppointmentStatus.valueOf(data[2]),
                                    new AppointmentOutcomeRecord(consultationNotes,medications),
                                    data[5],
                                    data[3],
                                    MedicalService.valueOf(data[4]),
                                    data[0],
                                    data[7]
                            )
                    );

            }
            count++;
        }
        br.close();
    }


    public void writeAll() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
        String[] header = {"Appointment_id","Patient_id","Appointment_status","Date",
                "Treatment", "Time", "Doctor_id", "Doctor_name", "Prescribed_medication", "Consultation_notes"};
        for(String h : header){
            bw.write(h);
            bw.write(",");
        }
        for(Appointment temp : appointmentList){
            bw.newLine();
            bw.write(temp.getAppointmentID());
            bw.write(",");
            bw.write(temp.getPatientId().getId());
            bw.write(",");
            bw.write(temp.getAppointmentStatus().toString());
            bw.write(",");
            bw.write(temp.getDateOfTreatment());
            bw.write(",");
            bw.write(temp.getTreatmentTitle().toString());
            bw.write(",");
            bw.write(temp.getTimeOfTreatment());
            bw.write(",");
            bw.write(temp.getDoctorId().getId());
            bw.write(",");
            bw.write(temp.getDoctorName());
            bw.write(",");
            if(!temp.getAppointmentOutcomeRecord().getMedications().isEmpty()) {
                for(Medication medicine : temp.getAppointmentOutcomeRecord().getMedications()){
                    bw.write(medicine.getName());
                    bw.write(":");
                }
            } else {
                bw.write("None");
            }
            bw.write(",");

            if(temp.getAppointmentOutcomeRecord().getConsultationNotes().getCriticalDetails().equals("None")){
                bw.write("None");
            } else {
                bw.write(temp.getAppointmentOutcomeRecord().getConsultationNotes().getCriticalDetails());
                bw.write(":");
                bw.write(temp.getAppointmentOutcomeRecord().getConsultationNotes().getComplaints());
                bw.write(":");
                bw.write(temp.getAppointmentOutcomeRecord().getConsultationNotes().getFurtherInfo());
                bw.write(":");
            }
        }
        bw.flush();
        bw.close();
    }
    public ArrayList<Appointment> getList() {
        return appointmentList;
    }

}
