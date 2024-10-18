package entities;

import enums.UserType;

public class User {
    private final int id; // shouldnt be allowed to change
    private UserType userType;
    private String password; //will change upon first login
    private boolean firstLogin;

    // initialise a random password on first entry
    // then let them change
    public User(int id, UserType userType) {
        this.id = id;
        this.userType = userType;
        this.password = "password";
        this.firstLogin  = true;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
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

}
