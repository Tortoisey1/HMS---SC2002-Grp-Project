package services.doctor;

import entities.Doctor;
import entities.Patient;
import entities.Staff;
import enums.AppointmentStatus;
import enums.MedicalService;
import enums.MedicationStatus;
import information.Appointment;
import information.medical.ConsultationNotes;
import information.medical.Medication;
import management.AppointmentDataManager;
import management.MedicationRequestDataManager;
import management.PatientDataManager;
import management.DataManager;
import menu.CustomCalendar;
import services.AppointmentManagementService;
import app.Global;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class AppointmentManagementServiceDoctor extends AppointmentManagementService {

    private DataManager<Medication,String> requestDataManager;
    //private DataManager<Appointment, String> appointmentDataManager;



    private static AppointmentManagementServiceDoctor appointmentManagementServiceDoctor;
    private Doctor currentDoctor;

    public AppointmentManagementServiceDoctor(
            DataManager<Appointment, String> appointmentDataManager, Doctor currentDoctor) {
        super(appointmentDataManager);
        this.currentDoctor = currentDoctor;
        this.requestDataManager = MedicationRequestDataManager.getInstance();
        //this.appointmentDataManager = AppointmentDataManager.getInstance();
    }

    public static AppointmentManagementServiceDoctor getInstance(Doctor doctor) {
        if (appointmentManagementServiceDoctor == null) {
            appointmentManagementServiceDoctor = new AppointmentManagementServiceDoctor(
                    AppointmentDataManager.getInstance(), doctor);
        }
        return appointmentManagementServiceDoctor;
    }

    public void viewPersonalSchedule() {
        // get the filtered appointments for this specific doctor
        int i=1;
        //List<Appointment> filteredAppointments = getAppointmentsForDoctor(
        //      this.currentDoctor.getUserInformation().getID().getId());

        ArrayList<Appointment> filteredAppointments= getConfirmedAppointmentList();

        System.out.println("=== Personal Schedule ===");
        if (filteredAppointments.size() == 0) {
            System.out.println("No appointments scheduled.");
            return;
        }
        else {

            for (Appointment appointment : filteredAppointments) {

                if(appointment.getAppointmentStatus()== AppointmentStatus.CONFIRMED)
                {
                    System.out.println("Appointment "+ (i++) + ":");
                    System.out.println("Treatment: " + appointment.getTreatmentTitle().toString());
                    System.out.println("Date: "+ appointment.getDateOfTreatment().toString());
                    System.out.println("Time: " + appointment.getTimeOfTreatment().toString());
                    System.out.println();

                }

            }
        }
    }



    public void acceptDecline()
    {
        //List<Appointment> filteredAppointments = getAppointmentsForDoctor(
        //      this.currentDoctor.getUserInformation().getID().getId());


        List<Appointment> filteredAppointments = getPendingAppointmentsForDoctor(
                this.currentDoctor.getUserInformation().getID().getId());

        if (filteredAppointments.size() == 0) {
            System.out.println("No pending appointments");
            return;
        }

        int i=1;
        for(Appointment appointment : filteredAppointments)
        {

            System.out.println("Appointment " + (i++) + ": ");
            System.out.println("Treatment: "+appointment.getTreatmentTitle());
            System.out.println("Date: "+ appointment.getDateOfTreatment());
            System.out.println("Time: "+ appointment.getTimeOfTreatment());
            System.out.println();


            System.out.println("Would you like to accept?");
            System.out.println("1. YES");
            System.out.println("2. NO");
            int choice=-1;

            try {
                choice = Integer.valueOf(Global.getScanner().nextLine());

                switch (choice) {
                    case 1:
                        System.out.println("Validating appointment ...");
                        validateAvailability(appointment,getConfirmedAppointmentList());
                        break;
                    case 2:
                        System.out.println("Declining appointment ...");
                        appointment.setAppointmentStatus(AppointmentStatus.CANCELLED);
                        break;
                    default:
                        System.out.println("Invalid choice!");
                        break;
                }

            } catch (Exception e) {
                // TODO: handle exception
                System.out.println(e.getMessage());
            }


        }




    }


    public void validateAvailability(Appointment appointment,ArrayList<Appointment> confirmedAppointments)
    {

        String targetDate=appointment.getDateOfTreatment();
        String targetTime=appointment.getTimeOfTreatment();

        for (Appointment confirmed : confirmedAppointments) {

            if(confirmed.getDateOfTreatment().equals(targetDate)
                    && confirmed.getTimeOfTreatment().equals(targetTime))
            {
                System.out.println("Schedule clash with a confirmed appointment");
                System.out.println("Appointment acceptance failed!");
                return;
            }

        }

        System.out.println("Appointment successfully accepted!");
        appointment.setAppointmentStatus(AppointmentStatus.CONFIRMED);
        return;

    }


    public void recordOutcome()
    {
        int treatmentChoice=-1;
        String medicineInput,correctMedicineName;

        System.out.println("Please enter Patient ID: ");
        String PatientID= String.valueOf(Global.getScanner().nextLine());

        System.out.println("Please enter Appointment ID: ");
        String AppointmentID= String.valueOf(Global.getScanner().nextLine());

        Appointment currentAppointment=getSpecificConfirmedAppointment(AppointmentID);

        //Check if appointment even exist or has been approved by any doctor

        if(currentAppointment== null || currentAppointment.getAppointmentStatus()!=AppointmentStatus.CONFIRMED)
        {
            System.out.println("Appointment has not been completed or no such appointment exist!");
            return;
        }



        // Check if doctor has access, correct patient for this CONFIRMED appointment

        if(PatientID.equals(currentAppointment.getPatientId().getId())
                && currentAppointment.getDoctorId().getId().equals(this.currentDoctor.getUserInformation().getID().getId()))
        {
            System.out.println();
            System.out.println("Appointment found!");


            System.out.println("===== Please input consultation notes!");
            System.out.println("Critical Details:");
            String criticalDetails=String.valueOf(Global.getScanner().nextLine());

            System.out.println("Complaints:");
            String complaints=String.valueOf(Global.getScanner().nextLine());

            System.out.println("Further Information:");
            String furtherInfo=String.valueOf(Global.getScanner().nextLine());

            currentAppointment.getAppointmentOutcomeRecord()
                    .setConsultationNotes(new ConsultationNotes(criticalDetails, complaints, furtherInfo));

            while (true)
            {
                System.out.println("0. Exit prescription process");
                System.out.println("1. Prescription needed for patient");

                try
                {
                    int selection = Integer.valueOf(Global.getScanner().nextLine());

                    if(selection==0)
                    {
                        System.out.println("Stop adding medicines, moving on ...");
                        break;
                    }
                    else if(selection==1)
                    {
                        System.out.println("Enter the name of prescription");
                        medicineInput = String.valueOf(Global.getScanner().nextLine());

                        correctMedicineName=medicineValidator(medicineInput);

                        if(correctMedicineName==null) System.out.println("Medicine does not exist, please try again");
                        else
                        {
                            currentAppointment.getAppointmentOutcomeRecord()
                                    .addMedications(new Medication(correctMedicineName, MedicationStatus.PENDING));
                        }


                    }
                    else System.out.println("Invalid choice");

                }catch (Exception e){
                    System.out.println(e.getMessage());
                    //break;
                }

            } // end of prescription intake

            while(true)
            {
                try
                {
                    System.out.println("===== Please input the treatment done =====");
                    MedicalService.printMedicalServices();
                    treatmentChoice = Integer.valueOf(Global.getScanner().nextLine());

                    if(treatmentChoice>0 && treatmentChoice<8) break;
                    else System.out.println("Invalid input for treatment");

                }catch (Exception e) {
                    // TODO: handle exception
                    System.out.println(e.getMessage());
                }


            }

            currentAppointment.setAppointmentStatus(AppointmentStatus.COMPLETED);
            currentAppointment.setMedicalService(MedicalService.values()[treatmentChoice - 1]);

            Patient patient = PatientDataManager.getInstance().retrieve(PatientID);
            patient.getMedicalInformation().getPastTreatments().add(currentAppointment);

        }
        else
        {
            System.out.println("Not certified to process appointment. Goodbye");
            return;

            // Doctor either does not have access, correct patient, or not a CONFIRMED appointment by Doctor
        }


    }









    public String medicineValidator(String medicineInput)
    {

        String normalizedInput=medicineInput.replace(" ", "").toLowerCase();

        ArrayList<Medication> medicationList= requestDataManager.getList();

        for(Medication correctMedicineName : medicationList)
        {
            String normalizedReference=correctMedicineName.getName().replace(" ", "").toLowerCase();

            if(normalizedInput.equals(normalizedReference))
            {
                return correctMedicineName.getName();
            }
        }


        return null;
    }

}