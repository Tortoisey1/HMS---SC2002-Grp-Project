package app;
import java.util.Scanner;

public class Global {
    private static Scanner scanner;
    public static Scanner getScanner(){
        if (scanner == null){
            scanner = new Scanner(System.in);
        }
        return scanner;
    }
}
