import entities.Account;

public interface PasswordInterface {
    public Account validate(String accountId, String password);
    public boolean changePassword(Account account, String newPassword);
    public boolean checkFirstLogin(Account account);
    public boolean resetPasswordValid(String newPassword);
}
