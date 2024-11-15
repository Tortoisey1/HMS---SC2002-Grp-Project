package entities;

import information.UserInformation;

/**
 * Administrator class implements the basic behaviours from Staff
 */
public class Administrator extends Staff {
    /**
     * Constructs an Administrator and initialize the field needed with super
     * from the class User
     * @param userInformation with the basic information respective to
     * each user
     */
    public Administrator(UserInformation userInformation) {
        super(userInformation);
    }
}
