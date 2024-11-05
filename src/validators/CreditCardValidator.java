package validators;

import app.Global;
import exceptions.InvalidPhoneNumberException;
import menu.dialogs.Dialog;
import services.patient.PaymentService;

public class CreditCardValidator {
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
