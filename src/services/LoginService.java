package services;

import app.Global;
import entities.User;
import enums.UserType;
import exceptions.InvalidTypeException;
import exceptions.InvalidUserException;
import exceptions.NotInSystem;
import information.id.UserID;
import management.PatientDataManager;
import management.StaffDataManager;

public class LoginService {
    private final int maxPasswordTries = 3;

    // users will change their password upon initial login
    public static User login(UserType selectedUserType, String id, String passwordAttempt) {
        // check if its user first attempt
        String className = "information.id." + selectedUserType.name().substring(0, 1)
                + selectedUserType.name().substring(1).toLowerCase()
                + "ID";

        System.out.println(className);

        try {
            // Dynamically load the class using reflection
            Class<?> clazz = Class.forName(className);
            UserID userID = (UserID) clazz.getDeclaredConstructor().newInstance();

            if (userID.isValidUserId(id)) {
                System.out.println("Valid " + selectedUserType + " ID.");

                if (selectedUserType.equals(UserType.PATIENT)) {
                    // check whether the user is in the system
                    if (PatientDataManager.checkPatientExist(id)) {
                        // then check the password for that user
                        User user = PatientDataManager.getInstance().retrieve(id);
                        if (user.getUserInformation().getPassword().equals(passwordAttempt)) {
                            return user;
                        } else {
                            throw new InvalidUserException("Wrong password");
                        }

                    }

                }

                else {
                    // check whether the user is in the system
                    if (StaffDataManager.checkStaffExist(id)) {
                        // then check the password for that user
                        User user = StaffDataManager.getInstance().retrieve(id);
                        if (user.getUserInformation().getPassword().equals(passwordAttempt)) {
                            return user;
                        } else {
                            throw new InvalidUserException("Wrong password");
                        }

                    }

                    throw new NotInSystem("User does not exist in the hospital dont play");
                }
            } else {
                throw new InvalidTypeException("ID is not feasible for " + selectedUserType);

            }
        } catch (ClassNotFoundException e) {
            System.out.println("User ID class not found for " + className);
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        return null;

    }

    public static void changePassword(User user) {
        System.out.println("What's the password you want to change to?");
        String newPassword = Global.getScanner().nextLine();
        user.getUserInformation().setPassword(newPassword);
    }
}