package menu;

import entities.User;

public class AppMenu extends Menu {
    public void printMenu() {
        System.out.println("Would you like to login to the system?");
        System.out.println("Choice 1: Login");
        System.out.println("Choice 0: Exit");
    }

    @Override
    public void printMenu(User user) {}
}
