package services;

import entities.User;
import exceptions.LoginAttemptsExceededException;

public class LoginService {
    private final int maxPasswordTries = 3;

    // users will change their password upon initial login
    public void login(int id, String passwordAttempt) {
        // check if its user first attempt
        if (user.isFirstLogin()) {
            changePassword(user);
            // then do whatever tf u want do
        } else { // users should only have the standard 3 attempts to try otherwise they are
                 // locked out for a specific time
            authenticate(id,passwordAttempt);
        }
    }

    public static void changePassword(User user) {

    }

    private boolean authenticate(int id, String passwordAttempt) throws LoginAttemptsExceededException {
        int tries = 0;
        while(tries<this.maxPasswordTries){
            boolean success = checkCredentials(id,passwordAttempt);

            if (success) {
                //then handle some shit


                return true;

            }
            else{
                tries++;
            }
        }

        throw new LoginAttemptsExceededException("Maximum login attempts exceeded. Please try again later.");
        //should set a clock to time
    }

    public boolean checkCredentials(id,passwordAttempt){
        
    }
}
