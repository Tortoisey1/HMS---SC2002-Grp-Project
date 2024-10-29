package merged;
import java.util.Scanner;

public class Global {
    private static Scanner GLOBAL_SCANNER;

    public static Scanner scanner(){
        if(GLOBAL_SCANNER == null){
            GLOBAL_SCANNER = new Scanner(System.in);
        }
        return GLOBAL_SCANNER;
    }

}
