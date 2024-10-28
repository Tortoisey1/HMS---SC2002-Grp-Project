import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;

public class PatientUi {
    private final PatientInterface patientController;
    private final AppointmentInterface appointmentController;
    private Patient patient;
    private String patientId;
    private final Scanner scanner;


    PatientUi(PatientInterface patientController, String patientId, AppointmentInterface appointmentController){
        this.appointmentController = appointmentController;
        this.patientController = patientController;
        this.patientId = patientId;
        this.patient = patientController.getDetails(this.patientId);
        scanner = Global.scanner();
    }

    void showOptions(){
            System.out.println("==== Welcome to our portal " + patient.getName() +" ====");
            int option;
            boolean valid = true;
            do {
                try {
                    System.out.println("Press (1) to view your medical records \n" +
                            "Press (2) to update your contact information \n" + "Press (3) for appointment matters");
                    option = scanner.nextInt();
                    if(option == 1 || option == 2 || option == 3){
                        switch (option){
                            case 1 -> showDetailsUi();
                            case 2 -> showUpdateUi();
                            case 3 -> showAppointmentUi();
                        }
                    }else{
                       valid = false;
                    }

                }catch(InputMismatchException e) {
                    valid = false;
                    scanner.next();
                }
            } while(!valid);
    }


    public Patient getPatient() {
        return patient;
    }

    void showUpdateUi(){
        if(patient != null){
            int option;
            boolean valid = true;
            do{
                System.out.println("===== Which Contact info do you want to update =====");
                System.out.println("(1) Email");
                System.out.println("(2) Phone");
                try{
                    option = scanner.nextInt();
                    if(option == 1 || option == 2){
                        switch (option){
                            case 1:
                                System.out.println("Key in your new email address: ");
                                patientController.updateDetails(patient,1,scanner.next());
                                System.out.println("Updated your email");
                                break;
                            case 2:
                                System.out.println("Key in your new phone number: ");
                                patientController.updateDetails(patient,2,scanner.next());
                                System.out.println("Updated your phone number");
                                break;
                        }
                    }else{
                        valid = false;
                    }
                }catch(InputMismatchException e){
                    valid = false;
                    scanner.next();
                }
            }while(!valid);
            showOptions();
        }
    }


    void showDetailsUi(){
        if(patient != null){
            System.out.println("======= These are your medical records ======");
            System.out.println("Your ID: " + patient.getPatientId());
            System.out.println("Your Name: " + patient.getAge());
            System.out.println("Age: " + patient.getName());
            System.out.println("Your DOB: " + patient.getDOB());
            System.out.println("BloodType: " + patient.getBloodType());
            System.out.println("Email Address: " + patient.getContactInfo().getEmail());
            System.out.println("Phone Number: " + patient.getContactInfo().getPhoneNumber());
            System.out.println("Your illnesses: ");
            for(String illness : patient.getIllnesses()){
                System.out.println(illness);
            }
            showOptions();
        }
    }

    void showAppointmentUi(){
        int choice;
        boolean valid = true;

        do {
            System.out.println("Press (1) to book appointment");
            System.out.println("Press (2) to view appointments");
            System.out.println("Press (3) to reschedule appointments");
            System.out.println("Press (4) to remove appointment");
            try {
                choice = scanner.nextInt();
                if(choice >= 1 && choice <= 4){
                    switch (choice){
                        case 1:
                            bookingUi();
                            break;
                        case 2:
                            viewApptUi();
                            break;
                        case 3:
                            updateUi();
                            break;
                        case 4:
                            removeApptUi();
                            break;
                    }
                }else{
                    valid = false;
                }
            }catch(InputMismatchException e){
                valid = false;
                scanner.next();
            }
        }while(!valid);
        showOptions();
    }

    public String getPatientId() {
        return patientId;
    }


    public void viewApptUi(){
        ArrayList<Appointment> filteredList;
        int input;
        boolean valid = false;
        do {
            System.out.println("Press (1) to view your upcoming appointments");
            System.out.println("Press (2) to view your past appointments");
            input = scanner.nextInt();
            if(input == 1 || input == 2){
                valid = true;
                filteredList = appointmentController.getAppointmentsForPatient(patientId,input);
                if(input == 1){
                    System.out.println("Your Upcoming appointments");
                }else{
                    System.out.println("Your History");
                }
                if(filteredList != null){
                    filteredList.forEach((appointment -> {
                        System.out.println("==========================================");
                        System.out.println("Appointment ID: " + appointment.getAppointmentId());
                        System.out.println("Your ID: " + appointment.getPatientId());
                        System.out.println("Date: " + appointment.getDateOfTreatment());
                        System.out.println("Appointment Status: " + appointment.getStatus());
                        System.out.println("Treatment: " + appointment.getTreatmentTitle());
                        System.out.println("Time:" + appointment.getTimeOfTreatment());
                        System.out.println("==========================================");
                    }));
                }else{
                    System.out.println("No records!");
                }
            }
        }while(!valid);
    }


    public void updateUi(){
                System.out.println("Key in your Appointment ID: ");
                Appointment selectedAppointment = appointmentController.retrieveAppointment(scanner.next(),patientId);
                if(selectedAppointment != null){
                    System.out.println("What do you want to reschedule on?");
                    System.out.println("Press (1) for Time");
                    System.out.println("Press (2) for Date");
                    switch (scanner.nextInt()){
                        case 1:
                                updateTimeUi(selectedAppointment);
                                break;
                        case 2:
                                updateDateUi(selectedAppointment);
                                break;
                    }
                }else{
                    System.out.println("Appointment is not found!");
                }
    }


    public void updateTimeUi(Appointment selectedAppointment){
        String timeToUpdate;
        do{
            System.out.println("\n What time do you want to update it to? ");
            timeToUpdate = scanner.next();
            if((timeToUpdate != null) && (appointmentController.validAppointment(selectedAppointment.getDateOfAppointment(),selectedAppointment.getMonthOfAppointment(),timeToUpdate))){
                showUpdateDialog(appointmentController.updateAppointment(selectedAppointment,timeToUpdate,1));
                break;
            }else{
                System.out.println("Invalid input!");
            }
        }while (true);
    }


    public void removeApptUi(){
        System.out.println("What is the Appointment ID? ");
        Appointment selectedAppointment = appointmentController.retrieveAppointment(scanner.next(),patientId);
        if(selectedAppointment != null){
            showDeleteDialog(appointmentController.deleteAppointment(selectedAppointment));
        }else{
            System.out.println("Appointment is not found!");
        }
    }

    public void updateDateUi(Appointment selectedAppointment){
        CustomCalendar customCalendar = new CustomCalendar();
        String dateOfTreatment;
        int month = selectedAppointment.getMonthOfAppointment();
        do{
            customCalendar.generateCalendar(LocalDate.now().getYear(), month,appointmentController.filterAppointmentsFromField(selectedAppointment.getTimeOfTreatment(),month));
            System.out.println("\n-----------------------------");
            System.out.println("\nWhich date do you want? ");
            dateOfTreatment = scanner.next();
            if((dateOfTreatment != null) && (appointmentController.validAppointment(Integer.parseInt(dateOfTreatment),month,selectedAppointment.getTimeOfTreatment()))){
                showUpdateDialog(appointmentController.updateAppointment(selectedAppointment,dateOfTreatment,2));
                break;
            }else{
                System.out.println("Invalid input!");
            }
        } while(true);
    }


    public void bookingUi(){
        boolean valid = true;
        CustomCalendar customCalendar = new CustomCalendar();
        int month, option;
        String treatmentTitle, dateOfTreatment;
        do{
            try{
                customCalendar.generateMonths();
                month = scanner.nextInt();
                if(month > 12 || month < 1){
                    System.out.println("Invalid input!");
                    valid = false;
                    continue;
                }
                customCalendar.generateSlots();
                option = scanner.nextInt();
                if(option > 8 || option < 1){
                    System.out.println("Invalid input!");
                    valid = false;
                    continue;
                }
                boolean innerValid = true;
                do{
                    try{
                        customCalendar.generateCalendar(LocalDate.now().getYear(),month,appointmentController.filterAppointmentsFromOption(option,month));
                        System.out.println();
                        System.out.println("Which date do you want to pick? ");
                        System.out.println("or Press - to go back");
                        dateOfTreatment = scanner.next();
                        if(dateOfTreatment.charAt(0) == '-'){
                            showOptions();
                            break;
                        }
                        if(Integer.parseInt(dateOfTreatment) < 1 || Integer.parseInt(dateOfTreatment) > 31){
                            innerValid = false;
                            continue;
                        }
                        innerValid = appointmentController.validAppointment(Integer.parseInt(dateOfTreatment),month, appointmentController.optionForTime(option));
                        System.out.println("What is the treatment that you want to book for? ");
                        treatmentTitle = scanner.next();
                        do{
                            String doctor = appointmentController.optionForDoctor(showDoctorsUi());
                            if(!doctor.equals("invalid")){
                                showBookingDialog(appointmentController.bookAppointment(Integer.parseInt(dateOfTreatment),month,option,patientId,treatmentTitle,doctor));
                            }else{
                                System.out.println("Invalid input!");
                                innerValid = false;
                            }
                        }while (!innerValid);
                    }catch(InputMismatchException | NumberFormatException | DateTimeException e){
                        System.out.println("Invalid input!");
                        innerValid = false;
                    }
                }while(!innerValid);
            }catch (InputMismatchException e){
                System.out.println("Invalid input!");
                valid = false;
                scanner.next();
            }
        }while(!valid);
    }

    public void showBookingDialog(boolean isSuccessful){
       if(isSuccessful){
           System.out.println("====================");
           System.out.println("Successfully booked!");
           System.out.println("====================");
       }else{
           System.out.println("Error with booking!");
       }
    }

    public void showUpdateDialog(boolean isSuccessful){
        if(isSuccessful){
            System.out.println("====================");
            System.out.println("Successfully updated!");
            System.out.println("====================");
        }else{
            System.out.println("Error with updating!");
        }
    }
    public void showDeleteDialog(boolean isSuccessful){
        if(isSuccessful){
            System.out.println("====================");
            System.out.println("Successfully deleted!");
            System.out.println("====================");
        }else{
            System.out.println("Error with deleting!");
        }
    }
    public int showDoctorsUi() throws InputMismatchException{
        int input;
        System.out.println("====================");
        ArrayList<String> doctors = new ArrayList<String>(List.of("Dr Mike", "Dr Phil", "Dr Vanessa"));
        for(int i = 0; i < doctors.size(); i++){
            System.out.println("Press ("+ i +") " + "for " + doctors.get(i));
        }
        input = scanner.nextInt();
        return input;
    }

}
