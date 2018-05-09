package clinic.service.patient;

import clinic.dto.PatientDTO;
import clinic.entity.Patient;
import clinic.mapper.PatientMapper;
import clinic.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    PatientRepository patientRepository;
    PatientMapper patientMapper;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository, PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
    }

    @Override
    public List<Patient> getAll() {
        return patientRepository.findAll();
    }

    @Override
    public Patient create(PatientDTO patientDTO) {
        Patient patient = patientMapper.fromDTO(patientDTO);
        return patientRepository.save(patient);
    }

    @Override
    public Patient getById(Integer id) {
        return patientRepository.getOne(id);
    }

    @Override
    public Patient getByPnc(String pnc) {
        return patientRepository.findByPnc(pnc);
    }

    @Override
    public Patient update(PatientDTO patientDTO){
        Patient patient = patientMapper.fromDTO(patientDTO);
        return patientRepository.save(patient);
    }

    @Override
    public void delete(Integer id) {
        patientRepository.delete(id);
    }
}
