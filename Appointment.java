import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.concurrent.Future;

@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GeneratedType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    private Doctor doctor;

    @ManyToOne
    @NotNull
    private Patient patient;

    @Future
    private LocalDateTime appointmentTime;

    private int status; // 0 = Scheduled, 1 = Completed, 2 = Cancelled

    @Transient
    public LocalDateTime getEndTime() {
        return appointmentTime.plusHours(1);
    }

    // Getters and setters
    public Long getId() {
        return this.id;
    }

    public Doctor getDoctor() {
        return this.doctor;
    }

    public Patient getPatient() {
        return this.patient;
    }

    public LocalDateTime getAppointmentTime() {
        return this.appointmentTime;
    }

    public int getStatus() {
        return this.status;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setAppointmentTime(LocalDateTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
