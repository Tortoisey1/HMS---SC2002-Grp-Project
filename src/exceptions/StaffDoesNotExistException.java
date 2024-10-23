package exceptions;

public class StaffDoesNotExistException extends Exception {
    public StaffDoesNotExistException(String message) {
        super(message);
    }
}
