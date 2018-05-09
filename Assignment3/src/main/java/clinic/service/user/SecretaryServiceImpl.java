package clinic.service.user;

import clinic.dto.ConsultationSecretaryDTO;
import clinic.dto.PatientDTO;
import clinic.entity.Consultation;
import clinic.entity.Patient;
import clinic.entity.User;
import clinic.mapper.ConsultationSecretaryMapper;
import clinic.repository.UserRepository;
import clinic.service.consultation.ConsultationService;
import clinic.service.patient.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SecretaryServiceImpl implements SecretaryService {

    UserRepository userRepository;
    PatientService patientService;
    ConsultationService consultationService;
    ConsultationSecretaryMapper consultationSecretaryMapper;

    @Autowired
    public SecretaryServiceImpl(PatientService patientService, ConsultationService consultationService, ConsultationSecretaryMapper consultationSecretaryMapper, UserRepository userRepository) {
        this.patientService = patientService;
        this.consultationService = consultationService;
        this.consultationSecretaryMapper = consultationSecretaryMapper;
        this.userRepository = userRepository;
    }

    @Override
    public Patient createPatient(PatientDTO patientDTO) {
        return patientService.create(patientDTO);
    }

    @Override
    public Patient updatePatient(PatientDTO patientDTO) {
        return patientService.update(patientDTO);
    }

    @Override
    public List<Patient> getPatients() {
        return patientService.getAll();
    }

    @Override
    public Consultation createConsultation(ConsultationSecretaryDTO consultationSecretaryDTO) {
        return consultationService.create(consultationSecretaryDTO);
    }

    @Override
    public List<Consultation> getPatientConsultations(String pnc) {
        return consultationService.getByPatient(pnc);
    }

    @Override
    public Consultation updateConsultation(ConsultationSecretaryDTO consultationDTO) {
        Consultation consultation = consultationSecretaryMapper.fromDTO(consultationDTO);
        return consultationService.update(consultation);
    }

    @Override
    public void deleteConsultation(Integer id) {
        consultationService.delete(id);
    }

    @Override
    public Consultation getConsultationsById(Integer id){
        return consultationService.getById(id);
    }

    @Override
    public List<User> getDoctors() {
        return userRepository.findByRole("DOCTOR");
    }
}
