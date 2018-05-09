package clinic.service.consultation;

import clinic.dto.ConsultationDoctorDTO;
import clinic.dto.ConsultationSecretaryDTO;
import clinic.entity.Consultation;
import clinic.entity.User;

import java.util.Date;
import java.util.List;

public interface ConsultationService {

    List<Consultation> getAll();
    Consultation create(ConsultationSecretaryDTO consultationDTO);
    Consultation getById(Integer id);
    List<Consultation> getByDoctor(String doctorUsername);
    List<Consultation> getByPatient(String pnc);
    Consultation update(Consultation consultation);
    void delete(Integer id);
    List<Consultation> getPastConsultations(Date date, String doctor);
}
