package information.id;

import java.util.regex.Pattern;

public class PatientID implements UserID {
    private final String id;
    private static int counter = 0;// for unique creation

    public PatientID() {
        this.id = "P" + (1000 + (++counter)); // Generates ID like "P1001"
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean isValidUserId(String id) {
        String regex = "^P[1-9]\\d{3}$"; // Regex pattern for PatientID
        return Pattern.matches(regex, id);
    }
}
