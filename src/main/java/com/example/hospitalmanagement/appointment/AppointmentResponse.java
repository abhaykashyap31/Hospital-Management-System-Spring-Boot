package com.example.hospitalmanagement.appointment;

import java.time.LocalDateTime;

public record AppointmentResponse(
        Long id,
        Long doctorId,
        String doctorName,
        Long patientId,
        String patientName,
        LocalDateTime startAt,
        LocalDateTime endAt,
        AppointmentStatus status,
        String notes
) {
    public static AppointmentResponse from(Appointment appointment) {
        return new AppointmentResponse(
                appointment.getId(),
                appointment.getDoctor().getId(),
                appointment.getDoctor().getName(),
                appointment.getPatient().getId(),
                appointment.getPatient().getName(),
                appointment.getStartAt(),
                appointment.getEndAt(),
                appointment.getStatus(),
                appointment.getNotes()
        );
    }
}
