package information.id;

public interface UserID {
    String getId(); // returns the ID

    boolean isValidUserId(String id);

    void setId(String id);
}
