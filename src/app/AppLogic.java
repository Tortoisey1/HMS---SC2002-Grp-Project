import java.util.Scanner;

import exceptions.InvalidAmountException;
import exceptions.InvalidChoiceException;
import menu.LoginMenu;

public class AppLogic {
    private static Scanner scanner = new Scanner(System.in);

    public static Scanner getScanner() {
        return scanner;
    }

    public void mainMenu(int choice) throws InvalidChoiceException {
        switch (choice) {
            case 1:
                LoginMenu loginMenu = new LoginMenu();
                loginMenu.printMenu();

                int loginChoice = Integer.valueOf(AppLogic.getScanner().nextLine());

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
