package com.example.hospitalmanagement.appointment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record AppointmentRequest(
        @NotNull Long doctorId,
        @NotNull Long patientId,
        @NotNull LocalDateTime startAt,
        @NotNull LocalDateTime endAt,
        AppointmentStatus status,
        @Size(max = 500) String notes
) {
}
