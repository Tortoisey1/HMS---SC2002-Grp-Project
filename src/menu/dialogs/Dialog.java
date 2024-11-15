package menu.dialogs;

/**
 * This helper class provides static methods for reusability of output
 */
public class Dialog {

    /**
     * @param keyword for the word to be replaced in the dialog
     * Print the dialog when it is successful
     * Can be used for e.g. when details are updated
     */
   public static void showSuccessful(String keyword){
       System.out.println("====================");
       System.out.println("Successfully " + keyword +" !");
       System.out.println("====================");
   }

    /**
     * @param keyword for the word to be replaced in the dialog
     * Print the dialog when it is invalid
     * Can be used for e.g. when password did not meet condition
     */
    public static void showInvalidInput(String keyword){
        System.out.println("++++++++++++++++++++");
        System.out.println("Invalid Input for " + keyword + " !");
        System.out.println("++++++++++++++++++++");
    }

}
