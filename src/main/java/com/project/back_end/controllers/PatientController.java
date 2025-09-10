package com.project.back_end.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.back_end.DTO.Login;
import com.project.back_end.models.Patient;
import com.project.back_end.services.ClinicService;
import com.project.back_end.services.PatientService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;
    private final ClinicService service;

    /**
     * Get patient details
     * @param token Authentication token for the patient
     * @return Patient details if successful and an error message with appropriate HTTP status
     */
    @GetMapping("/{token}")
    public ResponseEntity<Map<String, Object>> getPatientDetails(@PathVariable final String token) {
        if (service.validateToken(token, "patient")) {
            return patientService.getPatientDetails(token);
        } else {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid token"));
        }
    }

    /**
     * Create a new patient
     * @param patient Patient details to be created
     * @return Response with success message or an error message if patient creation failed
     */
    @PostMapping()
    public ResponseEntity<Map<String, String>> createPatient(@RequestBody final Patient patient) {
        if (!service.validatePatient(patient)) {
            return ResponseEntity.status(409).body(Map.of("error", "Patient with email id or phone no already exist"));
        }
        
        int result = patientService.createPatient(patient);
        if (result == 1) {
            return ResponseEntity.status(201).body(Map.of("message", "Signup successful"));
        } else {
            return ResponseEntity.status(500).body(Map.of("error", "Internal server error"));
        }
    }

    /**
     * Login patient
     * @param login Login credentials (email, password)
     * @return The result of the login validation (success or failure)
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginPatient(@RequestBody final Login login) {
        return service.validatePatientLogin(login);
    }

    /**
     * Get patient appointments
     * @param id ID of the patient
     * @param token Authentication token for the patient
     * @return The list of patient appointments or an error message
     */
    @GetMapping("/{id}/{token}")
    public ResponseEntity<Map<String, Object>> getPatientAppointments(@PathVariable final Long id, @PathVariable final String token) {
        if (!service.validateToken(token, "patient")) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid token"));
        }
        
        return patientService.getPatientAppointment(id, token);
    }

    /**
     * Filters patient appointments
     * @param condition Condition to filter appointments (e.g. "upcoming", "past")
     * @param name Name of description for filtering (e.g. doctor name, appointment type)
     * @param token Authentication token for the patient
     * @return Filtered appointments or an error message
     */
    @GetMapping("/filter/{condition}/{name}/{token}")
    public ResponseEntity<Map<String, Object>> filterPatientAppointments(@PathVariable final String condition, @PathVariable final String name, @PathVariable final String token) {
        if (!service.validateToken(token, "patient")) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid token"));
        }
        
        return service.filterPatient(condition, name, token);
    }
}
