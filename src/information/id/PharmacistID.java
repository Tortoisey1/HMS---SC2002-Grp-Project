package information.id;

import java.util.regex.Pattern;
/**
 * PharmacistID class implements all the basic behaviours from UserID
 */
public class PharmacistID implements UserID {
    private String id;
    private static int counter = 0;// for unique creation
    /**
     * Constructs PharmacistID with the field {@code id} with the generated ID like "D001"
     * using static format method from {@link String}
     */
    public PharmacistID() {
        this.id = "P" + String.format("%03d", ++counter); // Generates ID like "P001"
    }

    /**
     * Retrieve {@code id} of this PharmacistID
     * @return String
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * check if {@param id} is valid
     * @return true if it met the regex condition else false
     */
    @Override
    public boolean isValidUserId(String id) {
        String regex = "^P\\d{3}$"; // Regex pattern for DoctorID
        return Pattern.matches(regex, id);
    }

    /**
     * Sets the new {@param id}
     */
    @Override
    public void setId(String id) {
        this.id = id;
    }
}
