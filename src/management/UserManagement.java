package management;

import java.util.List;

import entities.User;

import java.util.ArrayList;

public class UserManagement {
    private static List<User> users = new ArrayList<User>();

    private static List<User> getUsers() {
        return users;
    }

    public static User getUser(String id) { // will only use after checking exist so i am not putting a validation in
                                            // this
        for (User user : UserManagement.getUsers()) {
            if (user.getUserInformation().getID().getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    public static boolean checkUserExist(String id) {
        for (User user : UserManagement.getUsers()) {
            if (user.getUserInformation().getID().getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public static addUser(User user){
        getUsers().add(new User);
    }

}
