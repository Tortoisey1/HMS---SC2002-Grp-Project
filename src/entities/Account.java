package entities;
import merged.Role;

public class Account {
    private String password = "password";
    private String accountId;
    private Role role; //will change this move into the user
    private boolean firstLogin = true;
    private User user;

    Account(){}

    Account(String accountId,boolean firstLogin, String password, Role role){
        this.accountId = accountId;
        this.password = password;
        this.firstLogin = firstLogin;
        this.role = role;
    }

    public Role checkRole(){
        return switch (getAccount_Id().toUpperCase().charAt(0)) {
            case 'D' -> Role.DOCTOR;
            case 'P' -> Role.PHARMACIST;
            case 'A' -> Role.ADMINISTRATOR;
            case 'U' -> Role.PATIENT;
            default -> Role.PATIENT;
        };
    }

    public boolean validatePassword(String password){
        return password.equals(this.password);
    }

    public String getAccount_Id() {
        return accountId;
    }

    public Role getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public void setAccount_Id(String account_Id) {
        accountId = account_Id;
    }

    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
