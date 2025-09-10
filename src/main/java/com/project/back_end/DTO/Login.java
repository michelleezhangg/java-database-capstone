package com.project.back_end.DTO;

public class Login {
    private String identifier; // email for Doctor/Patient, username for Admin
    private String password;

    // Getters
    public String getIdentifier() {
        return this.identifier;
    }

    public String getPassword() {
        return this.password;
    }

    // Setters
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
