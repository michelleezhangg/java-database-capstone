package com.project.back_end.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Doctor model stores information about healthcare providers, including their contact details, medical specialty, and availability.
 * - This model is crucial for mapping appointments and verifying doctor credentials.
 */
@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 100)
    private String name;

    @NotNull
    @Size(min = 3, max = 50)
    private String specialty; // Medical specialty

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 6)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotNull
    @Size(min = 10, max = 10)
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phone;

    @ElementCollection
    private List<String> availableTimes; // Example: "09:00 - 10:00"

    /**
     * Get Id
     * @return id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Get Name
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get Specialty
     * @return specialty
     */
    public String getSpecialty() {
        return this.specialty;
    }

    /**
     * Get Email
     * @return email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Get Phone
     * @return phone
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * Get Available Times
     * @return availableTimes
     */
    public List<String> getAvailableTimes() {
        return this.availableTimes;
    }

    /**
     * Set Name
     * @param name Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set Specialty
     * @param specialty Specialty
     */
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    /**
     * Set Email
     * @param email Email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Set Password
     * @param password Password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Set Phone
     * @param phone Phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Set Available Times
     * @param availableTimes Available Times
     */
    public void setAvailableTimes(List<String> availableTimes) {
        this.availableTimes = availableTimes;
    }
}
