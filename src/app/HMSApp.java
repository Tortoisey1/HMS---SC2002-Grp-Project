import menu.AppMenu;

public class HMSApp {
    public static void main(String[] args) {
        // load the data files first

        // /first menu
        AppMenu menu = new AppMenu();
        menu.printMenu();

        try {
            int choice = Integer.valueOf(AppLogic.getScanner().nextLine());

            //if it wants to login will go into the login interface
            AppLogic mainLogic = new AppLogic();
            mainLogic.mainMenu(choice);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

    }
}