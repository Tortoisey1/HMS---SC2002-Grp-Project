package services.authentication;

import entities.*;
import enums.UserType;
import exceptions.*;
import management.PatientDataManager;
import management.StaffDataManager;
import menu.dialogs.Dialog;
import services.patient.AppointmentManagementServicePatient;
import validators.PasswordValidator;

/**
 * The LoginService that implements PasswordInterface, adhering to the Interface Segregation Principle.
 * Responsible for the business logics to log in the different types of Users and act as a controller to verify the details
 * of the account with the data manager.
 */
public class LoginService implements PasswordInterface {
    private final int maxPasswordTries = 3;
    private static LoginService loginService;

    /**
     * Singleton for the LoginService
     * Declared and initialized the Constructor to {@code loginService}
     * @return {@link LoginService}
     */
    public static LoginService getInstance(){
        if(loginService == null){
            loginService = new LoginService();
        }
        return loginService;
    }

    /**
     * Check the {@link UserType} of the account and retrieve the instance of the respective Data Manager accordingly.
     * Validate with Data Manager with the input: ID and password if it exists in the CSV and are identical.
     * handle {@exception NotInSystem} if account is not found
     * @return {@link User} if account found else NULL
     */
    public User login(UserType selectedUserType, String id, String passwordAttempt) {
        User accountFound = null;
        try {
                switch(selectedUserType){
                    case PATIENT -> accountFound = PatientDataManager.getInstance().retrieve(id);
                    case DOCTOR, PHARMACIST, ADMINISTRATOR ->  accountFound = StaffDataManager.getInstance().retrieve(id);
                }
                if(accountFound != null){
                    accountFound = validate(accountFound,id,passwordAttempt);
                    if(accountFound != null){
                        return accountFound;
                    }else{
                        throw new NotInSystem("No such account");
                    }
                }else{
                    throw new NotInSystem("No such account");
                }
        } catch (NotInSystem e) {
            System.exit(0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        return null;

    }

    /**
     * Validate with Data Manager with the input: ID and password if it exists in the CSV and are identical.
     * @return {@link User} if account found else NULL
     */
    @Override
    public User validate(User accountFound,String accountId, String password)  {
        if(accountFound.getUserInformation().getPassword().equals(password) &&
        accountFound.getUserInformation().getID().getId().equals(accountId)
        ){
            return accountFound;
        }else{
            Dialog.showInvalidInput("password");
        }
        return null;
    }

    /**
     * Check if the user isFirstLogin == true,
     * If yes then user will be prompted to change password and update according to their respective
     * DataManager
     * @return {@code true} if password have been changed
     */
    @Override
    public boolean changePassword(User account, String newPassword) throws InvalidPasswordException {
        if(PasswordValidator.validateNewPassword(newPassword)){
            account.getUserInformation().setPassword(newPassword);
            account.getUserInformation().setFirstLogin(false);
            if(account instanceof Patient){
                PatientDataManager.getInstance().update((Patient) account);
            }

            if(account instanceof Doctor){
                System.out.println(account.getUserInformation().getPassword());
                StaffDataManager.getInstance().update((Doctor) account);
            }

            if(account instanceof Pharmacist){
                StaffDataManager.getInstance().update((Pharmacist) account);
            }
            return true;
        } else {
            throw new InvalidPasswordException("Not following our password format!");
        }
    }

    /**
     * Getter method to get the field: firstLogin of the account
     * @return {@code true} if firstLogin == true else {@code false}
     */
    @Override
    public boolean checkFirstLogin(User account) {
        return account.getUserInformation().isFirstLogin();
    }

}