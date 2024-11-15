package enums;


/**
 * All the possible type of MedicalService provided
 */
public enum MedicalService {
    XRAY,
    CONSULTATION,
    BLOOD_TEST,
    MRI,
    ULTRASOUND,
    VACCINATION,
    PHYSIOTHERAPY;

    /**
     * Static method to print all the services
     */
    public static void printMedicalServices() {
        for (MedicalService service : MedicalService.values()) {
            System.out.println((service.ordinal() + 1) + ". " + service);
        }
    }
}
