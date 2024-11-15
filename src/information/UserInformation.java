package information;

import enums.UserType;
import information.id.UserID;

/**
 * This class holds the fields for userType, ID, password and privateInformation of user
 * provides basic getter/setters
 * Applicable to all users in the app
 * requires all user to change password if firstLogin == true
 */
public class UserInformation {
    private UserType userType;
    private UserID ID;
    private String password;
    private PrivateInformation privateInformation;
    private boolean firstLogin = true;

    /**
     * Constructs using default Constructor with no fields are needed
     */
    public UserInformation(){}

    /**
     * Constructs an PrivateInformation
     * @param userType for {@link UserType} of this user
     * @param iD for {@link UserID} of this ID
     * @param password for password to login for this user
     * @param privateInformation type {@link PrivateInformation}
     */
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

    /**
     * @return type of the user
     */
    public UserType getUserType() {
        return userType;
    }

    /**
     * Sets {@code userType} with new {@param userType}
     */
    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    /**
     * @return the type of the ID for the user
     */
    public UserID getID() {
        return ID;
    }

    /**
     * Sets {@code iD} with new {@param iD}
     */
    public void setID(UserID iD) {
        ID = iD;
    }

    /**
     * @return the password to login for this user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets {@code password} with new {@param password}
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets {@code firstLogin} with new {@param firstLogin}
     */
    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    /**
     * @return true and user have to change password else false
     */
    public boolean isFirstLogin() {
        return firstLogin;
    }

    /**
     * @return privateInformation of user
     */
    public PrivateInformation getPrivateInformation() {
        return privateInformation;
    }

    /**
     * Sets {@code privateInformation} with new {@param privateInformation}
     */
    public void setPrivateInformation(PrivateInformation privateInformation) {
        this.privateInformation = privateInformation;
    }

    /**
     * @return A string representation of the {@code userType}
     * {@code ID}, {@code password} , {@code privateInformation},
     * {@code firstLogin}
     */
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
