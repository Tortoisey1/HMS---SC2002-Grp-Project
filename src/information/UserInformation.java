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
        this.password = "password"; // default password is password
        this.privateInformation = privateInformation;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public UserID getID() {
        return ID;
    }

    public void setID(UserID iD) {
        ID = iD;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PrivateInformation getPrivateInformation() {
        return privateInformation;
    }

    public void setPrivateInformation(PrivateInformation privateInformation) {
        this.privateInformation = privateInformation;
    }

}
