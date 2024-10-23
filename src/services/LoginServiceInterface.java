package services;

import information.id.UserID;

public interface LoginServiceInterface {
    boolean validateLogin(UserID userId, String inputPassword);

    void changePassword(UserID userId, String newPassword);

    void recordLoginAttempt(UserID userId);

    int getLoginAttempts(UserID userId);

    void resetLoginAttempts(String userId);

    void addUser(UserID userId);
}
