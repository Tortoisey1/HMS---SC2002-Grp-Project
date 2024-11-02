package services.authentication;
import entities.User;
import exceptions.InvalidPasswordException;

public interface PasswordInterface {
    public User validate(User account, String accountId, String password);
    public boolean changePassword(User account, String newPassword) throws InvalidPasswordException;
    public boolean checkFirstLogin(User account);
}
