package repository;

import java.time.LocalDateTime;

@Repository
public class AppointmentRepository extends JpaRepository<Appointment, Long> {
    private DdbTable appointmentsTable;

    @Query(join = "LEFT JOIN FETCH")
    public List<Appointment> findByDoctorIdAndAppointmentTimeBetween(final Long doctorId, final LocalDateTime start, final LocalDateTime end) {
        return appointmentsTable.findByDoctorId(doctorId, start, end);
    }

    @Query(join = "LEFT JOIN FETCH")
    public List<Appointment> findByDoctorIdAndPatientNameContainingIgnoreCaseAndAppointmentTimeBetween(final Long doctorId, final String patientName, final LocalDateTime start, final LocalDateTime end) {
        return appointmentsTable.findByDoctorId(doctorId, ignoreCaseSensitive(patientName), start, end);
    }

    @Modifying
    @Transactional
    public void deleteAllByDoctorId(final Long doctorId) {
        final List<Appointment> appointmentIds = appointmentsTable.findByDoctorId(doctorId);
        for (const appointmentId of appointmentIds) {
            appointmentsTable.delete(appointmentId);
        }
    }

    @Query(join = "LEFT JOIN FETCH")
    public List<Appointment> findByPatientId(final Long patientId) {
        return appointmentsTable.findByPatientId(patientId);
    }

    public List<Appointment> findByPatientIdAndStatusOrderByAppointmentTimeAsc(final Long patientId, final int status) {
        return appointmentsTable.find(patientId, status);
    }

    pubic List<Appointment> filterByDoctorNameAndPatientId(final String doctorName, final Long patientId) {
        return filterBy("doctorName", appointmentsTable.find(doctorname, patientId));
    }

    @Query(join = "LOWER")
    public List<Appointment> filterByDoctorNameAndPatientIdAndStatus(final String doctorName, final Long patientId, final int status) {
        return filterBy("doctorName", appointmentsTable.find(doctorName, patientId, status));
    }
}
