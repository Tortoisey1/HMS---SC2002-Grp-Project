package information.id;

import java.util.regex.Pattern;


/**
 * AdministratorID class implements all the basic behaviours from UserID
 */

public class AdministratorID implements UserID {
    private String id;
    private static int counter = 0;// for unique creation
    /**
     * Constructs AdministratorID with the field {@code id} with the generated ID like "A001"
     * using static format method from {@link String}
     */
    public AdministratorID() {
        this.id = "A" + String.format("%03d", ++counter);
    }

    /**
     * Retrieve {@code id} of this AdministratorID
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
        String regex = "^A\\d{3}$"; // Regex pattern for AdministratorID
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
