package com.project.back_end.services;

import java.util.Map;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.back_end.models.Prescription;
import com.project.back_end.repositories.PrescriptionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrescriptionService {
    private final PrescriptionRepository prescriptionRepository;

    /**
     * Saves a prescription to the database
     * @param prescription Prescription to be saved
     * @return Response with a message indicating the result of the save operation
     */
    public ResponseEntity<Map<String, String>> savePrescription(final Prescription prescription) {
        try {
            prescriptionRepository.save(prescription);
            return ResponseEntity.status(201).body(Map.of("message", "Prescription saved"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Internal server error"));
        }
    }

    /**
     * Retrieves a prescription from the database based on the appointment ID
     * @param appointmentId ID of the appointment associated with the prescription
     * @return Response with the prescription if found, or an error message if not found
     */
    public ResponseEntity<Map<String, Object>> getPrescription(final Long appointmentId) {
        try {
            final List<Prescription> prescription = prescriptionRepository.findByAppointmentId(appointmentId);
            if (prescription == null || prescription.isEmpty()) {
                return ResponseEntity.status(404).body(Map.of("error", "Prescription not found"));
            }
            return ResponseEntity.ok(Map.of("prescription", prescription));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Internal server error"));
        }
    }
}
