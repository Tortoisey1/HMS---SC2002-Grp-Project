package app;
import java.util.Scanner;
import java.util.Timer;

public class Global {
    private static Scanner GLOBAL_SCANNER = new Scanner(System.in);
    public static Scanner getScanner() {
        return GLOBAL_SCANNER;
    }
}
