package entities;

import enums.UserType;
import services.LoginService;
import services.LogoutService;

public class User {
    private final int id; // shouldnt be allowed to change
    private UserType userType;
    private String password; // will change upon first login
    private boolean firstLogin;

    // initialise a random password on first entry
    // then let them change
    public User(int id, UserType userType) {
        this.id = id;
        this.userType = userType;
        this.password = "password";
        this.firstLogin = true;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public void removeFirstLogin() {
        this.firstLogin = false;
    }

    public int getId() {
        return id;
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
        //invokes the ui
    }

    public void login() {
        // LoginService loginService = new LoginService();
    }

}
