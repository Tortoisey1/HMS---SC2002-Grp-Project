package menu;

import app.Global;
import entities.User;
import enums.UserType;
import exceptions.InvalidAmountException;
import services.LoginService;

public class LoginMenu {
    public void printMenu() {
        int choice = 0;

        System.out.println("What kind of user are you? ");
        UserType.printUserTypes();

        try {
            choice = Integer.valueOf(Global.getScanner().nextLine());

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
        String selectedUserID = Global.getScanner().nextLine();

        System.out.println("What is your password?");
        String password = Global.getScanner().nextLine();

        loginLogic(choice, selectedUserID, password);

    }

    private void loginLogic(int userTypeChoice, String id, String passwordAttempt) {
        
        UserType selectedUserType = UserType.values()[userTypeChoice - 1];

        try {
            User currentUser = LoginService.login(selectedUserType, id, passwordAttempt);
            if (currentUser != null) {
                // based on the different user type will show different menu which comes with
                // the different functions
                String userType = currentUser.getUserInformation().getUserType().name();
                String className = userType.substring(0, 1).toUpperCase() + userType.substring(1).toLowerCase()
                        + "Menu";

                try {
                    // Use reflection to find the class and create an instance
                    Class<?> menuClass = Class.forName(className);
                    Menu userMenu = (Menu) menuClass.getDeclaredConstructor().newInstance();

                    // print the menu for the specific class

                    userMenu.printMenu(currentUser);
                } catch (ClassNotFoundException e) {
                    System.out.println("No menu found for user type: " + userType);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

    }

}
