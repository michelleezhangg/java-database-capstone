package com.project.back_end.services;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.back_end.DTO.Login;
import com.project.back_end.models.Admin;
import com.project.back_end.models.Appointment;
import com.project.back_end.models.Patient;
import com.project.back_end.repositories.AdminRepository;
import com.project.back_end.repositories.DoctorRepository;
import com.project.back_end.repositories.PatientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClinicService {
    private final TokenService tokenService;
    private final AdminRepository adminRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final DoctorService doctorService;
    private final PatientService patientService;

    /**
     * Validates a token for a specific user type
     * @param token Token to be validated
     * @param user User type (admin, doctor, patient)
     * @return True if token is valid, false otherwise
     */
    public boolean validateToken(final String token, final String user) {
        return tokenService.validateToken(token, user);
    }

    /**
     * Validates the login credentials of an admin
     * @param receivedAdmin Admin credentials (username and password) to be validated
     * @return A generated token if the admin is authenticated
     */
    public ResponseEntity<Map<String, String>> validateAdmin(final Admin receivedAdmin) {
        Admin admin = adminRepository.findByUsername(receivedAdmin.getUsername());
        if (admin == null || !admin.getPassword().equals(receivedAdmin.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }
        String token = tokenService.generateToken(admin.getUsername());
        return ResponseEntity.ok(Map.of("token", token));
    }

    /**
     * Filters doctors based on name, specialty, and available time
     * @param name Name of the doctor
     * @param specialty Specialty of the doctor
     * @param time Available time of the doctor
     * @return List of doctors that match the filtering criteria
     */
    public Map<String, Object> filterDoctor(final String name, final String specialty, final String time) {
        if (name != null && specialty != null && time != null) {
            return doctorService.filterDoctorsByNameSpecialtyandTime(name, specialty, time);
        } else if (name != null && specialty != null) {
            return doctorService.filtersDoctorByNameAndSpecialty(name, specialty);
        } else if (name != null && time != null) {
            return doctorService.filterDoctorByNameAndTime(name, time);
        } else if (name != null) {
            return doctorService.findDoctorByName(name);
        } else if (specialty != null) {
            return doctorService.filterDoctorBySpecialty(specialty);
        } else if (time != null) {
            return doctorService.filterDoctorsByTime(time);
        } else {
            return Map.of("doctors", doctorService.getDoctors());
        }
    }

    /**
     * Validates whether an appointment is available based on the doctor's schedule
     * @param appointment Appointment to validate
     * @return 1 if the appointment time is valid, 0 if unavailable, and -1 if the doctor doesn't exist
     */
    public int validateAppointment(final Appointment appointment) {
        if (!doctorRepository.existsById(appointment.getDoctor().getId())) {
            return -1; // Doctor doesn't exist
        }
        
        java.util.List<String> availableSlots = doctorService.getDoctorAvailability(
            appointment.getDoctor().getId(), 
            appointment.getAppointmentTime().toLocalDate()
        );
        
        String appointmentTime = appointment.getAppointmentTime().toLocalTime().toString();
        return availableSlots.stream()
            .anyMatch(slot -> slot.startsWith(appointmentTime)) ? 1 : 0;
    }

    /**
     * Checks whether a patient exists based on their email or phone number
     * @param patient Patient to validate
     * @return True if the patient does not exist and false if it exists already
     */
    public boolean validatePatient(final Patient patient) {
        Patient existingPatient = patientRepository.findByEmailOrPhone(patient.getEmail(), patient.getPhone());
        return existingPatient == null; // True if patient doesn't exist
    }

    /**
     * Validates a patient's loging credentials (email and password)
     * @param login Login credentials of the patient (email and password)
     * @return A generated token if the login is valid
     */
    public ResponseEntity<Map<String, String>> validatePatientLogin(final Login login) {
        Patient patient = patientRepository.findByEmail(login.getIdentifier());
        if (patient == null || !patient.getPassword().equals(login.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }
        String token = tokenService.generateToken(patient.getEmail());
        return ResponseEntity.ok(Map.of("token", token));
    }
    
    /**
     * Filters patient appointments based on certain criteria, like condition and doctor name
     * @param condition Medical condition to filter appointments by
     * @param name Doctor's name to filter appointments by
     * @param token Authentication token to identify the patient
     * @return Filtered list of patient appointments based on the criteria
     */
    public ResponseEntity<Map<String, Object>> filterPatient(final String condition, final String name, final String token) {
        String email = tokenService.validateToken(token);
        if (email == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid token"));
        }
        
        Patient patient = patientRepository.findByEmail(email);
        if (patient == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Patient not found"));
        }
        
        if (condition != null && name != null) {
            return patientService.filterByDoctorAndCondition(condition, name, patient.getId());
        } else if (condition != null) {
            return patientService.filterByCondition(condition, patient.getId());
        } else if (name != null) {
            return patientService.filterByDoctor(name, patient.getId());
        } else {
            return patientService.getPatientAppointment(patient.getId(), token);
        }
    }
}
