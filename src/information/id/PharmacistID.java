package information.id;

import java.util.regex.Pattern;

public class PharmacistID implements UserID {
    private final String id;
    private static int counter = 0;// for unique creation

    public PharmacistID() {
        this.id = "P" + String.format("%03d", ++counter); // Generates ID like "P001"
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean isValidUserId(String id) {
        String regex = "^P\\d{3}$"; // Regex pattern for DoctorID
        return Pattern.matches(regex, id);
    }
}
