package information.id;

import java.util.regex.Pattern;

public class AdministratorID implements UserID {
    private String id;
    private static int counter = 0;// for unique creation

    public AdministratorID() {
        this.id = "A" + String.format("%03d", ++counter); // Generates ID like "A001"
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

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
