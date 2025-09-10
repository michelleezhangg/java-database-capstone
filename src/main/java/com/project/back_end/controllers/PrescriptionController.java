package com.project.back_end.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.back_end.models.Prescription;
import com.project.back_end.services.ClinicService;
import com.project.back_end.services.PrescriptionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.path}" + "prescription")
@RequiredArgsConstructor
public class PrescriptionController {
    private final PrescriptionService prescriptionService;
    private final ClinicService service;

    /**
     * Saves a prescription
     * @param token Authentication token for the doctor
     * @param prescription Prescription details to be saved
     * @return Success message if prescription is successfully saved or error message with appropriate HTTP status if the token is invalid
     */
    @PutMapping("/{token}")
    public ResponseEntity<Map<String, String>> savePrescription(@PathVariable final String token, @RequestBody final Prescription prescription) {
        if (!service.validateToken(token, "doctor")) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid token"));
        }
        
        return prescriptionService.savePrescription(prescription);
    }

    /**
     * Get prescription by appointment ID
     * @param appointmentId ID of the appointment to retrieve the prescription for
     * @param token Authentication token for the doctor
     * @return Prescription details if found, message indicating no prescription found or if an error occurred
     */
    @GetMapping("/{appointmentId}/{token}")
    public ResponseEntity<Map<String, Object>> getPrescriptionByAppointmentId(@PathVariable final Long appointmentId, @PathVariable final String token) {
        if (!service.validateToken(token, "doctor")) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid token"));
        }
        
        return prescriptionService.getPrescription(appointmentId);
    }
}
