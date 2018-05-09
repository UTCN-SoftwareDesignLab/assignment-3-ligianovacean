package clinic.service.patient;

import clinic.dto.PatientDTO;
import clinic.entity.Patient;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface PatientService {

    List<Patient> getAll();
    Patient create(PatientDTO patientDTO);
    Patient getById(Integer id);
    Patient getByPnc(String pnc);
    Patient update(PatientDTO patientDTO);

    void delete(Integer id);
}
