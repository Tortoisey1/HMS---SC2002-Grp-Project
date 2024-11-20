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

/**
 * This Service AppointmentManagementServicePatient class handles the business
 * logic of the app for Patient Menu and inherits
 * all behaviours and properties from {@link AppointmentManagementService}
 * It allows current patient to schedule, reschedule, cancel schedule
 * {@code currentUser} to be initialized with the Patient logged in with type
 * {@link Patient} and will be used throughout this service
 * {@code doctorList} type {@link HashMap} to map each choice as key to the
 * value of doctor with type {@link entities.Doctor}
 */
public class AppointmentManagementServicePatient extends AppointmentManagementService {

    private final DataManager<Staff, String> staffDataManager;
    private static AppointmentManagementServicePatient appointmentServicePatient;
    private final HashMap<Integer, Staff> doctorList;
    private Patient currentUser;

    /**
     * Constructs an AppointmentManagementServicePatient
     * 
     * @param appointmentDataManager type {@link DataManager} for
     *                               AppointmentDataManager to retrieve appointments
     *                               and initialized to required field in super()
     * @param staffDataManager       type {@link DataManager} for StaffDataManager
     *                               to retrieve all doctors
     * @param currentUser            type {@link Patient} for the current patient
     *                               logged in
     */
    public AppointmentManagementServicePatient(
            DataManager<Appointment, String> appointmentDataManager,
            DataManager<Staff, String> staffDataManager,
            Patient currentUser) {
        super(appointmentDataManager);
        this.staffDataManager = staffDataManager;
        this.doctorList = new HashMap<>();
        this.staffDataManager.getList();
        this.currentUser = currentUser;
    }

    /**
     * Singleton for the AppointmentManagementServicePatient
     * Declared and initialized the Constructor to {@code appointmentServicePatient}
     * Retrieve Singletons of StaffDataManager, AppointmentDataManager
     * Initialized the {@code currentUser} with {@param patient} from
     * {@link menu.PatientMenu}
     * 
     * @return {@link AppointmentManagementServicePatient}
     */
    public static AppointmentManagementServicePatient getInstance(Patient patient) {
        if (appointmentServicePatient == null) {
            return appointmentServicePatient = new AppointmentManagementServicePatient(
                    AppointmentDataManager.getInstance(),
                    StaffDataManager.getInstance(),
                    patient);
        }
        return appointmentServicePatient;
    }

    /**
     * scheduleAppt() will be the main function to run the onboarding for scheduling
     * Allow patient to schedule appointment
     * Used {@link CustomCalendar} to create an actual GUI of the calendars for
     * every month
     * Patient pick the time slot for the appointment first
     * Patient can only pick the dates that are not booked or dates after today
     * based on {@link CustomCalendar}
     * handle {
     * 
     * @exception InputMismatchException} if patient input wrong type of input
     *                                    handle {
     * @exception NumberFormatException}  if patient did not input an integer for it
     *                                    to parse
     */
    public void scheduleAppt() {
        Scanner scanner = Global.getScanner();
        int month, option;
        String time;
        Staff doctorSelected;
        String treatmentTitle, dateOfTreatment;
        CustomCalendar customCalendar = new CustomCalendar();
        ArrayList<Appointment> filteredAppointments;
        while (true) {
            try {
                customCalendar.generateMonths();
                month = scanner.nextInt();
                if (month > 12 || month < 1) {
                    Dialog.showInvalidInput("month");
                    continue;
                }
                customCalendar.generateSlots();
                option = scanner.nextInt();
                if (option > 8 || option < 1) {
                    Dialog.showInvalidInput("time slot");
                    continue;
                }
                doctorSelected = optionForDoctor();
                if (doctorSelected != null) {
                    filteredAppointments = filterAppointmentsFromOption(option, month,
                            doctorSelected.getUserInformation().getID().getId());
                    customCalendar.generateCalendar(LocalDate.now().getYear(), month, filteredAppointments);
                    time = optionForTime(option);
                    dateOfTreatment = optionForDate(month, time, doctorSelected.getUserInformation().getID().getId());
                    if (dateOfTreatment != null) {
                        treatmentTitle = optionForTreatment();
                        if (bookAppointment(
                                Integer.parseInt(dateOfTreatment),
                                month,
                                option,
                                currentUser.getUserInformation().getID().getId(),
                                treatmentTitle,
                                doctorSelected.getUserInformation().getID().getId(),
                                doctorSelected.getUserInformation().getPrivateInformation().getName()

                        )) {
                            Dialog.showSuccessful("booked");
                        }
                    }
                    break;
                }
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println(e.getMessage());
                scanner.nextLine();
            }
        }

    }

    /**
     * bookAppointment() will be a sub function used in {@code scheduleAppt()}
     * 
     * @param dateOfTreatment for the date picked by the patient be used with
     *                        {@link LocalDate}
     * @param month           for the month picked by the patient to be used with
     *                        {@link LocalDate}
     * @param patientId       for the id of the current patient
     * @param treatment       for medical service picked
     * @param doctorId        for the id of the doctor picked
     * @param doctorName      for the name of the doctor picked
     *                        Add this new appointment to the list of appointments
     *                        in {@link AppointmentDataManager}
     * @return {@code true} if add is successful
     */
    public boolean bookAppointment(int dateOfTreatment,
            int month,
            int option,
            String patientId,
            String treatment,
            String doctorId,
            String doctorName) {
        LocalDate date = LocalDate.of(LocalDate.now().getYear(), month, dateOfTreatment);
        PatientID patientID = new PatientID();
        patientID.setId(patientId);
        DoctorID doctorID = new DoctorID();
        doctorID.setId(doctorId);

        return getAppointmentDataManager().add(
                new Appointment(
                        patientID,
                        doctorID,
                        AppointmentStatus.PENDING,
                        new AppointmentOutcomeRecord(new ConsultationNotes(), new ArrayList<Medication>()),
                        optionForTime(option),
                        date.toString(),
                        MedicalService.valueOf(treatment),
                        GenerateIdHelper.generateId("AP"),
                        doctorName

                ));
    }

    /**
     * validAppointment() as a helper function to check if the date picked is booked
     * or already passed
     * 
     * @param date     for the date picked by the patient be used with
     *                 {@link LocalDate}
     * @param month    for the month picked by the patient to be used with
     *                 {@link LocalDate}
     * @param doctorId for the id of the doctor picked
     *                 Filter the list of appointments down to {@param doctorId} to
     *                 see if doctor is free on that slot
     * @return {@code false} if date of the appointment is booked or already passed
     *         else {@code true}
     */
    public boolean validAppointment(int date, int month, String time, String doctorId) {
        LocalDate datePicked = LocalDate.of(LocalDate.now().getYear(), month, date);
        ArrayList<Appointment> filteredList;
        if (LocalDate.now().isAfter(datePicked)) {
            System.out.println("Invalid, the date has passed!");
            return false;
        }
        filteredList = filterAppointmentsFromField(time, month, doctorId);
        for (Appointment temp : filteredList) {
            if (temp.getAppointmentStatus() == AppointmentStatus.CONFIRMED && temp.getDateOfAppointment() == date) {
                System.out.println("The date has been booked");
                return false;
            }
        }
        return true;
    }

    /**
     * cancelAppointment() allows patient to cancel their appointment
     * Get the filtered list of {@link Appointment} for this current patient that
     * have {@link AppointmentStatus} of CONFIRMED
     * Use of {@link HashMap} with the key as the {@link Integer} and value as the
     * {@link Appointment}
     * Each key represent a choice and the value as the appointment
     * Allow user to input the choice based on the key to cancel and the respective
     * appointment will be deleted
     * handle {
     * 
     * @exception InputMismatchException} if patient input wrong type of input
     *                                    handle {
     * @exception NumberFormatException}  if patient did not input an integer for it
     *                                    to parse
     */
    public void cancelAppointment() {
        HashMap<Integer, Appointment> appointmentHashMap = filterPendingConfirmedForPatient(
                currentUser.getUserInformation().getID().getId());
        String choice;
        while (true) {
            try {
                System.out.println("These are the following appointments:");
                displayPendingConfirmedAppointmentsForThisPatient(appointmentHashMap, "delete");
                System.out.println("press (-) to go back");
                choice = Global.getScanner().next();
                if (choice.charAt(0) == '-') {
                    break;
                }
                if (appointmentHashMap.containsKey(Integer.parseInt(choice))) {
                    String appointmentId = appointmentHashMap.get(Integer.parseInt(choice)).getAppointmentID();
                    if (getAppointmentDataManager().delete(appointmentId)) {
                        Dialog.showSuccessful("deleted");
                        break;
                    } else {
                        Dialog.showInvalidInput("Appointment");
                    }
                }
            } catch (InputMismatchException | NumberFormatException e) {
                Dialog.showInvalidInput("Appointment");
                System.out.println(e.getMessage());
                Global.getScanner().nextLine();
            }
        }
    }

    /**
     * updateAppointmentForPatient() allows patient to reschedule their appointment
     * by time or date
     * Get the filtered list of {@link Appointment} for this current patient
     * Use of {@link HashMap} with the key as the {@link Integer} and value as the
     * {@link Appointment}
     * Each key represent a choice and the value as the appointment
     * Allow user to input the choice based on the key to update and the respective
     * appointment will be updated
     * handle {
     * 
     * @exception InputMismatchException} if patient input wrong type of input
     *                                    handle {
     * @exception NumberFormatException}  if patient did not input an integer for it
     *                                    to parse
     */
    public void updateAppointmentForPatient() {
        HashMap<Integer, Appointment> appointmentHashMap = filterPendingConfirmedForPatient(
                currentUser.getUserInformation().getID().getId());
        int appointmentChoice, updateChoice, timeChoice;
        while (true) {
            try {
                System.out.println("These are the following appointments:");
                displayPendingConfirmedAppointmentsForThisPatient(appointmentHashMap, "update");
                System.out.println("Key in the choice");
                appointmentChoice = Global.getScanner().nextInt();
                if (appointmentHashMap.containsKey(appointmentChoice)) {
                    System.out.println("What do you want to update on?");
                    System.out.println("(1) Time");
                    System.out.println("(2) Date");
                    updateChoice = Global.getScanner().nextInt();
                    Appointment appointmentFound = appointmentHashMap.get(appointmentChoice);
                    CustomCalendar customCalendar = new CustomCalendar();
                    if (updateChoice == 1) {
                        customCalendar.generateSlots();
                        timeChoice = Global.getScanner().nextInt();
                        String timeChosen = optionForTime(timeChoice);
                        if (validAppointment(appointmentFound.getDateOfAppointment(),
                                appointmentFound.getMonthOfAppointment(), timeChosen,
                                appointmentFound.getDoctorId().getId())) {
                            if (updateAppointment(appointmentFound, timeChosen, 1)) {
                                Dialog.showSuccessful("updated time");
                                break;
                            }
                        }
                    }
                    if (updateChoice == 2) {
                        String dateOfTreatment;
                        customCalendar.generateCalendar(LocalDate.now().getYear(),
                                appointmentFound.getMonthOfAppointment(),
                                filterAppointmentsFromField(appointmentFound.getTimeOfTreatment(),
                                        appointmentFound.getMonthOfAppointment(),
                                        appointmentFound.getDoctorId().getId()));
                        dateOfTreatment = optionForDate(
                                appointmentFound.getMonthOfAppointment(),
                                appointmentFound.getTimeOfTreatment(),
                                appointmentFound.getDoctorId().getId());
                        if (dateOfTreatment != null) {
                            if (updateAppointment(appointmentFound, dateOfTreatment, 2)) {
                                Dialog.showSuccessful("updated date");
                                break;
                            }
                        }
                    }
                } else {

                }
            } catch (InputMismatchException | NumberFormatException e) {
                Dialog.showInvalidInput("Appointment");
                System.out.println(e.getMessage());
                Global.getScanner().nextLine();
            }
        }
    }

    /**
     * displayPendingConfirmedAppointmentsForThisPatient() as a helper function to
     * display each option of appointment
     * 
     * @param appointmentHashMap for the hash map to display
     * @param keyword            for reusability (e.g. delete, update)
     */
    public void displayPendingConfirmedAppointmentsForThisPatient(HashMap<Integer, Appointment> appointmentHashMap,
            String keyword) {
        // System.out.println(appointmentHashMap);
        appointmentHashMap.forEach((key, value) -> {
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

    /**
     * displayAppointmentsForThisPatient() as a helper function to display all the
     * appointments
     * based on the {@link AppointmentStatus} for the patient
     */
    public void displayAppointmentsForThisPatient() {
        ArrayList<Appointment> confirmedList = filterConfirmedAppointmentsForPatient(
                currentUser.getUserInformation().getID().getId());
        ArrayList<Appointment> pendingList = filterPendingAppointmentsForPatient(
                currentUser.getUserInformation().getID().getId());
        ArrayList<Appointment> cancelledList = filterCancelledAppointmentsForPatient(
                currentUser.getUserInformation().getID().getId());
        System.out.println();
        System.out.println("=== Confirmed appointments ===");
        for (Appointment temp : confirmedList) {
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
        for (Appointment temp : pendingList) {
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
        for (Appointment temp : cancelledList) {
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

    /**
     * displayOutcomeRecordsForThisPatient() as a helper function to display all the
     * past appointments that patient went
     */
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

    /**
     * filterAppointmentsFromField() as a helper function to filter all the
     * appointments based on
     * 
     * @param time     for the time picked by the patient be used
     * @param month    for the month picked by the patient to be used with
     *                 {@link LocalDate}
     * @param doctorId for the id of the doctor picked
     *                 {@code Collections.sort()} to the list to sort the dates with
     *                 {@link Comparable} from {@link Appointment}
     * @return {@link ArrayList} all the appointments booked on the time, month and
     *         doctorId
     */
    public ArrayList<Appointment> filterAppointmentsFromField(String time, int month, String doctorId) {
        ArrayList<Appointment> filteredList = new ArrayList<>();
        ArrayList<Appointment> originalList = getAllAppointments();
        for (Appointment temp : originalList) {
            if (temp.getTimeOfTreatment().equals(time)
                    && temp.getMonthOfAppointment() == month
                    && temp.getDoctorId().getId().equals(doctorId)
                    && temp.getAppointmentStatus() == AppointmentStatus.CONFIRMED) {
                filteredList.add(temp);
            }
        }
        Collections.sort(filteredList);
        return filteredList;
    }

    /**
     * filterAppointmentsFromOption() as a helper function to filter all the
     * appointments based on
     * 
     * @param option     for the time picked by the patient be used
     * @param monthField for the month picked by the patient to be used with
     *                   {@link LocalDate}
     * @param doctorId   for the id of the doctor picked
     *                   {@code Collections.sort()} to the list to sort the dates
     *                   with {@link Comparable} from {@link Appointment}
     * @return {@link ArrayList} all the appointments booked on the time, month and
     *         doctorId
     */
    public ArrayList<Appointment> filterAppointmentsFromOption(int option, int monthField, String doctorId) {
        // deep copy of arraylist
        String time = optionForTime(option);
        ArrayList<Appointment> filteredList = new ArrayList<>();
        ArrayList<Appointment> originalList = getAllAppointments();
        for (Appointment temp : originalList) {
            if (temp.getTimeOfTreatment().equals(time) &&
                    temp.getMonthOfAppointment() == monthField &&
                    temp.getDoctorId().getId().equals(doctorId) &&
                    temp.getAppointmentStatus() == AppointmentStatus.CONFIRMED) {
                filteredList.add(temp);
            }
        }
        Collections.sort(filteredList);
        return filteredList;
    }

    /**
     * optionForTreatment() as a helper function to list out all the Medical Service
     * 
     * @return {@link String} of the {@link MedicalService}
     *         handle {
     * @exception InputMismatchException} if patient input wrong type of input
     *                                    handle {
     * @exception NumberFormatException}  if patient did not input an integer for it
     *                                    to parse
     */
    public String optionForTreatment() {
        int treatmentOption;
        while (true) {
            try {
                System.out.println("What is the treatment that you want to book for? ");
                int count = 1;
                for (MedicalService service : MedicalService.values()) {
                    System.out.println(count + ". for " + service.toString());
                    count++;
                }
                treatmentOption = Global.getScanner().nextInt();
                if (treatmentOption >= 1 && treatmentOption <= 7) {
                    switch (treatmentOption) {
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

    /**
     * optionForDate() as a helper function to list out all the date
     * 
     * @return {@link String} of the {@link MedicalService}
     *         handle {
     * @exception InputMismatchException} if patient input wrong type of input
     *                                    handle {
     * @exception NumberFormatException}  if patient did not input an integer for it
     *                                    to parse
     */
    public String optionForDate(int month, String time, String doctorId) {
        String dateOfTreatment;
        Scanner scanner = Global.getScanner();
        while (true) {
            try {
                System.out.println();
                System.out.println("Which date do you want to pick? ");
                System.out.println("or Press - to go back");
                dateOfTreatment = scanner.next();
                if (dateOfTreatment.charAt(0) == '-') {
                    return null;
                }
                if (Integer.parseInt(dateOfTreatment) < 1 || Integer.parseInt(dateOfTreatment) > 31) {
                    Dialog.showInvalidInput("date");
                    continue;
                }
                if (validAppointment(
                        Integer.parseInt(dateOfTreatment),
                        month,
                        time,
                        doctorId)) {
                    return dateOfTreatment;
                }
            } catch (InputMismatchException | NumberFormatException e) {
                Dialog.showInvalidInput("date");
                System.out.println(e.getMessage());
                scanner.nextLine();
            }
        }
    }

    /**
     * optionForDoctor() as a helper function to list out all the doctors
     * 
     * @return {@link Staff} of the doctor selected
     *         handle {
     * @exception InputMismatchException} if patient input wrong type of input
     *                                    handle {
     * @exception NumberFormatException}  if patient did not input an integer for it
     *                                    to parse
     */
    public Staff optionForDoctor() {
        Scanner scanner = Global.getScanner();
        ArrayList<Staff> staffList = staffDataManager.getList();
        int doctorOption;
        int count = 1;
        for (Staff staff : staffList) {
            if (staff.getUserInformation().getUserType().equals(UserType.DOCTOR)) {
                doctorList.put(count++, staff);
            }
        }
        while (true) {
            try {
                System.out.println("These are the following doctors:");
                doctorList.forEach((key, value) -> {
                    System.out.println("Press (" + key + ") for Dr " +
                            value.getUserInformation().getPrivateInformation().getName() +
                            " ID: " + value.getUserInformation().getID().getId());
                });
                System.out.println("Which doctor do you want?");
                doctorOption = scanner.nextInt();
                if (doctorList.containsKey(doctorOption)) {
                    return doctorList.get(doctorOption);
                } else {
                    Dialog.showInvalidInput("doctor");
                }
            } catch (InputMismatchException | NumberFormatException e) {
                Dialog.showInvalidInput("doctor");
                System.out.println(e.getMessage());
                scanner.nextLine();
            }
        }

    }

    /**
     * optionForTime() as a helper function to list out all the time slot
     * 
     * @return {@link String} of time slot
     */
    public String optionForTime(int option) {
        switch (option) {
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
            default -> {
                return "invalid";
            }
        }
    }
}
