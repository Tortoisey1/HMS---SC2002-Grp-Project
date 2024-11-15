package information.id;

import java.util.regex.Pattern;

/**
 * PatientID class implements all the basic behaviours from UserID
 */
public class PatientID implements UserID {
    private String id;
    private static int counter = 0;// for unique creation

    /**
     * Constructs PatientID with the field {@code id} with the generated ID like "D001"
     * using static format method from {@link String}
     */
    public PatientID() {
        this.id = "P" + (1000 + (++counter)); // Generates ID like "P1001"
    }

    /**
     * Retrieve {@code id} of this PatientID
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
        String regex = "^P[1-9]\\d{3}$"; // Regex pattern for PatientID
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
