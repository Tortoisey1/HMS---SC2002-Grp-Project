package entities;

import information.UserInformation;


/**
 * Pharmacist class implements the basic behaviours from Staff
 */
public class Pharmacist extends Staff {
    /**
     * Constructs a Pharmacist and initialize the field needed with super
     * from the class User
     * @param userInformation with the basic information respective to
     * each user
     */
    public Pharmacist(UserInformation userInformation) {
        super(userInformation);
    }
}
