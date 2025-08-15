# Schema Design

## MySQL Database Design

### Table: patients
- id: INT, PRIMARY KEY, AUTO_INCREMENT
- first_name: VARCHAR(20), NOT NULL
- last_name: VARCHAR(20), NOT NULL
- age: INT, NOT NULL
- appointments: LIST OF Foreign Key -> appointments(id)
- doctor_id: INT, Foreign Key -> doctors(id)

### Table: doctors
- id: INT, PRIMARY KEY, AUTO_INCREMENT
- specialty: VARCHAR(50)
- patients: LIST OF Foreign Key -> patients(id)

### Table: appointments
- id: INT, PRIMARY KEY, AUTO_INCREMENT
- doctor_id: INT, Foreign Key -> doctors(id)
- patient_id: INT, Foreign Key -> patient(id)
- appointment_time: DATETIME, NOT NULL
- status: INT (0 = Scheduled, 1 = Completed, 2 = Cancelled)

### Table: admin
- id: INT, PRIMARY KEY, AUTO_INCREMENT
- username: VARCHAR(20), NOT NULL, UNIQUE
- password: VARCHAR(20), NOT NULL, UNIQUE
- email: VARCHAR(50), NOT NULL, UNIQUE
- doctors: LIST OF Foreign Key -> doctors(id)

## MongoDB Collection Design

### Collection: prescriptions

```json
{
    "_id": "ObjectId('64abc123456')",
    "patientName": "John Smith",
    "appointmentId": 51,
    "medication": "Paracetamol",
    "dosage": "500mg",
    "doctorNotes": "Take 1 tablet every 6 hours",
    "refillCount": 2,
    "pharmacy": {
        "name": "Walgreens SF",
        "location": "Market Street",
    }
}
```

### Collections: logs

```json
{
    "_id": "ObjectId('23abc123456')",
    "event_code": "Appointment.getPrescription",
    "message": "Getting patient with ID: ID:123456 prescription: 64abc123456",
    "request_id": "ID:12"
}
```