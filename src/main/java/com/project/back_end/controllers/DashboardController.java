package com.project.back_end.controllers;

import com.project.back_end.services.DashboardService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DashboardController {
    @Autowired
    private final DashboardService dashboardService;

    @GetMapping("/adminDashboard/{token}")
    public String adminDashboard(@PathVariable String token) {
        final List<String> validationMessages = dashboardService.validateToken(token, "admin");
        if (validationMessages.isEmpty()) { // Token is valid
            return "Admin Dashboard View";
        }
        return "Redirect to login page";
    }

    @GetMapping("/doctorDashboard/{token}")
    public String doctorDashboard(@PathVariable String token) {
        final List<String> validationMessages = dashboardService.validateToken(token, "doctor");
        if (validationMessages.isEmpty()) { // Token is valid
            return "Doctor Dashboard View";
        }
        return "Redirect to login page";
    }
}
