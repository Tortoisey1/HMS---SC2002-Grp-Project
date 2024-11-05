package services.patient;


import app.Global;
import information.MedicalBill;
import menu.dialogs.Dialog;
import validators.CreditCardValidator;

import java.util.InputMismatchException;
import java.util.TimerTask;

public class PaymentService {
    public static boolean creditCardPrompt() {
        while (true) {
            try {
                System.out.println("Credit Card number:");
                long ccNumber = Global.getScanner().nextLong();
                System.out.println("CVV:");
                String cvv = Global.getScanner().next();
                PaymentService.processingStimulation();
                if (CreditCardValidator.validCVV(cvv)) {
                    Thread.sleep(1000);
                    Dialog.showSuccessful("PAYMENT MADE");
                    return true;
                } else {
                    Dialog.showInvalidInput("CC Info");
                }
            } catch (InputMismatchException  | InterruptedException e) {
                Dialog.showInvalidInput("CC Info");
                Global.getScanner().nextLine();
            }
        }
    }

    public static void processingStimulation() {
        for(int i = 0; i < 2; i++) {
            try {
                System.out.println("Verifying Credit Card Details");
                System.out.println("...............................");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
