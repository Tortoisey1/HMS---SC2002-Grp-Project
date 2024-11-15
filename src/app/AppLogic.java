package app;
import exceptions.InvalidChoiceException;
import menu.LoginMenu;


/**
 * Manages the login process
 * This class handles the choice given by user to proceed
 */
public class AppLogic {
    /**
     * Create a new object instance of LoginMenu or Exit
     * @param choice given to check for it with switch case
     * @throws InvalidChoiceException here if choice is not 0 or 1
     **/
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
