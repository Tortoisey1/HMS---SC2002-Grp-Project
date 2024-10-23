package exceptions;

public class MedicineDoesNotExistException extends Exception {
    public MedicineDoesNotExistException(String message) {
        super(message);
    }
}
