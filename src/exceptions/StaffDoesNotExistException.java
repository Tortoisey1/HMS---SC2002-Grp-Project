package exceptions;

/**
 * Custom exception class to handle cases where a staff member does not exist.
 * 
 * <p>
 * This exception is thrown when an attempt is made to access or perform an
 * operation on a staff member
 * that does not exist in the system. The message provided during instantiation
 * specifies the reason for the exception,
 * such as a missing or non-existent staff record.
 * 
 * <p>
 * Common scenarios for this exception:
 * <ul>
 * <li>Attempting to find or retrieve a staff member that is not present in the
 * system</li>
 * <li>Searching for a staff member using a non-existent identifier (e.g., staff
 * ID, username)</li>
 * <li>Attempting to perform actions like updating or deleting a non-existing
 * staff record</li>
 * </ul>
 * 
 * <p>
 * Example usage:
 * 
 * <pre>
 * {@code
 * if (!staffExists(staffId)) {
 *     throw new StaffDoesNotExistException("Staff with ID " + staffId + " does not exist.");
 * }
 * }
 * </pre>
 * 
 */
public class StaffDoesNotExistException extends Exception {
    /**
     * Constructs a new {@code StaffDoesNotExistException} with the specified detail
     * message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public StaffDoesNotExistException(String message) {
        super(message);
    }
}
