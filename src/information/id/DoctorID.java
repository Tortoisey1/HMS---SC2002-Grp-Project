package information.id;

import java.util.regex.Pattern;

public class DoctorID implements UserID {
    private final String id;
    private static int counter = 0;// for unique creation

    public DoctorID() {
        this.id = "D" + String.format("%03d", ++counter); // Generates ID like "D001"
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean isValidUserId(String id) {
        String regex = "^D\\d{3}$"; // Regex pattern for DoctorID
        return Pattern.matches(regex, id);
    }
}
