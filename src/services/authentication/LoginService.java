package services.authentication;

import entities.*;
import enums.UserType;
import exceptions.*;
import management.PatientDataManager;
import management.StaffDataManager;
import menu.dialogs.Dialog;
import validators.PasswordValidator;

public class LoginService implements PasswordInterface {
    private final int maxPasswordTries = 3;
    private static LoginService loginService;

    public static LoginService getInstance(){
        if(loginService == null){
            loginService = new LoginService();
        }
        return loginService;
    }

    // users will change their password upon initial login
    public User login(UserType selectedUserType, String id, String passwordAttempt) {
        // check if its user first attempt
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
                        throw new ClassNotFoundException("No such account");
                    }
                }else{
                    throw new ClassNotFoundException("No such account");
                }
        } catch (ClassNotFoundException e) {
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        return null;

    }


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
    @Override
    public boolean checkFirstLogin(User account) {
        return account.getUserInformation().isFirstLogin();
    }

}