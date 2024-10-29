import MainMenu;

package ui;
public abstract class GenericUi {
    void logOut(){
        MainMenu.isAuthenticated = false;
    }
}
