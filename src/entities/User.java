package entities;

import information.UserInformation;



/**
 * This abstract class represents a user
 * It defines the basic properties and behaviours that
 * all users in the app should have
 */
public abstract class User {
    private UserInformation userInformation;

    /**
     * @param userInformation is the basic info of all users should have
     * Constructor here helps to define needed field: {@param userInformation}
     * in subclasses
     */
    public User(UserInformation userInformation) {
        this.userInformation = userInformation;
    }

    /**
     * Retrieves the userInformation associated with the object.
     * @return userInformation
     */
    public UserInformation getUserInformation() {
        return userInformation;
    }

    /**
     * Sets the userInformation object to the
     * new object from {@param userInformation }
     */
    public void setUserInformation(UserInformation userInformation) {
        this.userInformation = userInformation;
    }

    /**
     * @return A string representation of the userInformation.
     */
    @Override
    public String toString() {
        return "User{" +
                "userInformation=" + userInformation.toString() +
                '}';
    }
}
