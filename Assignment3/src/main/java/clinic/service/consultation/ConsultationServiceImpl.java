package clinic.service.consultation;

import clinic.dto.ConsultationDoctorDTO;
import clinic.dto.ConsultationSecretaryDTO;
import clinic.entity.Consultation;
import clinic.entity.Patient;
import clinic.entity.User;
import clinic.mapper.ConsultationSecretaryMapper;
import clinic.repository.ConsultationRepository;
import clinic.repository.UserRepository;
import clinic.service.patient.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ConsultationServiceImpl implements ConsultationService {

    ConsultationRepository consultationRepository;
    UserRepository userRepository;
    ConsultationSecretaryMapper consultationSecretaryMapper;
    PatientService patientService;

    @Autowired
    public ConsultationServiceImpl(ConsultationRepository consultationRepository, ConsultationSecretaryMapper consultationSecretaryMapper,
                                   PatientService patientService, UserRepository userRepository) {
        this.consultationRepository = consultationRepository;
        this.consultationSecretaryMapper = consultationSecretaryMapper;
        this.patientService = patientService;
        this.userRepository = userRepository;
    }

    @Override
    public List<Consultation> getAll() {
        return consultationRepository.findAll();
    }

    @Override
    public Consultation create(ConsultationSecretaryDTO consultationDTO) {
        Consultation consultation = consultationSecretaryMapper.fromDTO(consultationDTO);
        return consultationRepository.save(consultation);
    }

    @Override
    public Consultation getById(Integer id) {
        return consultationRepository.getOne(id);
    }

    @Override
    public List<Consultation> getByDoctor(String doctorUsername) {
        return consultationRepository.findByDoctorUsername(doctorUsername);
    }

    @Override
    public List<Consultation> getByPatient(String pnc) {
        Patient patient = patientService.getByPnc(pnc);
        return consultationRepository.findByPatientId(patient.getId());
    }

    @Override
    public Consultation update(Consultation consultation) {
        return consultationRepository.save(consultation);
    }

    @Override
    public void delete(Integer id) {
        consultationRepository.delete(id);
    }

    @Override
    public List<Consultation> getPastConsultations(Date date, String username) {
        User doctor = userRepository.findByUsername(username);
        return consultationRepository.findByDateBeforeAndDoctor(date, doctor);
    }
}
