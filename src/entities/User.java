package entities;

import information.UserInformation;

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

    @Override
    public String toString() {
        return "User{" +
                "userInformation=" + userInformation.toString() +
                '}';
    }
}
