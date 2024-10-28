public class PasswordController implements PasswordInterface{

    private final DataManager<Account,String> accountDataManager;

    PasswordController(DataManager<Account,String> adm){
        accountDataManager = adm;
    }

    @Override
    public Account validate(String accountId, String password) {
        // check if account exists
        Account account = accountDataManager.retrieve(accountId);
        if(account != null){
            if(account.validatePassword(password)){
                return account;
            }else{
                System.out.println("Invalid password");
            }
        }else{
            System.out.println("No such account!");
        }
        return null;
    }

    @Override
    public boolean changePassword(Account account, String newPassword) {
        if(resetPasswordValid(newPassword)){
            account.setPassword(newPassword);
            account.setFirstLogin(false);
            accountDataManager.update(account);
            return true;
        }
        return false;
    }

    @Override
    public boolean checkFirstLogin(Account account) {
        return account.isFirstLogin();
    }

    @Override
    public boolean resetPasswordValid(String newPassword) {
        return newPassword.length() >= 8 && newPassword.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).*");
    }
}
