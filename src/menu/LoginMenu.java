package menu;

import app.Global;
import entities.User;
import enums.UserType;
import exceptions.InvalidAmountException;
import exceptions.InvalidPasswordException;
import services.authentication.LoginService;

import java.util.Scanner;

/**
 * Represents the login menu where users can select their type, enter
 * credentials, and log in.
 * This class handles the login process, including user type selection, user
 * authentication,
 * and the appropriate menu for different user types.
 * 
 * <p>
 * The {@code LoginMenu} class extends {@code Menu} and implements the logic for
 * the login process,
 * including:
 * <ul>
 * <li>Prompting the user to select a user type.</li>
 * <li>Collecting the user ID and password for login.</li>
 * <li>Authenticating the user via the {@link LoginService}.</li>
 * <li>Redirecting to the appropriate menu based on the user type.</li>
 * <li>Handling special cases such as first-time login and password change.</li>
 * </ul>
 * 
 * @see Menu
 * @see LoginService
 * @see UserType
 * @see User
 */
public class LoginMenu extends Menu {
    /**
     * Prints the login menu for a given user.
     * Since this method doesn't take in a user object, it's overridden to remain
     * empty.
     * 
     * @param user the user whose specific menu would be printed (not used in this
     *             method).
     */
    @Override
    public void printMenu(User user) {
    }

    /**
     * Prints the general login menu and prompts the user for their user type, user
     * ID, and password.
     * 
     * <p>
     * After gathering user inputs, it authenticates the user and redirects them to
     * the appropriate menu based on their user type.
     * It also validates the user type selection and handles invalid input or system
     * errors.
     */
    @Override
    public void printMenu() {
        int choice;

        System.out.println("What kind of user are you? ");
        UserType.printUserTypes();

        try {
            choice = Integer.parseInt(Global.getScanner().nextLine());
            System.out.println("What is your user ID? ");
            String selectedUserID = Global.getScanner().nextLine();
            System.out.println("What is your password?");
            String password = Global.getScanner().nextLine();

            loginLogic(choice, selectedUserID, password);

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
    }

    /**
     * Handles the login logic for a selected user type and user credentials.
     * 
     * <p>
     * This method calls the {@link LoginService} to authenticate the user based on
     * their ID, password,
     * and selected user type. After successful authentication, it checks if it's
     * the user's first login
     * and prompts them to change their password if necessary. It then redirects the
     * user to the appropriate menu
     * based on their user type using reflection to dynamically load the
     * corresponding menu.
     * 
     * @param userTypeChoice  the index of the user type chosen by the user.
     * @param id              the user ID entered by the user.
     * @param passwordAttempt the password entered by the user.
     */
    private void loginLogic(int userTypeChoice, String id, String passwordAttempt) {

        UserType selectedUserType = UserType.values()[userTypeChoice - 1];
        try {
            User currentUser = LoginService.getInstance().login(selectedUserType, id, passwordAttempt);
            if (currentUser != null) {
                // based on the different user type will show different menu which comes with
                // the different functions
                if (LoginService.getInstance().checkFirstLogin(currentUser)) {
                    changePasswordPrompt(currentUser);

                }
                String userType = currentUser.getUserInformation().getUserType().name();

                String className = userType.substring(0, 1).toUpperCase() + userType.substring(1).toLowerCase()
                        + "Menu";
                try {
                    // Use reflection to find the class and create an instance
                    Class<?> menuClass = Class.forName("menu." + className);
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

    /**
     * Prompts the user to change their password during the first login.
     * This method repeatedly prompts the user for a new password until a valid
     * password is provided.
     * 
     * @param account the user account whose password is to be changed.
     */
    public void changePasswordPrompt(User account) {
        Scanner scanner = Global.getScanner();
        String newPasswordField;

        while (true) {
            try {
                System.out.println("Enter new password: ");
                newPasswordField = scanner.nextLine();
                if (LoginService.getInstance().changePassword(account, newPasswordField)) {
                    System.out.println("Password has successfully been changed!");
                    break;
                }
            } catch (InvalidPasswordException e) {
                System.out.println(e);
            }
        }
    }

}
