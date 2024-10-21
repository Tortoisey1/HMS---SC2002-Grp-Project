package information;

import enums.AppointmentStatus;
import information.medical.AppointmentOutcomeRecord;

import java.time.LocalDateTime;

public class AppointmentInformation {
    private int patientId;
    private int doctorId;
    private AppointmentStatus appointmentStatus;
    private LocalDateTime appointmentDateTime;
    private AppointmentOutcomeRecord appointmentOutcomeRecord;

}
