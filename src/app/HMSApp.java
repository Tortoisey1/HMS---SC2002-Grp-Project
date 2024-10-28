import menu.AppMenu;

public class HMSApp {
    public static void main(String[] args) {
        // load the data files first

        // /first menu
        AppMenu menu = new AppMenu();
        menu.printMenu();

        AppLogic mainLogic = new AppLogic();
        int choice = Integer.valueOf(AppLogic.getScanner().nextLine());

        try {
            mainLogic.mainMenu(choice);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

    }
}