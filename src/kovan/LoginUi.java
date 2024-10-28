import java.util.Scanner;

public class LoginUi {
    private final PasswordInterface passwordController;

    LoginUi(PasswordInterface passwordController){
        this.passwordController = passwordController;
    }

    public Account login(){
        Scanner scanner = Global.scanner();
        String IdField, passwordField;
        Account account;
        do {
            System.out.println("Key in Account Id: ");
            IdField = scanner.nextLine();
            System.out.println("Key in Password: ");
            passwordField = scanner.nextLine();
            account = passwordController.validate(IdField,passwordField);
            if(account == null){
                System.out.println("No such account");
            }
        }while(account == null);
        if(passwordController.checkFirstLogin(account)){
            changePasswordPrompt(account);
        }
        MainMenu.isAuthenticated = true;
        return account;

    }

    public void changePasswordPrompt(Account account){
        Scanner scanner = Global.scanner();
        boolean isSuccessful = false;
        String newPasswordField;
        do{
            System.out.println("Enter new password: ");
            newPasswordField = scanner.nextLine();
            isSuccessful = passwordController.changePassword(account,newPasswordField);
            if(isSuccessful){
                System.out.println("Password has successfully been changed!");
            }else{
                System.out.println("Password did not meet the requirement!");
            }
        }while(!isSuccessful);
    }
}
