import java.io.IOException;

import management.PatientDataManager;

public class test {
    public static void main(String[] args) throws IOException {
        PatientDataManager patientData = new PatientDataManager();
        System.out.println(PatientDataManager.getInstance().getList().get(0).getUserInformation()
                .getPrivateInformation().getName());
        patientData.writeAll();
    }
}
