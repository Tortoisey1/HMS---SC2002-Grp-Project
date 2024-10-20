package information;

import enums.AppointmentStatus;
import information.medical.AppointmentOutcomeRecord;

import java.time.LocalDateTime;

public class AppointmentInformation {
    private int PatientId;
    private int DoctorId;
    private AppointmentStatus appointmentStatus;
    private LocalDateTime appointmentDateTime;
    private AppointmentOutcomeRecord appointmentOutcomeRecord;

}
