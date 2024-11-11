package services.patient;


import app.Global;
import entities.Patient;
import enums.TransactionStatus;
import information.MedicalBill;
import management.DataManager;
import management.MedicalBillDataManager;
import menu.dialogs.Dialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;

public class MedicalBillingServicePatient {

    private DataManager<MedicalBill, String> medicalBillDataManager;
    private static MedicalBillingServicePatient medicalBillingServicePatient;
    private Patient currentUser;

    public MedicalBillingServicePatient(
            DataManager<MedicalBill, String> medicalBillDataManager,
            Patient currentUser
    ) {
        this.medicalBillDataManager = medicalBillDataManager;
        this.currentUser = currentUser;
    }

    public static MedicalBillingServicePatient getInstance(Patient patient) {
        if (medicalBillingServicePatient == null) {
            return medicalBillingServicePatient = new MedicalBillingServicePatient(
                    MedicalBillDataManager.getInstance(),
                    patient
            );
        }
        return medicalBillingServicePatient;
    }

    public void retrieveUnPaidBills(){
        if(medicalBillDataManager instanceof MedicalBillDataManager){
            int count = 1;
            String choice;
            HashMap<Integer,MedicalBill> transactionHashMap = new HashMap<>();
            ArrayList<MedicalBill> allUnpaid = new ArrayList<>();
            allUnpaid = ((MedicalBillDataManager) medicalBillDataManager).retrieveUnpaidBillForPatient(
                    currentUser.getUserInformation().getID().getId());
           if(allUnpaid != null) {
               for(MedicalBill bill : allUnpaid){
                   transactionHashMap.put(count++,bill);
               }
           }
            System.out.println("These are your unpaid bills:");
            transactionHashMap.forEach((key, value) -> {
                System.out.println("===========================");
                System.out.println("Press (" + key + ") to pay for appointment on " +
                        value.getDateOfAppointment());
                System.out.println("STATUS:" + value.getTransactionStatus().toString());
                System.out.println("FEE: $ " + value.getConsultationFee());
                System.out.println("TransactionRef: $ " + value.getTransactionRef());
                value.checkMoreThanOneMonth();
                if(value.getMoreThanOneMonth()){
                    System.out.println("Additional $10 for late payment not made more than 30 days");
                    long totalFee = value.getConsultationFee() + 10;
                    System.out.println("Total Fee:$ " + totalFee);
                }
                System.out.println("===========================");
            });
            while(true){
                try{
                    System.out.println("Input here \n or (-) to go back");
                    choice = Global.getScanner().next();
                    if(choice.charAt(0) == '-'){
                        break;
                    }
                    if(transactionHashMap.containsKey(Integer.parseInt(choice))){
                        MedicalBill bill = transactionHashMap.get(Integer.parseInt(choice));
                        if(PaymentService.creditCardPrompt()){
                            bill.setTransactionStatus(TransactionStatus.PAID);
                            System.out.println("From bill" + bill.getTransactionRef());
                            medicalBillDataManager.update(bill);
                            break;
                        }
                    } else {
                        Dialog.showInvalidInput("MedicalBill");
                    }
                }catch(InputMismatchException | NumberFormatException e ) {
                    Dialog.showInvalidInput("MedicalBill");
                    Global.getScanner().nextLine();
                }
            }
        }
    }

}