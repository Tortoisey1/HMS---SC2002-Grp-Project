package menu.dialogs;

public class Dialog {
   public static void showSuccessful(String keyword){
       System.out.println("====================");
       System.out.println("Successfully " + keyword +" !");
       System.out.println("====================");
   }

    public static void showInvalidInput(String keyword){
        System.out.println("++++++++++++++++++++");
        System.out.println("Invalid Input for " + keyword + " !");
        System.out.println("++++++++++++++++++++");
    }

}
