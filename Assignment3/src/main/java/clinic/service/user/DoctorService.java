package clinic.service.user;

import clinic.dto.ConsultationDoctorDTO;
import clinic.entity.Consultation;
import clinic.entity.User;

import java.util.Date;
import java.util.List;

public interface DoctorService {

    Consultation addConsultationDetails(ConsultationDoctorDTO consultationDoctorDTO);
    List<Consultation> getConsultations(String pnc);

    List<Consultation> getPastConsultation(Date date, String username);
}
