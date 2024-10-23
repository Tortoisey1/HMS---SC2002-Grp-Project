package entities;

import enums.Gender;
import enums.UserType;
import information.UserInformation;
import services.LoginService;
import services.LogoutService;

public abstract class User {
    private UserInformation userInformation;

    // initialise a random password on first entry
    // then let them change

    public User(UserInformation userInformation) {
        this.userInformation = userInformation;
    }

    public UserInformation getUserInformation() {
        return userInformation;
    }

    public void setUserInformation(UserInformation userInformation) {
        this.userInformation = userInformation;
    }

}
