package entities;

import information.UserInformation;

public class Pharmacist extends Staff {
    public Pharmacist(UserInformation userInformation) {
        super(userInformation);
    }
    // Pharmacists can view the Appointment Outcome Record to fulfill medication
    // prescriptions orders from doctors.
    // ○ Pharmacists can update the status of prescription in the Appointment Outcome
    // Record (e.g., pending to dispensed).
    // ○ Pharmacists can monitor the inventory of medications, including tracking stock
    // levels.
    // ○ Pharmacists can submit replenishment requests to administrators when stock
    // levels are low.
}
