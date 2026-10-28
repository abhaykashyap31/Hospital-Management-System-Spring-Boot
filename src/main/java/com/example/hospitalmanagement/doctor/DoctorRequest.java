package com.example.hospitalmanagement.doctor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DoctorRequest(
        @NotBlank @Size(max = 120) String name,
        @NotBlank @Size(max = 100) String specialization,
        @NotBlank @Email @Size(max = 160) String email,
        @NotBlank @Size(max = 30) String phone
) {
}
