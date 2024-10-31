// package information;

// import enums.AppointmentStatus;
// import information.id.DoctorID;
// import information.id.PatientID;
// import information.medical.AppointmentOutcomeRecord;
// import java.time.LocalDateTime;

// public class AppointmentInformation {
//     private PatientID patientId;
//     private DoctorID doctorId;
//     private AppointmentStatus appointmentStatus;
//     private LocalDateTime appointmentDateTime;
//     private AppointmentOutcomeRecord appointmentOutcomeRecord;
//     // from kovan
//     private String appointmentID;
//     private String timeOfTreatment;
//     private String dateOfTreatment;

//     public Appointment(PatientID patientId, DoctorID doctorId, AppointmentStatus appointmentStatus,
//             LocalDateTime appointmentDateTime, AppointmentOutcomeRecord appointmentOutcomeRecord) {
//         this.patientId = patientId;
//         this.doctorId = doctorId;
//         this.appointmentStatus = appointmentStatus;
//         this.appointmentDateTime = appointmentDateTime;
//         this.appointmentOutcomeRecord = appointmentOutcomeRecord;
//     }

//     public PatientID getPatientId() {
//         return patientId;
//     }

//     public void setPatientId(PatientID patientId) {
//         this.patientId = patientId;
//     }

//     public DoctorID getDoctorId() {
//         return doctorId;
//     }

//     public void setDoctorId(DoctorID doctorId) {
//         this.doctorId = doctorId;
//     }

//     public AppointmentStatus getAppointmentStatus() {
//         return appointmentStatus;
//     }

//     public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
//         this.appointmentStatus = appointmentStatus;
//     }

//     public LocalDateTime getAppointmentDateTime() {
//         return appointmentDateTime;
//     }

//     public void setAppointmentDateTime(LocalDateTime appointmentDateTime) {
//         this.appointmentDateTime = appointmentDateTime;
//     }

//     public AppointmentOutcomeRecord getAppointmentOutcomeRecord() {
//         return appointmentOutcomeRecord;
//     }

//     public void setAppointmentOutcomeRecord(AppointmentOutcomeRecord appointmentOutcomeRecord) {
//         this.appointmentOutcomeRecord = appointmentOutcomeRecord;
//     }

//     // @Override
//     // public int compareTo(Appointment other) {
//     //     if (this.getDateOfAppointment() > other.getDateOfAppointment()) {
//     //         return 1;
//     //     }
//     //     return -1;
//     // }

// }
