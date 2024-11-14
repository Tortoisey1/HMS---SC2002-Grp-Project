package information;

import enums.UserType;
import information.id.UserID;

public class UserInformation {
    private UserType userType;
    private UserID ID;
    private String password;
    private PrivateInformation privateInformation;
    private boolean firstLogin = true;


    public UserInformation(){}

    public UserInformation(UserType userType,
                           UserID iD,
                           String password,
                           PrivateInformation privateInformation
                           ) {
        this.userType = userType;
        this.ID = iD;
        this.password = password;
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

    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }
    public boolean isFirstLogin() {
        return firstLogin;
    }
    public PrivateInformation getPrivateInformation() {
        return privateInformation;
    }

    public void setPrivateInformation(PrivateInformation privateInformation) {
        this.privateInformation = privateInformation;
    }

    @Override
    public String toString() {
        return "UserInformation{" +
                "userType=" + userType +
                ", ID=" + ID +
                ", password='" + password + '\'' +
                ", privateInformation=" + privateInformation +
                ", firstLogin=" + firstLogin +
                '}';
    }
}
