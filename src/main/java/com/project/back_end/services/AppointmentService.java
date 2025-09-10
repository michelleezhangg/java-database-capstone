package com.project.back_end.services;

import java.util.List;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.back_end.models.Appointment;
import com.project.back_end.repositories.AppointmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final TokenService tokenService;

    /**
     * Books a new aappointment.
     * @param appointment The appointment to book
     * @return The status of the booked appointment. 1 for successful and 0 if there's an error.
     */
    public int bookAppointment(final Appointment appointment) {
        try {
            appointmentRepository.save(appointment);
            return 1;
        } catch (Exception error) {
            return 0;
        }
    }
    
    /**
     * Updates an existing appointment
     * @param appointment Appointment to update
     * @return Response message indicating success or failure
     */
    public ResponseEntity<Map<String, String>> updateAppointment(final Appointment appointment) {
        if (appointment == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "No appointment specified."));
        }

        final Optional<Appointment> appointmentToUpdate = appointmentRepository.findById(appointment.getId());

        if (appointmentToUpdate.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Appointment not found."));
        }

        if (validateAppointment(appointment)) {
            appointmentRepository.save(appointment);
        }
        return ResponseEntity.ok(Map.of("message", "Appointment updated successfully."));
    }

    /**
     * Cancels an existing appointment
     * @param id ID of appointment to cancel
     * @param token Authorization token
     * @return Response message indicating success or failure
     */
    public ResponseEntity<Map<String, String>> cancelAppointment(final Long id, final String token) {
        if (id == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Appointment ID cannot be null."));
        }

        final Optional<Appointment> appointment = appointmentRepository.findById(id);

        if (appointment.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Appointment not found."));
        }

        if (tokenService.validateToken(token) == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid token. Cannot cancel appointment."));
        }

        appointmentRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Appointment cancelled successfully."));
    }

    /**
     * Retrieves a list of appointments for a specific doctor on a specific date.
     * @param pname Patient name to filter by
     * @param date Date for appointments
     * @param token Authorization token
     * @return Map containing the list of appointments
     */
    public Map<String, Object> getAppointment(final String pname, final LocalDateTime date, final String token) {
        if (tokenService.validateToken(token) == null) {
            return Map.of("error", "Invalid token");
        }
        
        final LocalDateTime startOfDay = date.toLocalDate().atStartOfDay();
        final LocalDateTime endOfDay = date.toLocalDate().atTime(23, 59, 59);
        
        List<Appointment> appointments = appointmentRepository.findByDoctorIdAndAppointmentTimeBetween(null, startOfDay, endOfDay);
        
        if (pname != null && !pname.isEmpty()) {
            appointments = appointments.stream()
                .filter(apt -> apt.getPatient().getName().toLowerCase().contains(pname.toLowerCase()))
                .toList();
        }
        
        return Map.of("appointments", appointments);
    }

    /**
     * Validates an appointment.
     * @param appointment Appointment to validate.
     * @return True if the appointment is valid and false otherwise.
     */
    private boolean validateAppointment(final Appointment appointment) {
        return true;
    }
}
