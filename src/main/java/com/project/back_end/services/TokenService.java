package com.project.back_end.services;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.project.back_end.repositories.AdminRepository;
import com.project.back_end.repositories.DoctorRepository;
import com.project.back_end.repositories.PatientRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenService {
    private final AdminRepository adminRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    
    @Value("${jwt.secret:mySecretKey123456789012345678901234567890}")
    private String jwtSecret;
    
    public TokenService(AdminRepository adminRepository, DoctorRepository doctorRepository, PatientRepository patientRepository) {
        this.adminRepository = adminRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    /**
     * Generates a JWT token for a given user's identifier
     * @param identifier Unique identifier for the user (username for Admin and email for Doctor and Patient)
     * @return Generated JWT token
     */
    public String generateToken(final String identifier) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 7 * 24 * 60 * 60 * 1000); // 7 days
        
        return Jwts.builder()
            .subject(identifier)
            .issuedAt(now)
            .expiration(expiration)
            .signWith(getSigningKey())
            .compact();
    }

    /**
     * Extracts the identifier (subject) from a JWT token
     * @param token JWT token for which the identifier is to be extracted
     * @return Identifier extracted from the token
     */
    public String extractIdentifier(final String token) {
        try {
            Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
            return claims.getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Validates the JWT token for a given user type (admin, doctor, or patient)
     * @param token JWT token to be validated
     * @param user Type of user (admin, doctor, patient, etc.)
     * @return True if the token is valid for the specified user type and false if token is invalid or expired
     */
    public boolean validateToken(final String token, final String user) {
        String identifier = extractIdentifier(token);
        if (identifier == null) return false;
        
        switch (user.toLowerCase()) {
            case "admin":
                return adminRepository.findByUsername(identifier) != null;
            case "doctor":
                return doctorRepository.findByEmail(identifier) != null;
            case "patient":
                return patientRepository.findByEmail(identifier) != null;
            default:
                return false;
        }
    }
    
    /**
     * Validates the JWT token and returns the identifier if valid
     * @param token JWT token to be validated
     * @return Identifier if token is valid, null otherwise
     */
    public String validateToken(final String token) {
        return extractIdentifier(token);
    }

    /**
     * Retrieves the signing key used for JWT token signing
     * @return Key used for signing the JWT
     */
    public SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }
}
