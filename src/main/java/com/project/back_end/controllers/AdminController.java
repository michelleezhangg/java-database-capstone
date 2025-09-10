package com.project.back_end.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.back_end.models.Admin;
import com.project.back_end.services.ClinicService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.path}" + "admin")
@RequiredArgsConstructor
public class AdminController {
    private final ClinicService service;

    /**
     * Admin login
     * @param admin Admin credentials
     * @return Generated token based on the admin
     */
    @PostMapping()
    public ResponseEntity<Map<String, String>> adminLogin(@RequestBody final Admin admin) {
        return service.validateAdmin(admin);
    }
}
