public class MainMenu {
    public static boolean isAuthenticated = false;
    public static void main(String[] args) {
        PasswordController pwc = new PasswordController(AccountDataManager.getInstance());
        LoginUi loginView = new LoginUi(pwc);
        Account account = loginView.login();
        if(MainMenu.isAuthenticated){
            System.out.println("Welcome");
            switch (account.getRole()){
                case PATIENT:
                    PatientUi patientView = new PatientUi(PatientController.getInstance(), account.getAccount_Id(), AppointmentController.getInstance());
                    patientView.showOptions();
                    break;
                case PHARMACIST:
                    // UI FOR PHARMACIST HERE
                    break;

            }

        }

    }
}
