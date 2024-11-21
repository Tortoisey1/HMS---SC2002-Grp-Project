package menu;

import entities.User;

/**
 * Represents the first menu for the application. This menu provides the user with options to either log in 
 * to the system or exit the application.
 */
public class AppMenu extends Menu {

    /**
     * Displays the options for logging into the system or exiting the application.
     * Prompts the user with a choice to either log in or exit.
     */
    public void printMenu() {
        System.out.println("Would you like to login to the system?");
        System.out.println("Choice 1: Login");
        System.out.println("Choice 0: Exit");
    }

    /**
     * This method is overridden but does not provide any functionality. It is not used in the context of this menu.
     * 
     * @param user The current logged-in user, expected to be a `User` object, but the method does not use this parameter.
     */
    @Override
    public void printMenu(User user) {}
}
