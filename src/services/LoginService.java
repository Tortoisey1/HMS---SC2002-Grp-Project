package services;

import java.util.Scanner;

import entities.User;
import exceptions.InvalidUserException;
import exceptions.LoginAttemptsExceededException;

public class LoginService {
    private final int maxPasswordTries = 3;

    // users will change their password upon initial login
    public void login(int id, String passwordAttempt) {
        // check if its user first attempt

        User user = authenticate(id, passwordAttempt);
        if (user.isFirstLogin()) {
            // set the user first login to false
            user.removeFirstLogin();

            changePassword(user);
            // then do whatever tf u want do
        }
    }

    public static void changePassword(User user) {
        System.out.println("What's the password you want to change to?");
        Scanner scanner = new Scanner(System.in);
        String newPassword = scanner.nextLine();
        user.setPassword(null);
    }

    private User authenticate(int id, String passwordAttempt) throws LoginAttemptsExceededException {
        int tries = 0;
        while(tries<this.maxPasswordTries){
            User user = checkCredentials(id,passwordAttempt);

            if (success) {
                //then handle some shit


                return user;

            }
            else{
                tries++;
            }
        }

        throw new LoginAttemptsExceededException("Maximum login attempts exceeded. Please try again later.");
        //should set a clock to time
    }

    public User checkCredentials(int id,String passwordAttempt){


        throw new InvalidUserException("Invalid User. Please try again.")
    }
}
