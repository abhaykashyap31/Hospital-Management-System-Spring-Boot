package com.example.hospitalmanagement;

import com.example.hospitalmanagement.appointment.AppointmentRepository;
import com.example.hospitalmanagement.doctor.Doctor;
import com.example.hospitalmanagement.doctor.DoctorRepository;
import com.example.hospitalmanagement.patient.Patient;
import com.example.hospitalmanagement.patient.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AppointmentApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    private Doctor doctor;
    private Patient firstPatient;
    private Patient secondPatient;

    @BeforeEach
    void setUp() {
        appointmentRepository.deleteAll();
        patientRepository.deleteAll();
        doctorRepository.deleteAll();

        doctor = doctorRepository.save(new Doctor(
                "Jordan Lee", "Family Medicine", "jordan.lee@example.com", "555-0101"));
        firstPatient = patientRepository.save(new Patient(
                "Sam Rivera", LocalDate.of(1990, 4, 12), "sam.rivera@example.com", "555-0102"));
        secondPatient = patientRepository.save(new Patient(
                "Taylor Morgan", LocalDate.of(1985, 8, 3), "taylor.morgan@example.com", "555-0103"));
    }

    @Test
    void createsAppointmentAndRejectsOverlappingDoctorSchedule() throws Exception {
        mockMvc.perform(post("/api/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(appointmentJson(firstPatient.getId())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.status", is("SCHEDULED")));

        mockMvc.perform(post("/api/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(appointmentJson(secondPatient.getId())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.message",
                        is("Doctor already has an appointment during this time")));
    }

    private String appointmentJson(Long patientId) {
        return """
                {
                  "doctorId": %d,
                  "patientId": %d,
                  "startAt": "2026-10-27T10:00:00",
                  "endAt": "2026-10-27T10:30:00",
                  "notes": "Routine consultation"
                }
                """.formatted(doctor.getId(), patientId);
    }
}
