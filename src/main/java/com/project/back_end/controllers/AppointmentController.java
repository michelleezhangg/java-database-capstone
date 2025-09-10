package com.project.back_end.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.back_end.models.Appointment;
import com.project.back_end.services.AppointmentService;
import com.project.back_end.services.ClinicService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final ClinicService service;

    /**
     * Get appointments
     * @param date Appointment date
     * @param patientName Patient name
     * @param token Token to validate
     * @return Response with appointments based on the criteria or an error message is token validation failed
     */
    @GetMapping("/{date}/{patientName}/{token}")
    public ResponseEntity<Map<String, Object>> getAppointments(
        @PathVariable final String date,
        @PathVariable final String patientName,
        @PathVariable final String token) {
        if (!service.validateToken(token, "doctor")) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid token"));
        }
        
        LocalDateTime appointmentDate = LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        Map<String, Object> result = appointmentService.getAppointment(patientName, appointmentDate, token);
        return ResponseEntity.ok(result);
    }

    /**
     * Book an appointment
     * @param token Token to validate
     * @param appointment Appointment to be booked
     * @return Response with success message or an error message if token validation failed or appointment booking failed
     */
    @PostMapping("/{token}")
    public ResponseEntity<Map<String, String>> bookAppointment(@PathVariable final String token, @RequestBody final Appointment appointment) {
        if (!service.validateToken(token, "patient")) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid token"));
        }
        
        int validationResult = service.validateAppointment(appointment);
        if (validationResult == -1) {
            return ResponseEntity.badRequest().body(Map.of("error", "Doctor not found"));
        }
        if (validationResult == 0) {
            return ResponseEntity.badRequest().body(Map.of("error", "Appointment time not available"));
        }
        
        int bookingResult = appointmentService.bookAppointment(appointment);
        if (bookingResult == 1) {
            return ResponseEntity.status(201).body(Map.of("message", "Appointment booked successfully"));
        } else {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to book appointment"));
        }
    }

    /**
     * Update appointment
     * @param token Token to validate
     * @param appointment Appointment to be updated
     * @return Response with success message or an error message if token validation failed or appointment update failed
     */
    @PutMapping("/{token}")
    public ResponseEntity<Map<String, String>> updateAppointment(@PathVariable final String token, @RequestBody final Appointment appointment) {
        if (!service.validateToken(token, "patient")) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid token"));
        }
        
        return appointmentService.updateAppointment(appointment);
    }

    /**
     * Cancel appointment
     * @param id Appointment ID
     * @param token Token to validate
     * @return Response with success message or an error message if token validation failed or appointment cancellation failed
     */
    @DeleteMapping("/{id}/{token}")
    public ResponseEntity<Map<String, String>> cancelAppointment(@PathVariable final String id, @PathVariable final String token) {
        if (!service.validateToken(token, "patient")) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid token"));
        }
        
        return appointmentService.cancelAppointment(Long.parseLong(id), token);
    }
}
