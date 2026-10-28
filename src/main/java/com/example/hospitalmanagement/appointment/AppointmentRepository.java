package com.example.hospitalmanagement.appointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("""
            select count(a) > 0 from Appointment a
            where a.doctor.id = :doctorId
              and a.id <> :excludedId
              and a.status <> :cancelled
              and a.startAt < :endAt
              and a.endAt > :startAt
            """)
    boolean hasDoctorConflict(
            @Param("doctorId") Long doctorId,
            @Param("startAt") LocalDateTime startAt,
            @Param("endAt") LocalDateTime endAt,
            @Param("excludedId") Long excludedId,
            @Param("cancelled") AppointmentStatus cancelled
    );

    @Query("""
            select count(a) > 0 from Appointment a
            where a.patient.id = :patientId
              and a.id <> :excludedId
              and a.status <> :cancelled
              and a.startAt < :endAt
              and a.endAt > :startAt
            """)
    boolean hasPatientConflict(
            @Param("patientId") Long patientId,
            @Param("startAt") LocalDateTime startAt,
            @Param("endAt") LocalDateTime endAt,
            @Param("excludedId") Long excludedId,
            @Param("cancelled") AppointmentStatus cancelled
    );
}
