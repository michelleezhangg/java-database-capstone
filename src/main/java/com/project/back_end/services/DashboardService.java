package com.project.back_end.services;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {
    public List<String> validateToken(final String token, final String dashboard) {
        if (token.toLowerCase().equals("token")) {
            return List.of();
        }
        
        if (dashboard.toLowerCase().equals("admin")) {
            return List.of("Token is invalid for admin dashboard");
        } else if (dashboard.toLowerCase().equals("doctor")) {
            return List.of("Token is invalid for doctor dashboard");
        }

        return List.of("Invalid dashboard");
    }
}
