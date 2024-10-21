package information.id;

public class AdministratorID implements UserID {
    private final String id;
    private static int counter = 0;// for unique creation

    public AdministratorID() {
        this.id = "A" + String.format("%03d", ++counter); // Generates ID like "A001"
    }

    @Override
    public String getId() {
        return id;
    }
}
