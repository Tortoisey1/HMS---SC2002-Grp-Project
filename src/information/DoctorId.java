package information.

import information.id.UserID;

import information.UserID;

public class DoctorID implements UserID {
    private final String id;
    private static int counter = 0;// for unique creation

    public DoctorID() {
        this.id = "D" + String.format("%03d", ++counter);  // Generates ID like "D001"
    }

    @Override
    public String getId() {
        return id;
    }
}
