package com.example.hospitalmanagement.appointment;

import com.example.hospitalmanagement.doctor.Doctor;
import com.example.hospitalmanagement.doctor.DoctorRepository;
import com.example.hospitalmanagement.exception.ResourceNotFoundException;
import com.example.hospitalmanagement.patient.Patient;
import com.example.hospitalmanagement.patient.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public AppointmentService(
            AppointmentRepository appointmentRepository,
            DoctorRepository doctorRepository,
            PatientRepository patientRepository
    ) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> findAll() {
        return appointmentRepository.findAll().stream().map(AppointmentResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public AppointmentResponse findById(Long id) {
        return AppointmentResponse.from(getAppointment(id));
    }

    public AppointmentResponse create(AppointmentRequest request) {
        Doctor doctor = getDoctor(request.doctorId());
        Patient patient = getPatient(request.patientId());
        validateTimeRange(request);
        validateAvailability(doctor.getId(), patient.getId(), request, null);

        Appointment appointment = new Appointment(
                doctor,
                patient,
                request.startAt(),
                request.endAt(),
                statusOrDefault(request.status()),
                request.notes()
        );
        return AppointmentResponse.from(appointmentRepository.save(appointment));
    }

    public AppointmentResponse update(Long id, AppointmentRequest request) {
        Appointment appointment = getAppointment(id);
        Doctor doctor = getDoctor(request.doctorId());
        Patient patient = getPatient(request.patientId());
        validateTimeRange(request);
        validateAvailability(doctor.getId(), patient.getId(), request, id);

        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setStartAt(request.startAt());
        appointment.setEndAt(request.endAt());
        appointment.setStatus(statusOrDefault(request.status()));
        appointment.setNotes(request.notes());
        return AppointmentResponse.from(appointmentRepository.save(appointment));
    }

    public void delete(Long id) {
        appointmentRepository.delete(getAppointment(id));
    }

    private Appointment getAppointment(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found: " + id));
    }

    private Doctor getDoctor(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found: " + id));
    }

    private Patient getPatient(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found: " + id));
    }

    private void validateTimeRange(AppointmentRequest request) {
        if (!request.endAt().isAfter(request.startAt())) {
            throw new IllegalArgumentException("endAt must be after startAt");
        }
    }

    private void validateAvailability(
            Long doctorId,
            Long patientId,
            AppointmentRequest request,
            Long appointmentId
    ) {
        if (request.status() == AppointmentStatus.CANCELLED) {
            return;
        }
        long excludedId = appointmentId == null ? 0L : appointmentId;
        if (appointmentRepository.hasDoctorConflict(
                doctorId, request.startAt(), request.endAt(), excludedId, AppointmentStatus.CANCELLED)) {
            throw new IllegalArgumentException("Doctor already has an appointment during this time");
        }
        if (appointmentRepository.hasPatientConflict(
                patientId, request.startAt(), request.endAt(), excludedId, AppointmentStatus.CANCELLED)) {
            throw new IllegalArgumentException("Patient already has an appointment during this time");
        }
    }

    private AppointmentStatus statusOrDefault(AppointmentStatus status) {
        return status == null ? AppointmentStatus.SCHEDULED : status;
    }
}
