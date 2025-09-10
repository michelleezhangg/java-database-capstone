package com.project.back_end.controllers;

import java.time.LocalDate;
import java.util.List;
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

import com.project.back_end.DTO.Login;
import com.project.back_end.models.Doctor;
import com.project.back_end.services.DoctorService;
import com.project.back_end.services.ClinicService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.path}" + "doctor")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;
    private final ClinicService service;

    /**
     * Gets the doctor's availability
     * @param user Role of the user (doctor, patient, admin, and so on)
     * @param doctorId Unique ID of the doctor
     * @param date Date for which the availability needs to be fetched
     * @param token Authentication token for validating the user
     * @return A map with the doctor's availability of an error message
     */
    @GetMapping("/availability/{user}/{doctorId}/{date}/{token}")
    public ResponseEntity<Map<String, Object>> getDoctorAvailability(@PathVariable final String user, @PathVariable final Long doctorId, @PathVariable final LocalDate date, @PathVariable final String token) {
        if (!service.validateToken(token, user)) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid token"));
        }
        
        List<String> availability = doctorService.getDoctorAvailability(doctorId, date);
        return ResponseEntity.ok(Map.of("availability", availability));
    }

    /**
     * Gets a list of doctors
     * @return A list of all doctors
     */
    @GetMapping()
    public ResponseEntity<Map<String, Object>> getDoctors() {
        List<Doctor> doctors = doctorService.getDoctors();
        return ResponseEntity.ok(Map.of("doctors", doctors));
    }

    /**
     * Adds a new doctor
     * @param doctor Doctor details to be added
     * @param token Authentication token for validation
     * @return Success or error messages based on the result of the save operation
     */
    @PostMapping("/{token}")
    public ResponseEntity<Map<String, String>> addDoctor(@RequestBody final Doctor doctor, @PathVariable final String token) {
        if (!service.validateToken(token, "admin")) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid token"));
        }
        
        int result = doctorService.saveDoctor(doctor);
        if (result == 1) {
            return ResponseEntity.status(201).body(Map.of("message", "Doctor added to db"));
        } else if (result == -1) {
            return ResponseEntity.status(409).body(Map.of("error", "Doctor already exists"));
        } else {
            return ResponseEntity.status(500).body(Map.of("error", "Some internal error occured"));
        }
    }

    /**
     * Login a doctor
     * @param login Login details (email, password)
     * @return The result of the login validation
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginDoctor(@RequestBody final Login login) {
        return doctorService.validateDoctor(login);
    }

    /**
     * Updates a doctor's details
     * @param doctor Doctor object with updated details
     * @param token Authentication token for validation
     * @return Success or error message based on the result of the update operation
     */
    @PutMapping("/{token}")
    public ResponseEntity<Map<String, String>> updateDoctor(@RequestBody final Doctor doctor, @PathVariable final String token) {
        if (!service.validateToken(token, "admin")) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid token"));
        }
        
        int result = doctorService.updateDoctor(doctor);
        if (result == 1) {
            return ResponseEntity.ok(Map.of("message", "Doctor updated"));
        } else if (result == -1) {
            return ResponseEntity.status(409).body(Map.of("error", "Doctor not found"));
        } else {
            return ResponseEntity.status(500).body(Map.of("error", "Some internal error occured"));
        }
    }

    /**
     * Deletes a doctor
     * @param id ID for the doctor to be deleted
     * @param token Authentication token for validation
     * @return Success or error messages based on the result of the delete operation
     */
    @DeleteMapping("/{id}/{token}")
    public ResponseEntity<Map<String, String>> deleteDoctor(@PathVariable final Long id, @PathVariable final String token) {
        if (!service.validateToken(token, "admin")) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid token"));
        }
        
        int result = doctorService.deleteDoctor(id);
        if (result == 1) {
            return ResponseEntity.ok(Map.of("message", "Doctor deleted successfully"));
        } else if (result == -1) {
            return ResponseEntity.status(409).body(Map.of("error", "Doctor not found with id"));
        } else {
            return ResponseEntity.status(500).body(Map.of("error", "Some internal error occured"));
        }
    }

    /**
     * Filter doctors
     * @param name Name of the doctor (can be partial)
     * @param time Available time for filtering
     * @param specialty Specialty of the doctor
     * @return A map of filtered doctor data
     */
    @GetMapping("/filter/{name}/{time}/{specialty}")
    public ResponseEntity<Map<String, Object>> filterDoctors(@PathVariable final String name, @PathVariable final String time, @PathVariable final String specialty) {
        Map<String, Object> result = service.filterDoctor(name, specialty, time);
        return ResponseEntity.ok(result);
    }
}
