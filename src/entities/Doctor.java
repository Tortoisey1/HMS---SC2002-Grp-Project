package entities;

import java.util.List;

import information.UserInformation;

public class Doctor extends Staff {

    public Doctor(UserInformation userInformation) {
        super(userInformation);
        //TODO Auto-generated constructor stub
    }

    private List<Patient> patients;

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

}
