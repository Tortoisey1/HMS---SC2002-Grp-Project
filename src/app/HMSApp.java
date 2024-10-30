import management.PatientDataManager;
import menu.AppMenu;

public class HMSApp {
    public static void main(String[] args) {
        // load the data files first
        PatientDataManager patientData = new PatientDataManager();
        
        // /first menu
        AppMenu menu = new AppMenu();
        menu.printMenu();

        try {
            int choice = Integer.valueOf(Global.getScanner().nextLine());

            // if it wants to login will go into the login interface
            AppLogic mainLogic = new AppLogic();
            mainLogic.mainMenu(choice);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

        // output back into the csv

        try {

            patientData.writeAll();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

    }
}