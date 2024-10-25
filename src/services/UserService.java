import java.util.HashMap;
import java.util.Map;

import entities.User;
import information.id.UserID;

public abstract class UserService {
    protected Map<String, User> usersDatabase; // Assume this holds user data

    public UserService() {
        this.usersDatabase = new HashMap<>();
        // Populate with default users if necessary
    }

    // Method for user login
    public boolean login(UserID hospitalId, String password) {
        User user = usersDatabase.get(hospitalId);
        if (user != null && user.validateCredentials(password)) {
            return true; // Successful login
        }
        return false; // Invalid credentials
    }

    // Method to change user password
    public boolean changePassword(UserID hospitalId, String newPassword) {
        User user = usersDatabase.get(hospitalId);
        if (user != null) {
            user.changePassword(newPassword);
            return true; // Password changed successfully
        }
        return false; // User not found
    }

    public abstract boolean logout();
}
