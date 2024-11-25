package menu;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import enums.AppointmentStatus;
import information.Appointment;



/**
 * CustomCalendar class provides the GUI of a Calendar based on the month and year for the user
 */
public class CustomCalendar {

    /**
     * Constructs a CustomCalendar
     */
    public CustomCalendar(){}

    /**
     * generateCalendar() to create the GUI of the Calendar for the user to view
     * @param year for the year of the Calendar to present
     * @param month for the month of the Calendar to present
     * @param filteredAppointments a list of {@link Appointment} that are not meant for booking
     * Uses {@link LocalDate} to calculate and print out the correct Calendar
     * {@link Appointment} in the {@param filteredAppointments} will be marked with one of the legends
     */
    public void generateCalendar(int year, int month, ArrayList<Appointment> filteredAppointments){
        YearMonth ym = YearMonth.of(year,month);
        int increment = 1;
        legends();
        System.out.println(ym.getMonth() + "," + ym.getYear());
        System.out.println("SUN MON TUE WED THU FRI SAT");
        DayOfWeek firstDayOfMonth = LocalDate.of(year,ym.getMonth(),1).getDayOfWeek();
        int firstDayInt = firstDayOfMonth.getValue();
        int totalDays = LocalDate.of(year,ym.getMonth(),1).lengthOfMonth();
        if(firstDayInt != 7){
            for(int i = 0; i < firstDayInt; i++){
                System.out.printf("%-4s","");
                increment++;
            }
        }
        int count = 0;
        for(int j = 1; j <= totalDays; j++, increment++){
            System.out.print("|");
            if(LocalDate.now().isAfter(LocalDate.of(year,month,j))){
                System.out.printf("%-2d", j);
                System.out.print("X");
            }else{
                if (count < filteredAppointments.size() && filteredAppointments.get(count).getDateOfAppointment() == j) {
                    System.out.printf("%-2d", j);
                    if (filteredAppointments.get(count).getAppointmentStatus() == AppointmentStatus.CONFIRMED) {
                        System.out.print("T");
                    }
                    count++;
                } else {
                    System.out.printf("%-3d", j);
                }
            }

            if(increment % 7 == 0){
                System.out.println();
            }

            if(j == totalDays){
                System.out.print("|");
            }
        }
    }

    /**
     * legends to show the icons in the calendar
     */
        public void legends(){
            System.out.println("Legends:");
            System.out.println("X: PASSED THE DATE");
            System.out.println("T: TAKEN");
        }

    /**
     * Display options for months
     */
    public void generateMonths(){
        String[] options = {"(1)", "(2)", "(3)", "(4)", "(5)",
                "(6)", "(7)", "(8)", "(9)", "(10)", "(11)", "(12)"};
        String[] months = {
                "Jan","Feb","Mar","Apr","May","Jun","Jul",
                "Aug","Sep","Oct","Nov","Dec"
        };
        System.out.println("Which month are you interested in?");
        for(int i = 0; i < options.length; i++) {
            System.out.println("Press " + options[i] + " for " + months[i]);
        }
    }

    /**
     * Display options for time
     */
    public void generateSlots(){
        String[] slots = {"0900 - 1000", "1000 - 1100", "1100 - 1200",
                "1200 - 1300", "1300 - 1400", "1400 - 1500", "1500 - 1600", "1600 - 1700"};
        String[] options = {"(1)", "(2)", "(3)", "(4)", "(5)", "(6)", "(7)", "(8)"};
        System.out.println("These are the time slots for the appointment");
        for(int i = 0; i < slots.length; i++) {
            System.out.println("Press " + options[i] + " for " + slots[i]);
        }
    }

}
