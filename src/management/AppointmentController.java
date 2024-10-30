// package management;
// import java.io.IOException;
// import java.time.LocalDate;
// import java.util.*;

// import GenerateIdHelper;
// import merged.Appointment;
// import merged.AppointmentStatus;
// import ui.AppointmentInterface;

// public class AppointmentController implements AppointmentInterface {

//     private final AppointmentDataManager appointmentDataManager;
//     private static AppointmentController appointmentController;
//     public AppointmentController(AppointmentDataManager appointmentDataManager){
//         this.appointmentDataManager = appointmentDataManager;
//     }

//     public static AppointmentController getInstance(){
//         if(appointmentController == null){
//             appointmentController = new AppointmentController(AppointmentDataManager.getInstance());
//         }
//         return appointmentController;
//     }

//     @Override
//     public boolean bookAppointment(int dateOfTreatment, int month, int option, String patientId, String treatment, String doctor) {
//         LocalDate date = LocalDate.of(LocalDate.now().getYear(),month,dateOfTreatment);
//         return appointmentDataManager.add(
//                new Appointment(
//                        GenerateIdHelper.generateId("AP"),
//                        patientId,
//                        AppointmentStatus.UPCOMING,
//                        date.toString(),
//                        treatment,
//                        optionForTime(option),
//                        doctor
//                )
//        );
//     }


//     @Override
//     public boolean validAppointment(int date, int month, String time) {
//         LocalDate datePicked;
//         ArrayList<Appointment> filteredList;
//         filteredList = filterAppointmentsFromField(time,month);
//         datePicked = LocalDate.of(LocalDate.now().getYear(),month,date);
//         if(LocalDate.now().isAfter(datePicked)){
//             System.out.println("Invalid, the date has passed!");
//             return false;
//         }
//         for(Appointment temp : filteredList){
//             if (temp.getDateOfAppointment() == date && temp.getTimeOfTreatment().equals(time)) {
//                 System.out.println("The date has been booked");
//                 return false;
//             }
//         }

//         return true;
//     }

//     @Override
//     public ArrayList<Appointment> getAppointmentsForPatient(String patientId, int filterOption) {
//         ArrayList<Appointment> filteredList = new ArrayList<>();
//         ArrayList<Appointment> originalList = getAllAppointments();
//         switch(filterOption){
//             case 1:
//                 originalList.forEach(appointment -> {
//                     if(appointment.getPatientId().equals(patientId) && appointment.getStatus() == AppointmentStatus.UPCOMING){
//                         filteredList.add(appointment);
//                     }
//                 });
//                 break;
//             case 2:
//                 originalList.forEach((appointment -> {
//                     if(appointment.getPatientId().equals(patientId) && appointment.getStatus() == AppointmentStatus.COMPLETED){
//                         filteredList.add(appointment);
//                     }
//                 }));
//                 break;
//             default:
//                 return null;
//         }
//         return filteredList;
//     }

//     @Override
//     public boolean updateAppointment(Appointment appointment, String newInput, int choice) {
//         if(choice == 1){
//             appointment.setTimeOfTreatment(newInput);
//         }else{
//             LocalDate date = LocalDate.of(LocalDate.now().getYear(),appointment.getMonthOfAppointment(),Integer.parseInt(newInput));
//             appointment.setDateOfTreatment(date.toString());
//         }
//         appointmentDataManager.update(appointment);
//         return true;
//     }

//     @Override
//     public boolean deleteAppointment(Appointment appointment) {
//         appointmentDataManager.delete(appointment);
//         return true;
//     }

//     @Override
//     public Appointment retrieveAppointment(String appointmentId, String patientId) {
//         Appointment appointment = appointmentDataManager.retrieve(appointmentId);
//         if(appointment != null && appointment.getPatientId().equals(patientId)){
//             return appointment;
//         }
//         return null;
//     }

//     @Override
//     public ArrayList<Appointment> getAllAppointments() {
//         return appointmentDataManager.getList();
//     }

//     @Override
//     public ArrayList<Appointment> filterAppointmentsFromOption(int option, int monthField) {
//         String time = optionForTime(option);
//         ArrayList<Appointment> filteredList = new ArrayList<>();
//         ArrayList<Appointment> originalList = getAllAppointments();
//         for(Appointment temp : originalList){
//             if(temp.getTimeOfTreatment().equals(time) && temp.getMonthOfAppointment() == monthField){
//                 filteredList.add(temp);
//             }
//         }
//         Collections.sort(filteredList);
//         return filteredList;
//     }

//     @Override
//     public ArrayList<Appointment> filterAppointmentsFromField(String time, int month) {
//         ArrayList<Appointment> filteredList = new ArrayList<>();
//         ArrayList<Appointment> originalList = getAllAppointments();
//         for(Appointment temp : originalList){
//             if(temp.getTimeOfTreatment().equals(time) && temp.getMonthOfAppointment() == month){
//                 filteredList.add(temp);
//             }
//         }
//         Collections.sort(filteredList);
//         return filteredList;
//     }

//     @Override
//     public String optionForDoctor(int option){
//         switch (option){
//             case 0 -> {
//                 return "Dr Mike";
//             }
//             case 1 -> {
//                 return "Dr Phil";
//             }
//             case 2 -> {
//                 return "Dr Vanessa";
//             }
//             default ->  {
//                 return "invalid";
//             }
//         }
//     }

//     @Override
//     public int timeForOption(String time){
//         switch (time){
//             case "0900" -> {
//                 return 1;
//             }
//             case "1000" -> {
//                 return 2;
//             }
//             case "1100" -> {
//                 return 3;
//             }
//             case "1200" -> {
//                 return 4;
//             }
//             case "1300" -> {
//                 return 5;
//             }
//             case "1400" -> {
//                 return 6;
//             }
//             case "1500" -> {
//                 return 7;
//             }
//             case "1600" -> {
//                 return 8;
//             }
//             default ->  {
//                 return -1;
//             }
//         }
//     }


//     public String optionForTime(int option){
//         switch (option){
//             case 1 -> {
//                 return "0900";
//             }
//             case 2 -> {
//                 return "1000";
//             }
//             case 3 -> {
//                 return "1100";
//             }
//             case 4 -> {
//                 return "1200";
//             }
//             case 5 -> {
//                 return "1300";
//             }
//             case 6 -> {
//                 return "1400";
//             }
//             case 7 -> {
//                 return "1500";
//             }
//             case 8 -> {
//                 return "1600";
//             }
//             default ->  {
//                 return "invalid";
//             }
//         }
//     }
// }
