package entities;

import information.UserInformation;

/**
 * Doctor class implements the basic behaviours from Staff
 */
public class Doctor extends Staff {
    /**
     * Constructs a Doctor and initialize the field needed with super
     * from the class User
     * @param userInformation with the basic information respective to
     * each user
     */
    public Doctor(UserInformation userInformation) {
        super(userInformation);
    }

}
