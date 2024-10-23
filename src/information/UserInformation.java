package information;

import enums.UserType;
import information.id.UserID;

public abstract class UserInformation {
    private UserType userType;
    private UserID ID;
    private String password;
    private PrivateInformation privateInformation;
    
    public UserInformation(UserType userType, UserID iD, String password, PrivateInformation privateInformation) {
        this.userType = userType;
        ID = iD;
        this.password = "password"; //default password is password
        this.privateInformation = privateInformation;
    }
    

}
