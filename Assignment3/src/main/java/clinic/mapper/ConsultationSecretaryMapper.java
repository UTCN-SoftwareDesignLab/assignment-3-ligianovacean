package clinic.mapper;

import clinic.dto.ConsultationDoctorDTO;
import clinic.dto.ConsultationSecretaryDTO;
import clinic.entity.Consultation;
import clinic.repository.ConsultationRepository;
import clinic.repository.PatientRepository;
import clinic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsultationSecretaryMapper {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    ConsultationRepository consultationRepository;

    public Consultation fromDTO(ConsultationSecretaryDTO consultationDTO) {
        Consultation consultation = new Consultation();

        consultation.setDate(consultationDTO.date);
        consultation.setDoctor(userRepository.findByUsername(consultationDTO.doctorUsername));
        consultation.setPatient(patientRepository.getOne(consultationDTO.patientId));
        if (consultationDTO.id != null) {
            consultation.setId(consultationDTO.id);
            Consultation prevConsultation = consultationRepository.getOne(consultationDTO.id);
            consultation.setDiagnosis(prevConsultation.getDiagnosis());
        }

        return consultation;
    }

    public ConsultationSecretaryDTO toDTO(Consultation consultation) {
        ConsultationSecretaryDTO consultationDTO = new ConsultationSecretaryDTO();

        consultationDTO.doctorUsername = consultation.getDoctor().getUsername();
        consultationDTO.patientId = consultation.getPatient().getId();
        consultationDTO.date = consultation.getDate();
        consultationDTO.id = consultation.getId();

        return consultationDTO;
    }

}
