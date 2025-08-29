package com.project.back_end.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

/**
 * Appointment model represents a scheduled meeting between a doctor and a patient.
 * - Includes metadata such as the date, time, status of the appointment, and helper methods to extract specific time-based information.
 * - This model links together the Doctor and Patient entities to form the core of the clinic's scheduling system.
 */
@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    private Doctor doctor;

    @ManyToOne
    @NotNull
    private Patient patient;

    @Future(message = "Appointment time must be in the future")
    private LocalDateTime appointmentTime;

    @NotNull
    private int status; // 0 for Scheduled, 1 for Completed

    /**
     * Get Id
     * @return id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Get Doctor
     * @return doctor
     */
    public Doctor getDoctor() {
        return this.doctor;
    }

    /**
     * Get Patient
     * @return patient
     */
    public Patient getPatient() {
        return this.patient;
    }

    /**
     * Get Appointment Time
     * @return appointmentTime
     */
    public LocalDateTime getAppointmentTime() {
        return this.appointmentTime;
    }

    /**
     * Get Status
     * @return status
     */
    public int getStatus() {
        return this.status;
    }

    /**
     * Set Doctor
     * @param doctor Doctor
     */
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    /**
     * Set Patient
     * @param patient Patient
     */
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    /**
     * Set Appointment Time
     * @param appointmentTime Appointment Time
     */
    public void setAppointmentTime(LocalDateTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    /**
     * Set Status
     * @param status Status
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Gets appointment end time. Appointments last 1 hour.
     * @return LocalDateTime indicating the appointment end time
     */
    @Transient
    public LocalDateTime getEndTime() {
        return this.appointmentTime.plusHours(1);
    }

    /**
     * Get the appointment date
     * @return LocalDate indicating the appointment date
     */
    @Transient
    public LocalDate getAppointmentDate() {
        return this.appointmentTime.toLocalDate();
    }

    /**
     * Get appointment time
     * @return LocalTime indicating the appointment time
     */
    @Transient
    public LocalTime getAppointmentTimeOnly() {
        return this.appointmentTime.toLocalTime();
    }
}
