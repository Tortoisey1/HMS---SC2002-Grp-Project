package enums;

public enum MedicalService {
    XRAY,
    CONSULTATION,
    BLOOD_TEST,
    MRI,
    ULTRASOUND,
    VACCINATION,
    PHYSIOTHERAPY;

    public static void printMedicalServices() {
        for (MedicalService service : MedicalService.values()) {
            System.out.println((service.ordinal() + 1) + ". " + service);
        }
    }
}
