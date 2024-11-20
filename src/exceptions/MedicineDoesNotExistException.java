package exceptions;

/**
 * Custom exception class to handle errors when a medicine does not exist.
 * 
 * <p>
 * This exception is thrown when a requested medicine is not found in the
 * database
 * The message provided during instantiation specifies the reason for the
 * exception, such as the medicine
 * not being available or not existing in the system.
 * 
 * <p>
 * Common scenarios for this exception:
 * <ul>
 * <li>Searching for a medicine that is not in the database</li>
 * <li>Attempting to retrieve a medicine by a non-existing ID or name</li>
 * <li>Trying to perform an action on a medicine that has been discontinued or
 * removed from the system</li>
 * </ul>
 * 
 * <p>
 * Example usage:
 * 
 * <pre>
* {@code
 * if (!medicineExists(medicineId)) {
 *     throw new MedicineDoesNotExistException("Medicine with ID " + medicineId + " does not exist.");
 * }
 * }
* </pre>
 * 
 */

public class MedicineDoesNotExistException extends Exception {
    /**
     * Constructs a new {@code MedicineDoesNotExistException} with the specified
     * detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public MedicineDoesNotExistException(String message) {
        super(message);
    }
}
