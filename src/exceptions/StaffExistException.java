package exceptions;

/**
 * Custom exception class to handle cases where a staff member already exists.
 * 
 * <p>
 * This exception is thrown when an attempt is made to add or register a staff
 * member
 * who already exists in the system. The message provided during instantiation
 * specifies the reason for the exception,
 * such as a duplicate staff entry or conflict in records.
 * 
 * <p>
 * Common scenarios for this exception:
 * <ul>
 * <li>Attempting to add a staff member to the system when they already exist
 * (e.g., by ID, email, or other identifier)</li>
 * <li>Trying to register a staff member with the same details or credentials as
 * an existing one</li>
 * <li>Duplicate data entry in a staff management system</li>
 * </ul>
 * 
 * <p>
 * Example usage:
 * 
 * <pre>
* {@code
 * if (staffExists(newStaff)) {
 *     throw new StaffExistException("Staff with ID " + newStaff.getId() + " already exists.");
 * }
 * }
* </pre>
 * 
 */

public class StaffExistException extends Exception {
    /**
     * Constructs a new {@code StaffExistException} with the specified detail
     * message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public StaffExistException(String message) {
        super(message);
    }
}
