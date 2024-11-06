package app;

import exceptions.InvalidChoiceException;
import menu.LoginMenu;

public class AppLogic {
    public void mainMenu(int choice) throws InvalidChoiceException {
        switch (choice) {
            case 1:
                LoginMenu loginMenu = new LoginMenu();
                loginMenu.printMenu();
                break;

            case 0:
                System.out.println("Bye!");
                System.exit(0);
                break;
            default:
                throw new InvalidChoiceException("This choice dont exist dont play");
        }
    }
}
