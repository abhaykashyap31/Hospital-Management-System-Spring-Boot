package com.example.hospitalmanagement.patient;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PatientRequest(
        @NotBlank @Size(max = 120) String name,
        @NotNull @Past LocalDate dateOfBirth,
        @NotBlank @Email @Size(max = 160) String email,
        @NotBlank @Size(max = 30) String phone
) {
}
