package information.id;

import java.util.regex.Pattern;

/**
 * DoctorID class implements all the basic behaviours from UserID
 */
public class DoctorID implements UserID {
    private String id;
    private static int counter = 0;// for unique creation

    /**
     * Constructs DoctorID with the field {@code id} with the generated ID like "D001"
     * using static format method from {@link String}
     */
    public DoctorID() {
        this.id = "D" + String.format("%03d", ++counter);
    }

    /**
     * Retrieve {@code id} of this DoctorID
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
        String regex = "^D\\d{3}$"; // Regex pattern for DoctorID
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
