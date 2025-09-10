package com.project.back_end.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.back_end.DTO.Login;
import com.project.back_end.models.Doctor;
import com.project.back_end.repositories.AppointmentRepository;
import com.project.back_end.repositories.DoctorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;

    /**
     * Fetches the available slots for a specific doctor on a given date
     * @param doctorId ID of the doctor
     * @param date Date for which the availability is needed
     * @return List of available slots for the doctor on the specified date
     */
    public List<String> getDoctorAvailability(final Long doctorId, final LocalDate date) {
        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);
        if (doctor == null) return List.of();
        
        List<String> availableSlots = doctor.getAvailableTimes();
        List<String> bookedSlots = appointmentRepository.findByDoctorIdAndAppointmentTimeBetween(
            doctorId, date.atStartOfDay(), date.atTime(23, 59, 59))
            .stream()
            .map(apt -> apt.getAppointmentTime().toLocalTime().toString())
            .toList();
            
        return availableSlots.stream()
            .filter(slot -> !bookedSlots.contains(slot.split(" - ")[0]))
            .toList();
    }

    /**
     * Saves a new doctor to the database
     * @param doctor Doctor to be saved
     * @return Returns 1 for success, -1 if the doctor already exists, 0 for internal errors
     */
    public int saveDoctor(final Doctor doctor) {
        try {
            if (doctorRepository.findByEmail(doctor.getEmail()) != null) {
                return -1; // Doctor already exists
            }
            doctorRepository.save(doctor);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Updates the details of an existing doctor
     * @param doctor Doctor wih updated details
     * @return Returns 1 for success, -1 if the doctor already exists, 0 for internal errors
     */
    public int updateDoctor(final Doctor doctor) {
        try {
            if (!doctorRepository.existsById(doctor.getId())) {
                return -1; // Doctor not found
            }
            doctorRepository.save(doctor);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Retrieves a list of all doctors
     * @return List of all doctors
     */
    public List<Doctor> getDoctors() {
        return doctorRepository.findAll();
    }

    /**
     * Deletes a doctor by ID
     * @param id ID of the doctor to be deleted
     * @return Returns 1 for success, -1 if the doctor already exists, 0 for internal errors
     */
    public int deleteDoctor(final Long id) {
        try {
            if (!doctorRepository.existsById(id)) {
                return -1; // Doctor not found
            }
            appointmentRepository.deleteAllByDoctorId(id);
            doctorRepository.deleteById(id);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Validates a doctor's login credentials
     * @param login Login containing email and password
     * @return Response with a token if valid or an error message if not
     */
    public ResponseEntity<Map<String, String>> validateDoctor(final Login login) {
        final Doctor doctor = doctorRepository.findByEmail(login.getIdentifier());
        if (doctor == null || !login.getPassword().equals(login.getPassword())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid credentials"));
        }
        return ResponseEntity.ok(Map.of("token", "doctor_token_" + doctor.getId()));
    }

    /**
     * Finds doctors by their name
     * @param name Name of the doctor to search for
     * @return Map with the list of doctors matching the name
     */
    public Map<String, Object> findDoctorByName(final String name) {
        List<Doctor> doctors = doctorRepository.findByNameLike(name);
        return Map.of("doctors", doctors);
    }

    /**
     * Filters doctors by name, specialty, and availability during AM/PM
     * @param name Doctor's name
     * @param specialty Doctor's specialty
     * @param amOrPm Time of day: AM/PM
     * @return Map with the filtered list of doctors
     */
    public Map<String, Object> filterDoctorsByNameSpecialtyandTime(final String name, final String specialty, final String amOrPm) {
        List<Doctor> doctors = doctorRepository.findByNameContainingIgnoreCaseAndSpecialtyIgnoreCase(name, specialty);
        List<Doctor> filteredDoctors = filterDoctorByTime(doctors, amOrPm);
        return Map.of("doctors", filteredDoctors);
    }

    /**
     * Filters doctors by name and their availability during AM/PM
     * @param name Doctor's name
     * @param amOrPm Time of day: AM/PM
     * @return Map with the filtered list of doctors
     */
    public Map<String, Object> filterDoctorByNameAndTime(final String name, final String amOrPm) {
        List<Doctor> doctors = doctorRepository.findByNameLike(name);
        List<Doctor> filteredDoctors = filterDoctorByTime(doctors, amOrPm);
        return Map.of("doctors", filteredDoctors);
    }

    /**
     * Filters doctors by name and specialty
     * @param name Doctor's name
     * @param specialty Doctor's specialty
     * @return Map with filtered list of doctors
     */
    public Map<String, Object> filtersDoctorByNameAndSpecialty(final String name, final String specialty) {
        List<Doctor> doctors = doctorRepository.findByNameContainingIgnoreCaseAndSpecialtyIgnoreCase(name, specialty);
        return Map.of("doctors", doctors);
    }

    /**
     * Filters doctors by specialty
     * @param specialty Doctor's specialty
     * @return Map with filtered list of doctors
     */
    public Map<String, Object> filterDoctorBySpecialty(final String specialty) {
        List<Doctor> doctors = doctorRepository.findBySpecialtyIgnoreCase(specialty);
        return Map.of("doctors", doctors);
    }

    /**
     * Filters doctors by their availability during AM/PM
     * @param amOrPm Time of day: AM/PM
     * @return Map with filtered list of doctors
     */
    public Map<String, Object> filterDoctorsByTime(final String amOrPm) {
        List<Doctor> allDoctors = doctorRepository.findAll();
        List<Doctor> filteredDoctors = filterDoctorByTime(allDoctors, amOrPm);
        return Map.of("doctors", filteredDoctors);
    }

    /**
     * Filters a list of doctors by their availability during AM/PM
     * @param doctors List of doctors to filter
     * @param amOrPm Time of day: AM/PM
     * @return Filtered list of doctors
     */
    public List<Doctor> filterDoctorByTime(final List<Doctor> doctors, final String amOrPm) {
        return doctors.stream()
            .filter(doctor -> doctor.getAvailableTimes().stream()
                .anyMatch(time -> {
                    String startTime = time.split(" - ")[0];
                    int hour = Integer.parseInt(startTime.split(":")[0]);
                    return amOrPm.equalsIgnoreCase("AM") ? hour < 12 : hour >= 12;
                }))
            .toList();
    }
}
