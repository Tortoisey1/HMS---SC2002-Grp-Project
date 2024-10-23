package services;

import java.util.Scanner;

import entities.User;
import exceptions.InvalidUserException;
import exceptions.LoginAttemptsExceededException;
import information.id.UserID;

public class LoginService implements LoginServiceInterface {
    private final int maxPasswordTries = 3;

    // users will change their password upon initial login
    public boolean login(int id, String passwordAttempt) {
        // check if its user first attempt

        User user = authenticate(id, passwordAttempt);
        if (user.isFirstLogin()) {
            // set the user first login to false
            user.removeFirstLogin();

            changePassword(user);
            // then do whatever tf u want do
        }


        return true;
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

    @Override
    public boolean validateLogin(UserID userId, String inputPassword) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateLogin'");
    }

    @Override
    public void changePassword(UserID userId, String newPassword) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'changePassword'");
    }

    @Override
    public void recordLoginAttempt(UserID userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'recordLoginAttempt'");
    }

    @Override
    public int getLoginAttempts(UserID userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLoginAttempts'");
    }

    @Override
    public void resetLoginAttempts(String userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resetLoginAttempts'");
    }

    @Override
    public void addUser(UserID userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addUser'");
    }
}
