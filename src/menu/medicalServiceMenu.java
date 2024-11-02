package menu;

import enums.MedicalService;

public class medicalServiceMenu {
    public static void printMenu() {
        for (int i = 0; i < MedicalService.values().length; i++) {
            System.out.println("Choice " + (i+1) + ": " + MedicalService.values()[i]);
        }
    }

}
