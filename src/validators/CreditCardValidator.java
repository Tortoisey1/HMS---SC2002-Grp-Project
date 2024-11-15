package validators;

import app.Global;
import menu.dialogs.Dialog;



/**
 * This helper class provides static method to validate
 * credit card info and ensure it meets the conditions below
 * handles @exception NumberFormatException if
 * input is not integers
 */

public class CreditCardValidator {

    /**
     * @param cvv to validate
     * @return true if credit card info met the conditions below
     * and valid else false
     */
    public static boolean validCVV(String cvv){
        int cvvInteger;
        try {
            if(cvv.length() == 3){
                cvvInteger = Integer.parseInt(cvv);
                return true;
            }
        }catch(NumberFormatException e){
            Dialog.showInvalidInput("CVV");
            Global.getScanner().nextLine();
            return false;
        }
        return false;
    }
}
