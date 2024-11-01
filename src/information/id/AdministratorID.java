package information.id;

import java.util.regex.Pattern;

public class AdministratorID implements UserID {
    private final String id;
    private static int counter = 0; // for unique creation

    // Default constructor for auto-generating IDs
    public AdministratorID() {
        this.id = "A" + String.format("%03d", ++counter); // Generates ID like "A001"
    }

    // New constructor that accepts a specific ID string
    public AdministratorID(String id) {
        if (isValidUserId(id)) {
            this.id = id;
        } else {
            throw new IllegalArgumentException("Invalid Administrator ID format");
        }
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean isValidUserId(String id) {
        String regex = "^A\\d{3}$"; // Regex pattern for AdministratorID
        return Pattern.matches(regex, id);
    }
}
