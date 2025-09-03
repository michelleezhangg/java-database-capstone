# Stores Procedures

## Daily Appointment Report by Doctor
This procedure generates a report listing all appointments on a specific date, grouped by doctor. It displays the doctor's name, appointment time, appointment status, and the patient's name and phone number. This is useful for daily operational reviews in the clinic.

```{sql}
DELIMITER $$

CREATE PROCEDURE GetDailyAppointmentReportByDoctor(
    IN report_date DATE
)
BEGIN
    SELECT
        d.name AS doctor_name,
        a.appointment_time,
        a.status,
        p.name AS patient_name,
        p.phone AS patient_phone
    FROM
        appointment a
    JOIN
        doctor d on a.doctor_id = d.id
    JOIN
        patient p ON a.patient_id = p.id
    WHERE
        DATE(a.appointment_time) = report_date
    ORDER BY
        d.name, a.appointment_time;
END $$

DELIMITER ;
```

```{sql}
CALL GetDailyAppointmentReportByDoctor('2025-04-15');
```

## Doctor with Most Patients By Month
This procedure identifies the doctor who saw the most patients in a given month and year. It helps clinic managers understand which doctor had the highest patient load during a time period.

```{mysql}
DELIMITER $$

CREATE PROCEDURE GetDoctorWithMostPatientsByMonth(
    IN input_month INT,
    IN input_year INT
)
BEGIN
    SELECT
        doctor_id,
        COUNT(patient_id) AS patients_seen
    FROM
        appointment
    WHERE
        MONTH(appointment_time) = input_month
        AND YEAR (appointment_time) = input_year
    GROUP BY
        doctor_id
    ORDER BY
        patients_seen DESC
    LIMIT 1;
END $$

DELIMITER ;
```

```{mysql}
CALL GetDoctorWithMostPatientsByMonth(4, 2025);
```

## Doctor with Most Patients by Year
This procedure identifies the doctor with the most patients in a given year. It is helpful for generating annual performance summaries.

```{mysql}
DELIMITER $$

CREATE PROCEDURE GetDoctorWithMostPatientsByYear(
    IN input_year INT
)
BEGIN
    SELECT
        doctor_id,
        COUNT(patient_id) AS patients_seen
    FROM
        appointment
    WHERE
        YEAR(appointment_time) = input_year
    GROUP BY
        doctor_id
    ORDER BY
        patients_seen DESC
    LIMIT 1;
END $$

DELIMITER ;
```

```{mysql}
CALL GetDoctorWithMostPatientsByYear(2025);
```