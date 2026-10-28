package com.example.hospitalmanagement.doctor;

import com.example.hospitalmanagement.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Transactional(readOnly = true)
    public List<DoctorResponse> findAll() {
        return doctorRepository.findAll().stream().map(DoctorResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public DoctorResponse findById(Long id) {
        return DoctorResponse.from(getDoctor(id));
    }

    public DoctorResponse create(DoctorRequest request) {
        Doctor doctor = new Doctor(
                request.name(), request.specialization(), request.email(), request.phone());
        return DoctorResponse.from(doctorRepository.save(doctor));
    }

    public DoctorResponse update(Long id, DoctorRequest request) {
        Doctor doctor = getDoctor(id);
        doctor.setName(request.name());
        doctor.setSpecialization(request.specialization());
        doctor.setEmail(request.email());
        doctor.setPhone(request.phone());
        return DoctorResponse.from(doctorRepository.save(doctor));
    }

    public void delete(Long id) {
        doctorRepository.delete(getDoctor(id));
    }

    private Doctor getDoctor(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found: " + id));
    }
}
