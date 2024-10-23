package information;

import information.id.UserID;

public class LoginInformation {
    private UserID UserId; // dk whether to keep anot since i can just reference from userinformation
    private String password;

    public LoginInformation(UserID userID) {
        this.UserId = clone(userID);
        this.password = "password";
    }

}
