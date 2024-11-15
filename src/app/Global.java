package app;
import java.util.Scanner;

/**
 * Helper class provides a Singleton {@code Scanner} instance
 * that can be used throughout the application
 * Easier to implement scanner and reduce identical repetition cost of resources.
 */

public class Global {
    private static Scanner scanner;
    public static Scanner getScanner(){
        if (scanner == null){
            scanner = new Scanner(System.in);
        }
        return scanner;
    }
}
