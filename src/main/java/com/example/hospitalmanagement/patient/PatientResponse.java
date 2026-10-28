package com.example.hospitalmanagement.patient;

import java.time.LocalDate;

public record PatientResponse(
        Long id,
        String name,
        LocalDate dateOfBirth,
        String email,
        String phone
) {
    public static PatientResponse from(Patient patient) {
        return new PatientResponse(
                patient.getId(),
                patient.getName(),
                patient.getDateOfBirth(),
                patient.getEmail(),
                patient.getPhone()
        );
    }
}
