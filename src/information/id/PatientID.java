package information.id;

public class PatientID implements UserID {
    private final String id;
    private static int counter = 0;// for unique creation

    public PatientID() {
        this.id = "P" + (1000+(++counter)); // Generates ID like "P1001"
    }

    @Override
    public String getId() {
        return id;
    }
}
