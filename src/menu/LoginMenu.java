package menu;

import app.AppLogic;
import enums.UserType;
import exceptions.InvalidAmountException;
import exceptions.InvalidTypeException;
import exceptions.NotInSystem;
import information.id.UserID;
import management.UserManagement;
import services.LoginService;

public class LoginMenu implements Menu {

    @Override
    public void printMenu() {
        int choice;

        System.out.println("What kind of user are you? ");
        UserType.printUserTypes();

        try {
            choice = Integer.valueOf(AppLogic.getScanner().nextLine());

            if (choice <= 0 || choice > UserType.values().length) {
                throw new InvalidAmountException("No such user type choice; don't play.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number corresponding to your user type.");
            System.exit(0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        System.out.println("What is your user ID? ");
        String selectedUserID = AppLogic.getScanner().nextLine();

        System.out.println("What is your password?");
        String password = AppLogic.getScanner().nextLine();

        loginLogic(choice, selectedUserID, password);

    }

    private void loginLogic(int userTypeChoice, String id, String passwordAttempt) {

        UserType selectedUserType = UserType.values()[userTypeChoice - 1];

        try {
            if (LoginService.login(selectedUserType, id, passwordAttempt) != null) {
                // based on the different user type will show different menu which comes with
                // the different functions
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

    }

}
