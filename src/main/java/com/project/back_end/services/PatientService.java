package com.project.back_end.services;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.back_end.models.Appointment;
import com.project.back_end.models.Patient;
import com.project.back_end.repositories.AppointmentRepository;
import com.project.back_end.repositories.PatientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final TokenService tokenService;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;

    /**
     * Saves a new patient to the database
     * @param patient Patient to be saved
     * @return 1 on success, 0 on failure
     */
    public int createPatient(final Patient patient) {
        try {
            if (patientRepository.findByEmailOrPhone(patient.getEmail(), patient.getPhone()) != null) {
                return 0; // Patient already exists
            }
            patientRepository.save(patient);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Retrieves a list of appointment for a specific patient
     * @param id Patient ID
     * @param token JWT token containing the email
     * @return Reponse containing a list of appointments of an error message
     */
    public ResponseEntity<Map<String, Object>> getPatientAppointment(final Long id, final String token) {
        String email = tokenService.validateToken(token);
        if (email == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid token"));
        }
        
        Patient patient = patientRepository.findByEmail(email);
        if (patient == null || !patient.getId().equals(id)) {
            return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
        }
        
        List<Appointment> appointments = appointmentRepository.findByPatientId(id);
        return ResponseEntity.ok(Map.of("appointments", appointments));
    }

    /**
     * Filters appointments by condition for a specific patient
     * @param condition Condition to filter by ("past" or "future")
     * @param id Patient ID
     * @return Filtered appointments or an error message
     */
    public ResponseEntity<Map<String, Object>> filterByCondition(final String condition, final Long id) {
        int status = condition.equalsIgnoreCase("past") ? 1 : 0;
        List<Appointment> appointments = appointmentRepository.findByPatient_IdAndStatusOrderByAppointmentTimeAsc(id, status);
        return ResponseEntity.ok(Map.of("appointments", appointments));
    }

    /**
     * Filters the patient's appointment by doctor's name
     * @param name Doctor's name
     * @param patientId Patient's ID
     * @return Filtered appointments or an error message
     */
    public ResponseEntity<Map<String, Object>> filterByDoctor(final String name, final Long patientId) {
        List<Appointment> appointments = appointmentRepository.findByDoctorNameAndPatientId(name, patientId);
        return ResponseEntity.ok(Map.of("appointments", appointments));
    }

    /**
     * Filters the patient's appointments by doctor's name and appointment condition
     * @param condition Condition to filter by ("past" or "future")
     * @param name Doctor's name
     * @param patientId Patient's ID
     * @return Filtered appointments or an error message
     */
    public ResponseEntity<Map<String, Object>> filterByDoctorAndCondition(final String condition, final String name, final Long patientId) {
        int status = condition.equalsIgnoreCase("past") ? 1 : 0;
        List<Appointment> appointments = appointmentRepository.filterByDoctorNameAndPatientIdAndStatus(name, patientId, status);
        return ResponseEntity.ok(Map.of("appointments", appointments));
    }

    /**
     * Fetches the patient's details based on the provided JWT token
     * @param token JWT token containing the email
     * @return Patient's details or an error message
     */
    public ResponseEntity<Map<String, Object>> getPatientDetails(final String token) {
        String email = tokenService.validateToken(token);
        if (email == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid token"));
        }
        
        Patient patient = patientRepository.findByEmail(email);
        if (patient == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Patient not found"));
        }
        
        return ResponseEntity.ok(Map.of("patient", patient));
    }
}
