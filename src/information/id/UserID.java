package information.id;



/**
 * This Interface represents ID that all users should have in this app
 * It defines the basic properties and behaviours that
 * all user ID should have
 */
public interface UserID {
    /**
     * Retrieves the Type String userId
     * @return String of the ID
     */
    String getId(); // returns the ID

    /**
     * check if {@param id} is valid
     * @return true if it is valid else false
     */
    boolean isValidUserId(String id);
    /**
     * Sets the new {@param id}
     */
    void setId(String id);
}
