package information;

import enums.AppointmentStatus;
import information.id.DoctorID;
import information.id.PatientID;
import information.medical.AppointmentOutcomeRecord;

import java.time.LocalDateTime;

public class AppointmentInformation {
    private PatientID patientId;
    private DoctorID doctorId;
    private AppointmentStatus appointmentStatus;
    private LocalDateTime appointmentDateTime;
    private AppointmentOutcomeRecord appointmentOutcomeRecord;

}
