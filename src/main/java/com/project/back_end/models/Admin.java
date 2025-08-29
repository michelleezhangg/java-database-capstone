package com.project.back_end.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Admin model represents system administrators who have access to manage the backend portal of the Clinic Management System.
 * - Admins usually handle high-level operations such as user access, data review, and system maintenance.
 * - This model contains basic logic credentials required to authenticate an admin.
 */
@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "username cannot be null")
    private String username;

    @NotNull(message = "password cannot be null")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /**
     * Get Id
     * @return id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Get Username
     * @return username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Set Username
     * @param username Username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Set Password
     * @param password Password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
