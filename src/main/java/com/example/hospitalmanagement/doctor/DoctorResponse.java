package com.example.hospitalmanagement.doctor;

public record DoctorResponse(
        Long id,
        String name,
        String specialization,
        String email,
        String phone
) {
    public static DoctorResponse from(Doctor doctor) {
        return new DoctorResponse(
                doctor.getId(),
                doctor.getName(),
                doctor.getSpecialization(),
                doctor.getEmail(),
                doctor.getPhone()
        );
    }
}
