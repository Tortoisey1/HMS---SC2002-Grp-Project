package services.authentication;
import entities.User;
import exceptions.InvalidPasswordException;


/**
 * An interface with the required abstract methods for a login system
 */
public interface PasswordInterface {

    /**
     * check if account of type {@link User} exist in the data manager
     * based on the {@param accountId} {@param password} input by the user
     * @return {@link User} if account exist
     */
    public User validate(User account, String accountId, String password);

    /**
     * Change password for user
     * based on {@param newPassword} input by the user
     * @return {@code true} if password is valid
     * @throws InvalidPasswordException when password is invalid
     */
    public boolean changePassword(User account, String newPassword) throws InvalidPasswordException;

    /**
     * @return {@code true} if user login for the first time
     */
    public boolean checkFirstLogin(User account);
}
