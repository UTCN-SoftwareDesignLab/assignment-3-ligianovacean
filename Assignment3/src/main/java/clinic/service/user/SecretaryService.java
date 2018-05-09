package clinic.service.user;

import clinic.dto.ConsultationSecretaryDTO;
import clinic.dto.PatientDTO;
import clinic.entity.Consultation;
import clinic.entity.Patient;
import clinic.entity.User;

import java.util.List;

public interface SecretaryService {

    Patient createPatient(PatientDTO patientDTO);
    Patient updatePatient(PatientDTO patientDTO);
    List<Patient> getPatients();
    Consultation createConsultation(ConsultationSecretaryDTO consultationSecretaryDTO);
    List<Consultation> getPatientConsultations(String pnc);
    Consultation updateConsultation(ConsultationSecretaryDTO consultationDTO);
    void deleteConsultation(Integer id);
    Consultation getConsultationsById(Integer id);
    List<User> getDoctors();
}
