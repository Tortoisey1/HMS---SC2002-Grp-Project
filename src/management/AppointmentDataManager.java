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

/**
 * A DataManager for Appointment that implements interface {@link DataManager}
 * Collect data from CSV file with {@link BufferedReader} and write back to CSV file with {@link BufferedWriter}
 * {@param Appointment} for the data collected
 * {@param String} for the Appointment ID in String
 * Holds all the data type {@link Appointment}
 */
public class AppointmentDataManager implements DataManager<Appointment,String> {
    private ArrayList<Appointment> appointmentList;
    private static DataManager<Appointment,String> appointmentDataManager;
    private final String filePath;

    /**
     * Constructs an AppointmentDataManager
     * {@code filePath} for the directory of the CSV
     * {@code retrieveAll()} to retrieve all the data from CSV and
     * instantiate each of the data to type {@link Appointment} when app begin
     * and finally, add each data to {@code appointmentList}
     */
    public AppointmentDataManager(){
        filePath = "src/data/appointments.csv";
        appointmentList = new ArrayList<>();
        try {
            retrieveAll();
        }catch (IOException e){
            System.out.println(e);
        }
    }

    /**
     * Singleton for the AppointmentDataManager
     * Declared and initialized the Constructor to {@code appointmentDataManager} when app begin
     * Type {@link DataManager}
     * Down casting when needed
     */
    public static DataManager<Appointment, String> getInstance(){
        if(appointmentDataManager == null){
            return appointmentDataManager = new AppointmentDataManager();
        }
        return  appointmentDataManager;
    }

    /**
     * Retrieve an appointment based on the {@param AppointmentId} from {@code appointmentList}
     * @return the {@link Appointment} if found else null
     */
    public Appointment retrieve(String AppointmentId) {
        for(Appointment appointment : appointmentList) {
            if (appointment.getAppointmentID().equals(AppointmentId)) {
                return appointment;
            }
        }
        return null;
    }


    /**
     * update details of Appointment with {@param newAppointment}
     * search through the {@code appointmentList} and update it based on the appointment id on that index
     * @return true if update is successful else false
     */
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

    /**
     * delete the appointment from {@code appointmentList} based on the {@param appointmentId}
     * retrieve the memory reference of Appointment and if not null
     * {@code appointmentList} removes it
     * @return true if delete is successful else false
     */
    public boolean delete(String appointmentId) {
        Appointment appointment = retrieve(appointmentId);
        if(appointment != null){
            return appointmentList.remove(appointment);
        }
        return false;
    }

    /**
     * add the Appointment {@param appointment} to {@code appointmentList }
     * @return true if add is successful else false
     */
    public boolean add(Appointment appointment) {
         return appointmentList.add(appointment);
    }

    /**
     * Retrieve all the Appointments with {@link BufferedReader} from CSV of path {@code filePath}
     * Start collecting when count == 1 as count = 0 is the headers
     * Add each data and instantiate them to type {@link Appointment}
     * Add it to {@code appointmentList}
     * @throws IOException when file not found
     */
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

    /**
     * Write all the data of type {@link Appointment} back into CSV with {@link BufferedWriter}
     * path {@code filePath}
     * For each Appointment from {@code appointmentList}
     * Parse each fields from Appointment to String before adding into the CSV
     * @throws IOException when file not found
     */
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
    /**
     * Retrieve {@link ArrayList} of all Appointments with type {@link Appointment}
     * @return {@code appointmentList}
     */
    public ArrayList<Appointment> getList() {
        return appointmentList;
    }

}
