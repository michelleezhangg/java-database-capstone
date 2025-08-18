package model;
@Document(collection = "prescription")
public class Prescription {
    @Id
    private String id;

    @NotNull
    @Size(min = 3, max = 100)
    private String patientName;

    @NotNull
    private Long appointmentId;

    @NotNull
    @Size(min = 3, max = 100)
    private String medication;

    @Size(min = 3, max = 100)
    private String doctorsNotes;
}
