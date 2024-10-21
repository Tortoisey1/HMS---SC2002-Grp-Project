package entities;

import enums.Gender;
import enums.UserType;
import information.LoginInformation;
import information.UserInformation;
import services.LoginService;
import services.LogoutService;

public abstract class User {
    private UserType userType;
    private LoginInformation loginInformation;
    private boolean firstLogin;
    private UserInformation userInformation;

    // initialise a random password on first entry
    // then let them change
    public User(int id, UserType userType) {
        this.id = id;
        this.userType = userType;
        this.password = "password"; // first password is string "password"
        this.firstLogin = true;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public void removeFirstLogin() {
        this.firstLogin = false;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void logOut() {
        // invokes the ui
    }

    public void login() {
        // LoginService loginService = new LoginService();
    }

}
