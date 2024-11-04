package services.patient;

import app.Global;
import entities.Patient;
import entities.Staff;
import enums.AppointmentStatus;
import enums.MedicalService;
import enums.UserType;
import information.Appointment;
import information.medical.ConsultationNotes;
import information.medical.Medication;
import menu.CustomCalendar;
import information.id.DoctorID;
import information.id.PatientID;
import information.medical.AppointmentOutcomeRecord;
import management.AppointmentDataManager;
import management.DataManager;
import management.StaffDataManager;
import menu.dialogs.Dialog;
import services.AppointmentManagementService;
import services.helper.GenerateIdHelper;

import java.time.LocalDate;
import java.util.*;

public class AppointmentManagementServicePatient extends AppointmentManagementService {

    private final DataManager<Staff,String> staffDataManager;
    private static AppointmentManagementServicePatient appointmentServicePatient;
    private final HashMap<Integer,Staff> doctorList;
    private Patient currentUser;

    public AppointmentManagementServicePatient(
            DataManager<Appointment,String> appointmentDataManager,
            DataManager<Staff,String> staffDataManager,
            Patient currentUser
    ){
        super(appointmentDataManager);
        this.staffDataManager = staffDataManager;
        this.doctorList = new HashMap<>();
        this.staffDataManager.getList();
        this.currentUser = currentUser;
    }


    public static AppointmentManagementServicePatient getInstance(Patient patient){
        if(appointmentServicePatient == null){
            return appointmentServicePatient = new AppointmentManagementServicePatient(
                    AppointmentDataManager.getInstance(),
                    StaffDataManager.getInstance(),
                    patient
                    );
        }
        return appointmentServicePatient;
    }


    public void scheduleAppt(){
        Scanner scanner = Global.getScanner();
        int month, option;
        String time;
        Staff doctorSelected;
        String treatmentTitle, dateOfTreatment;
        CustomCalendar customCalendar = new CustomCalendar();
        ArrayList<Appointment> filteredAppointments;
        while(true){
            try{
                customCalendar.generateMonths();
                month = scanner.nextInt();
                if(month > 12 || month < 1){
                    Dialog.showInvalidInput("month");
                    continue;
                }
                customCalendar.generateSlots();
                option = scanner.nextInt();
                if(option > 8 || option < 1){
                    Dialog.showInvalidInput("time slot");
                    continue;
                }
                doctorSelected = optionForDoctor();
                if(doctorSelected != null){
                    filteredAppointments = filterAppointmentsFromOption(option,month,doctorSelected.getUserInformation().getID().getId());
                    customCalendar.generateCalendar(LocalDate.now().getYear(), month, filteredAppointments);
                    time = optionForTime(option);
                    dateOfTreatment = optionForDate(month,time,doctorSelected.getUserInformation().getID().getId());
                    if(dateOfTreatment != null){
                        treatmentTitle = optionForTreatment();
                        if(bookAppointment(
                                Integer.parseInt(dateOfTreatment),
                                month,
                                option,
                                currentUser.getUserInformation().getID().getId(),
                                treatmentTitle,
                                doctorSelected.getUserInformation().getID().getId(),
                                doctorSelected.getUserInformation().getPrivateInformation().getName()

                        )){
                            Dialog.showSuccessful("booked");
                        }
                    }
                    break;
                }
            }catch(InputMismatchException | NumberFormatException e){
                System.out.println(e.getMessage());
                scanner.nextLine();
            }
        }

    }

    public boolean bookAppointment(int dateOfTreatment,
                                   int month,
                                   int option,
                                   String patientId,
                                   String treatment,
                                   String doctorId,
                                   String doctorName)
    {
        LocalDate date = LocalDate.of(LocalDate.now().getYear(),month,dateOfTreatment);
        PatientID patientID = new PatientID();
        patientID.setId(patientId);
        DoctorID doctorID = new DoctorID();
        doctorID.setId(doctorId);

        return getAppointmentDataManager().add(
                new Appointment(
                        patientID,
                        doctorID,
                        AppointmentStatus.PENDING,
                        new AppointmentOutcomeRecord(new ConsultationNotes(),new ArrayList<Medication>()),
                        optionForTime(option),
                        date.toString(),
                        MedicalService.valueOf(treatment),
                        GenerateIdHelper.generateId("AP"),
                        doctorName

                )
        );
    }

    public boolean validAppointment(int date, int month, String time, String doctorId) {
        LocalDate datePicked = LocalDate.of(LocalDate.now().getYear(),month,date);
        ArrayList<Appointment> filteredList;
        if(LocalDate.now().isAfter(datePicked)){
            System.out.println("Invalid, the date has passed!");
            return false;
        }
        filteredList = filterAppointmentsFromField(time,month,doctorId);
        for(Appointment temp : filteredList){
            if (temp.getAppointmentStatus() == AppointmentStatus.CONFIRMED && temp.getDateOfAppointment() == date) {
                System.out.println("The date has been booked");
                return false;
            }
        }
        return true;
    }

    public void cancelAppointment(){
        HashMap<Integer,Appointment> appointmentHashMap = filterPendingConfirmedForPatient(currentUser.getUserInformation().getID().getId());
        String choice;
            while(true){
                try{
                    System.out.println("These are the following appointments:");
                    displayPendingConfirmedAppointmentsForThisPatient(appointmentHashMap,"delete");
                    System.out.println("press (-) to go back");
                    choice = Global.getScanner().next();
                    if(choice.charAt(0) == '-'){
                        break;
                    }
                    if(appointmentHashMap.containsKey(Integer.parseInt(choice))){
                        String appointmentId = appointmentHashMap.get(Integer.parseInt(choice)).getAppointmentID();
                        if(getAppointmentDataManager().delete(appointmentId)){
                            Dialog.showSuccessful("deleted");
                            break;
                        } else {
                            Dialog.showInvalidInput("Appointment");
                        }
                    }
                }catch(InputMismatchException  | NumberFormatException e ){
                    Dialog.showInvalidInput("Appointment");
                    System.out.println(e.getMessage());
                    Global.getScanner().nextLine();
                }
            }
    }


    public void updateAppointmentForPatient(){
        HashMap<Integer,Appointment> appointmentHashMap = filterPendingConfirmedForPatient(currentUser.getUserInformation().getID().getId());
        int appointmentChoice, updateChoice, timeChoice;
        while(true){
            try{
                System.out.println("These are the following appointments:");
                displayPendingConfirmedAppointmentsForThisPatient(appointmentHashMap,"update");
                System.out.println("Key in the choice");
                appointmentChoice = Global.getScanner().nextInt();
                if(appointmentHashMap.containsKey(appointmentChoice)){
                    System.out.println("What do you want to update on?");
                    System.out.println("(1) Time");
                    System.out.println("(2) Date");
                    updateChoice = Global.getScanner().nextInt();
                  Appointment appointmentFound = appointmentHashMap.get(appointmentChoice);
                  CustomCalendar customCalendar = new CustomCalendar();
                  if(updateChoice == 1){
                     customCalendar.generateSlots();
                     timeChoice = Global.getScanner().nextInt();
                     String timeChosen = optionForTime(timeChoice);
                     if(
                             validAppointment(appointmentFound.getDateOfAppointment(),
                             appointmentFound.getMonthOfAppointment(), timeChosen,
                             appointmentFound.getDoctorId().getId()))
                     {
                                     if(updateAppointment(appointmentFound,timeChosen,1)){
                                         Dialog.showSuccessful("updated time");
                                         break;
                                     }
                     }
                  }
                  if(updateChoice == 2){
                      String dateOfTreatment;
                      customCalendar.generateCalendar(LocalDate.now().getYear(),
                              appointmentFound.getMonthOfAppointment(),
                              filterAppointmentsFromField(appointmentFound.getTimeOfTreatment(),
                                      appointmentFound.getMonthOfAppointment(),
                                      appointmentFound.getDoctorId().getId())
                              );
                      dateOfTreatment = optionForDate(
                              appointmentFound.getMonthOfAppointment(),
                              appointmentFound.getTimeOfTreatment(),
                              appointmentFound.getDoctorId().getId()
                              );
                      if(dateOfTreatment != null){
                          if(updateAppointment(appointmentFound,dateOfTreatment,2)){
                              Dialog.showSuccessful("updated date");
                              break;
                          }
                      }
                  }
                } else {

                }
            }catch(InputMismatchException  | NumberFormatException e ){
                Dialog.showInvalidInput("Appointment");
                System.out.println(e.getMessage());
                Global.getScanner().nextLine();
            }
        }
     }

    public void displayPendingConfirmedAppointmentsForThisPatient(HashMap<Integer,Appointment> appointmentHashMap, String keyword){
        System.out.println(appointmentHashMap);
        appointmentHashMap.forEach((key,value) -> {
            System.out.println("=========================");
            System.out.println("Press (" + key + ") to " + keyword + " " + value.getAppointmentID());
            System.out.println("Appointment Date: " + value.getDateOfTreatment());
            System.out.println("Appointment Time: " + value.getTimeOfTreatment());
            System.out.println("Treatment: " + value.getTreatmentTitle());
            System.out.println("Doctor in charge: Dr " + value.getDoctorName());
            System.out.println("Doctor ID: Dr " + value.getDoctorId());
            System.out.println("Status:  " + value.getAppointmentStatus());
        });
    }


    public void displayAppointmentsForThisPatient() {
        ArrayList<Appointment> confirmedList = filterConfirmedAppointmentsForPatient(currentUser.getUserInformation().getID().getId());
        ArrayList<Appointment> pendingList = filterPendingAppointmentsForPatient(currentUser.getUserInformation().getID().getId());
        ArrayList<Appointment> cancelledList = filterCancelledAppointmentsForPatient(currentUser.getUserInformation().getID().getId());
        System.out.println();
        System.out.println("=== Confirmed appointments ===");
        for(Appointment temp : confirmedList){
            System.out.println("=========================");
            System.out.println("Appointment ID: " + temp.getAppointmentID());
            System.out.println("Appointment Date: " + temp.getDateOfTreatment());
            System.out.println("Appointment Time: " + temp.getTimeOfTreatment());
            System.out.println("Treatment: " + temp.getTreatmentTitle());
            System.out.println("Doctor in charge: Dr " + temp.getDoctorName());
            System.out.println("Doctor ID: Dr " + temp.getDoctorId());
            System.out.println("Status:  " + temp.getAppointmentStatus());
            System.out.println("=========================");
        }
        System.out.println();
        System.out.println("=== Pending appointments ===");
        for(Appointment temp : pendingList){
            System.out.println("=========================");
            System.out.println("Appointment Date: " + temp.getDateOfTreatment());
            System.out.println("Appointment Time: " + temp.getTimeOfTreatment());
            System.out.println("Treatment: " + temp.getTreatmentTitle());
            System.out.println("Doctor in charge: Dr " + temp.getDoctorName());
            System.out.println("Doctor ID: Dr " + temp.getDoctorId());
            System.out.println("Status:  " + temp.getAppointmentStatus());
            System.out.println("=========================");
        }
        System.out.println();
        System.out.println("=== Cancelled appointments ===");
        for(Appointment temp : cancelledList){
            System.out.println("=========================");
            System.out.println("Appointment Date: " + temp.getDateOfTreatment());
            System.out.println("Appointment Time: " + temp.getTimeOfTreatment());
            System.out.println("Treatment: " + temp.getTreatmentTitle());
            System.out.println("Doctor in charge: Dr " + temp.getDoctorName());
            System.out.println("Doctor ID: Dr " + temp.getDoctorId());
            System.out.println("Status:  " + temp.getAppointmentStatus());
            System.out.println("=========================");
        }
    }

    public void displayOutcomeRecordsForThisPatient() {
        System.out.println("=== Past appointments ===");
        for (Appointment temp : currentUser.getMedicalInformation().getPastTreatments()) {
            System.out.println("=========================");
            System.out.println("Appointment Date: " + temp.getDateOfTreatment());
            System.out.println("Appointment Time: " + temp.getTimeOfTreatment());
            System.out.println("Treatment: " + temp.getTreatmentTitle());
            System.out.println("Doctor in charge: Dr " + temp.getDoctorName());
            System.out.println("Doctor ID: Dr " + temp.getDoctorId());
            System.out.println("Status:  " + temp.getAppointmentStatus());
            System.out.println("Medications:");
            int count = 1;
            for (Medication med : temp.getAppointmentOutcomeRecord().getMedications()) {
                System.out.println(count + ". " + med.getName());
                count++;
            }
            System.out.println("=========================");
            System.out.println("Analysis Report");
            System.out.println(temp.getAppointmentOutcomeRecord().getConsultationNotes().getCriticalDetails());
            System.out.println(temp.getAppointmentOutcomeRecord().getConsultationNotes().getComplaints());
            System.out.println(temp.getAppointmentOutcomeRecord().getConsultationNotes().getFurtherInfo());
            System.out.println("=========================");
        }
    }

    public ArrayList<Appointment> filterAppointmentsFromField(String time, int month, String doctorId) {
        // deep copy of arraylist
        ArrayList<Appointment> filteredList = new ArrayList<>();
        ArrayList<Appointment> originalList = getAllAppointments();
        for(Appointment temp : originalList){
            if(temp.getTimeOfTreatment().equals(time)
                    && temp.getMonthOfAppointment() == month
                    && temp.getDoctorId().getId().equals(doctorId)
                    && temp.getAppointmentStatus() == AppointmentStatus.CONFIRMED
            ){
                filteredList.add(temp);
            }
        }
        Collections.sort(filteredList);
        return filteredList;
    }

    public ArrayList<Appointment> filterAppointmentsFromOption(int option, int monthField, String doctorId) {
        // deep copy of arraylist
        String time = optionForTime(option);
        ArrayList<Appointment> filteredList = new ArrayList<>();
        ArrayList<Appointment> originalList = getAllAppointments();
        for(Appointment temp : originalList){
            if(temp.getTimeOfTreatment().equals(time) &&
                    temp.getMonthOfAppointment() == monthField &&
                    temp.getDoctorId().getId().equals(doctorId) &&
                    temp.getAppointmentStatus() == AppointmentStatus.CONFIRMED
            ){
                filteredList.add(temp);
            }
        }
        Collections.sort(filteredList);
        return filteredList;
    }



    public String optionForTreatment() {
        int treatmentOption;
        while (true) {
            try {
                System.out.println("What is the treatment that you want to book for? ");
                int count = 1;
                for(MedicalService service : MedicalService.values()){
                    System.out.println(count + ". for " + service.toString());
                    count++;
                }
                treatmentOption = Global.getScanner().nextInt();
                if (treatmentOption >= 1 && treatmentOption <= 7) {
                    switch (treatmentOption){
                        case 1 -> {
                            return MedicalService.XRAY.name();
                        }
                        case 2 -> {
                            return MedicalService.CONSULTATION.name();
                        }
                        case 3 -> {
                            return MedicalService.BLOOD_TEST.name();
                        }
                        case 4 -> {
                            return MedicalService.MRI.name();
                        }
                        case 5 -> {
                            return MedicalService.ULTRASOUND.name();
                        }
                        case 6 -> {
                            return MedicalService.VACCINATION.name();
                        }
                        case 7 -> {
                            return MedicalService.PHYSIOTHERAPY.name();
                        }
                    }
                    break;
                }
            } catch (NumberFormatException | InputMismatchException e) {
                System.out.println(e.getMessage());
                Global.getScanner().nextLine();
            }
        }
        return null;
    }

    public String optionForDate(int month, String time, String doctorId){
        String dateOfTreatment;
        Scanner scanner = Global.getScanner();
        while(true){
            try{
                System.out.println();
                System.out.println("Which date do you want to pick? ");
                System.out.println("or Press - to go back");
                dateOfTreatment = scanner.next();
                if(dateOfTreatment.charAt(0) == '-'){
                    return null;
                }
                if(Integer.parseInt(dateOfTreatment) < 1 || Integer.parseInt(dateOfTreatment) > 31){
                    Dialog.showInvalidInput("date");
                    continue;
                }
                if(validAppointment(
                        Integer.parseInt(dateOfTreatment),
                        month,
                        time,
                        doctorId
                )){
                    return dateOfTreatment;
                }
            }catch(InputMismatchException  | NumberFormatException e){
                Dialog.showInvalidInput("date");
                System.out.println(e.getMessage());
                scanner.nextLine();
            }
        }
    }


    public Staff optionForDoctor(){
        Scanner scanner = Global.getScanner();
        ArrayList<Staff> staffList = staffDataManager.getList();
        int doctorOption;
        int count = 1;
        for(Staff staff : staffList){
            if(staff.getUserInformation().getUserType().equals(UserType.DOCTOR)){
                doctorList.put(count++, staff);
            }
        }
        while(true){
            try{
                System.out.println("These are the following doctors:");
                doctorList.forEach((key, value) -> {
                    System.out.println("Press (" + key + ") for Dr " +
                            value.getUserInformation().getPrivateInformation().getName() +
                            " ID: " + value.getUserInformation().getID().getId());
                });
                System.out.println("Which doctor do you want?");
                doctorOption = scanner.nextInt();
                if(doctorList.containsKey(doctorOption)){
                    return doctorList.get(doctorOption);
                } else {
                    Dialog.showInvalidInput("doctor");
                }
            }catch(InputMismatchException  | NumberFormatException e ) {
                Dialog.showInvalidInput("doctor");
                System.out.println(e.getMessage());
                scanner.nextLine();
            }
        }

    }


    public String optionForTime(int option){
        switch (option){
            case 1 -> {
                return "0900";
            }
            case 2 -> {
                return "1000";
            }
            case 3 -> {
                return "1100";
            }
            case 4 -> {
                return "1200";
            }
            case 5 -> {
                return "1300";
            }
            case 6 -> {
                return "1400";
            }
            case 7 -> {
                return "1500";
            }
            case 8 -> {
                return "1600";
            }
            default ->  {
                return "invalid";
            }
        }
    }
}
