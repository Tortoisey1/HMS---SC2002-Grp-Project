package exceptions;

/**
 * Custom exception class to handle cases where an entity is not found in the
 * system.
 * 
 * <p>
 * This exception is thrown when an operation is attempted on an entity (such as
 * a user, product, or record)
 * that does not exist in the system. The message provided during instantiation
 * specifies the reason for the exception,
 * such as an entity not being found or not being registered in the system.
 * 
 * <p>
 * Common scenarios for this exception:
 * <ul>
 * <li>Attempting to access a record (e.g., user, product, etc.) that is not
 * present in the system</li>
 * <li>Searching for an entity by ID, name, or other identifier that does not
 * exist</li>
 * <li>Trying to perform actions (e.g., update, delete) on non-existent
 * records</li>
 * </ul>
 * 
 * <p>
 * Example usage:
 * 
 * <pre>
 * {@code
 * if (!isEntityInSystem(entityId)) {
 *     throw new NotInSystem("Entity with ID " + entityId + " is not found in the system.");
 * }
 * }
 * </pre>
 */
public class NotInSystem extends Exception {
    /**
     * Constructs a new {@code NotInSystem} with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public NotInSystem(String message) {
        super(message);
    }
}
