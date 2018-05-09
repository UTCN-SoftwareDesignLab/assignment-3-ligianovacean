package clinic.service.user;

import clinic.dto.ConsultationDoctorDTO;
import clinic.entity.Consultation;
import clinic.entity.User;
import clinic.mapper.ConsultationDoctorMapper;
import clinic.repository.UserRepository;
import clinic.service.consultation.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    ConsultationService consultationService;
    ConsultationDoctorMapper consultationDoctorMapper;

    @Autowired
    public DoctorServiceImpl(ConsultationService consultationService, ConsultationDoctorMapper consultationDoctorMapper) {
        this.consultationService = consultationService;
        this.consultationDoctorMapper = consultationDoctorMapper;
    }

    @Override
    public Consultation addConsultationDetails(ConsultationDoctorDTO consultationDoctorDTO) {
        Consultation consultation = consultationDoctorMapper.fromDTO(consultationDoctorDTO);
        return consultationService.update(consultation);
    }

    @Override
    public List<Consultation> getConsultations(String pnc) {
        return consultationService.getByPatient(pnc);
    }

    @Override
    public List<Consultation> getPastConsultation(Date date, String username) {
        return consultationService.getPastConsultations(date, username);
    }
}
