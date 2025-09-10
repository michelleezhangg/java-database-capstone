package com.project.back_end.models;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Patient model represents user who book appointments and receive treatment.
 * - It captures personal details like contact information and address, and links to appointments and prescriptions indirectly.
 */
@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 100)
    private String name;

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

    @NotNull
    @Size(max = 255)
    private String address;

    @NotNull
    @Past
    private LocalDate dateOfBirth;

    @NotNull
    @Size(max = 255)
    @JsonIgnore
    private String emergencyContact;

    @NotNull
    @Size(max = 255)
    private String insuranceProvider;

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
     * Get Email
     * @return email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Get Password
     * @return password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Get Phone
     * @return phone
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * Get Address
     * @return address
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * Get Date of Birth
     * @return dateOfBirth
     */
    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    /**
     * Get Emergency Contact
     * @return emergencyContact
     */
    public String getEmergencyContact() {
        return this.emergencyContact;
    }

    /**
     * Get Insurance Provider
     * @return insuranceProvider
     */
    public String getInsuranceProvider() {
        return this.insuranceProvider;
    }

    /**
     * Set Name
     * @param name Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set Email
     * @param email Email
     */
    public void setEmail(String email) {
        this.email = email;
    }

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
     * Set Address
     * @param address Address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Set Date Of Birth
     * @param dateOfBirth Date Of Birth
     */
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Set Emergency Contact
     * @param emergencyContact Emergency Contact
     */
    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    /**
     * Set Insurance Provider
     * @param insuranceProvider Insurance Provider
     */
    public void setInsuranceProvider(String insuranceProvider) {
        this.insuranceProvider = insuranceProvider;
    }
}
