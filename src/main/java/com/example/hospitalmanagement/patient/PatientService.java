package com.example.hospitalmanagement.patient;

import com.example.hospitalmanagement.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Transactional(readOnly = true)
    public List<PatientResponse> findAll() {
        return patientRepository.findAll().stream().map(PatientResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public PatientResponse findById(Long id) {
        return PatientResponse.from(getPatient(id));
    }

    public PatientResponse create(PatientRequest request) {
        Patient patient = new Patient(
                request.name(), request.dateOfBirth(), request.email(), request.phone());
        return PatientResponse.from(patientRepository.save(patient));
    }

    public PatientResponse update(Long id, PatientRequest request) {
        Patient patient = getPatient(id);
        patient.setName(request.name());
        patient.setDateOfBirth(request.dateOfBirth());
        patient.setEmail(request.email());
        patient.setPhone(request.phone());
        return PatientResponse.from(patientRepository.save(patient));
    }

    public void delete(Long id) {
        patientRepository.delete(getPatient(id));
    }

    private Patient getPatient(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found: " + id));
    }
}
