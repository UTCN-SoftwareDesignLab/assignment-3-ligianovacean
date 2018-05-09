package clinic.mapper;

import clinic.dto.ConsultationDoctorDTO;
import clinic.entity.Consultation;
import clinic.repository.ConsultationRepository;
import clinic.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsultationDoctorMapper {

    @Autowired
    PatientRepository patientRepository;
    @Autowired
    ConsultationRepository consultationRepository;

    public Consultation fromDTO(ConsultationDoctorDTO consultationDTO) {
        Consultation consultation = new Consultation();

        consultation.setId(consultationDTO.id);
        consultation.setDiagnosis(consultationDTO.diagnosis);

        Consultation prevConsultation = consultationRepository.getOne(consultationDTO.id);
        consultation.setPatient(prevConsultation.getPatient());
        consultation.setDoctor(prevConsultation.getDoctor());
        consultation.setDate(prevConsultation.getDate());

        return  consultation;
    }

    public ConsultationDoctorDTO toDTO(Consultation consultation) {
        ConsultationDoctorDTO consultationDoctorDTO = new ConsultationDoctorDTO();

        consultationDoctorDTO.diagnosis = consultation.getDiagnosis();
        consultationDoctorDTO.id = consultation.getId();

        return consultationDoctorDTO;
    }

}
