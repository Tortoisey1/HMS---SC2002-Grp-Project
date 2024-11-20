package exceptions;

/**
 * Custom exception class to handle errors when a medicine already exists.
 * 
 * <p>
 * This exception is thrown when an attempt is made to add or register a
 * medicine that already exists in
 * the database. The message provided during instantiation
 * specifies the reason for the
 * exception, such as a duplicate entry or conflict in records.
 * 
 * <p>
 * Common scenarios for this exception:
 * <ul>
 * <li>Attempting to add a medicine to the inventory or database when it already
 * exists</li>
 * <li>Trying to register a medicine with the same name or identifier</li>
 * <li>Duplicate data entry in a medication management system</li>
 * </ul>
 * 
 * <p>
 * Example usage:
 * 
 * <pre>
 * {@code
 * if (medicineExists(newMedicine)) {
 *     throw new MedicineExistException("Medicine with name " + newMedicine.getName() + " already exists.");
 * }
 * }
 * </pre>
 * 
 */
public class MedicineExistException extends Exception {

    /**
     * Constructs a new {@code MedicineExistException} with the specified detail
     * message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public MedicineExistException(String message) {
        super(message);
    }
}
