package com.project.back_end.models;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Prescription model is a MongoDB document that stores medication instructions issued during appointments.
 * - It includes the patient's name, the referenced appointment, prescribed drugs, dosage, and optional notes from the doctor.
 */
@Document(collection = "prescriptions")
public class Prescription {
    @Id
    private String _id;

    @NotNull
    @Size(min = 3, max = 100)
    private String patientName;

    @NotNull
    private Long appointmentId;

    @NotNull
    @Size(min = 3, max = 100)
    private String medication;

    @NotNull
    @Size(min = 3, max = 20)
    private String dosage;

    @Size(max = 200)
    private String doctorNotes;

    @NotNull
    @Size(min = 0)
    private int refillCount;

    @Size(max = 100)
    private String pharmacyName;

    /**
     * Constructor for Prescription
     * @param patientName Patient Name
     * @param appointmentId Appointment ID
     * @param medication Medication
     * @param dosage Dosage
     * @param refillCount Refill Count
     * @param pharmacyCount Pharmacy Count
     */
    public Prescription(String patientName, Long appointmentId, String medication, String dosage, int refillCount, String pharmacyName) {
        this.patientName = patientName;
        this.appointmentId = appointmentId;
        this.medication = medication;
        this.dosage = dosage;
        this.refillCount = refillCount;
        this.pharmacyName = pharmacyName;
    }

    /**
     * Get ID
     * @return id
     */
    public String getId() {
        return this._id;
    }

    /**
     * Get Patient Name
     * @return patientName
     */
    public String getPatientName() {
        return this.patientName;
    }

    /**
     * Get Appointment ID
     * @return appointmentId
     */
    public Long getAppointmentId() {
        return this.appointmentId;
    }

    /**
     * Get Medication
     * @return medication
     */
    public String getMedication() {
        return this.medication;
    }

    /**
     * Get Dosage
     * @return dosage
     */
    public String getDosage() {
        return this.dosage;
    }

    /**
     * Get Doctor Notes
     * @return doctorNotes
     */
    public String getDoctorNotes() {
        return this.doctorNotes;
    }

    /**
     * Get Refill Count
     * @return refillCount
     */
    public int getRefillCount() {
        return this.refillCount;
    }

    /**
     * Get Pharmacy Name
     * @return pharmacyName
     */
    public String getPharmacyName() {
        return this.pharmacyName;
    }

    /**
     * Set Patient Name
     * @param patientName Patient Name
     */
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    /**
     * Set Appointment ID
     * @param appointmentId Appointment ID
     */
    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Set Medication
     * @param medication Medication
     */
    public void setMedication(String medication) {
        this.medication = medication;
    }

    /**
     * Set Dosage
     * @param dosage Dosage
     */
    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    /**
     * Set Doctor Notes
     * @param doctorNotes Doctor Notes
     */
    public void setDoctorNotes(String doctorNotes) {
        this.doctorNotes = doctorNotes;
    }

    /**
     * Set Refill Count
     * @param refillCount Refill Count
     */
    public void setRefillCount(int refillCount) {
        this.refillCount = refillCount;
    }

    /**
     * Set Pharmacy Name
     * @param pharmacyName Pharmacy Name
     */
    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }
}
