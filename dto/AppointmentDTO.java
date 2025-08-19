package dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class AppointmentDTO {
    // Private fields
    private Long id;
    private Long doctorId;
    private String doctorName;
    private Long patientId;
    private String patientName;
    private String patientEmail;
    private String patientPhone;
    private String patientAddress;
    private LocalDateTime appointmentTime;
    private int status;
    private LocalDate appointmentDate;
    private LocalTime appointmentTimeOnly;
    private LocalDateTime endTime;

    // Constructor
    public AppointmentDTO() {
        this.appointmentDate = this.appointmentTime.toLocalDate();
        this.appointmentTimeOnly = this.appointmentTime.toLocalTime();
        this.endTime = this.appointmentTime.plusHours(1);
    }

    // Getters
    public Long getId() {
        return this.id;
    }

    public Long getDoctorId() {
        return this.doctorId;
    }

    public String getDoctorName() {
        return this.doctorName;
    }

    public Long getPatientId() {
        return this.patientId;
    }

    public String getPatientName() {
        return this.patientName;
    }

    public String getPatientEmail() {
        return this.patientEmail;
    }

    public String getPatientPhone() {
        return this.patientPhone;
    }

    public String getPatientAddress() {
        return this.patientAddress;
    }

    public LocalDateTime getAppointmentTime() {
        return this.appointmentTime;
    }

    public int getStatus() {
        return this.status;
    }

    public LocalDate getAppointmentDate() {
        return this.appointmentDate;
    }

    public LocalTime getAppointmentTimeOnly() {
        return this.appointmentTimeOnly;
    }

    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    // Setters
    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    public void setAppointmentTime(LocalDateTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public void setAppointmentTimeOnly(LocalTime appointmentTimeOnly) {
        this.appointmentTimeOnly = appointmentTimeOnly;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
