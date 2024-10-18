package exceptions;

public class LoginAttemptsExceededException extends Exception {
    public LoginAttemptsExceededException(String message) {
        super(message);
    }
}
