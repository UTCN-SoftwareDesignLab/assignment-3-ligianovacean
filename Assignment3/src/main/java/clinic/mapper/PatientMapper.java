package clinic.mapper;

import clinic.dto.PatientDTO;
import clinic.entity.Patient;
import clinic.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
public class PatientMapper {

    @Autowired
    PatientRepository patientRepository;

    public Patient fromDTO(PatientDTO patientDTO) {
        Patient patient = new Patient();

        if (patientDTO.id != null) {
            patient.setId(patientDTO.id);
        }
        patient.setPnc(patientDTO.pnc);
        patient.setName(patientDTO.name);
        patient.setAddress(patientDTO.address);
        patient.setDateOfBirth(patientDTO.dateOfBirth);
        //Patient pat = patientRepository.findByPnc(patientDTO.pnc);
        //if (pat == null ) {
         //   throw new EntityNotFoundException("Patient could not be found.\n");
        //}
        //patient.setId(patientRepository.findByPnc(patientDTO.pnc).getId());

        return patient;
    }

    public PatientDTO toDTO(Patient patient) {
        PatientDTO patientDTO = new PatientDTO();

        patientDTO.name = patient.getName();
        patientDTO.pnc = patient.getPnc();
        patientDTO.address = patient.getAddress();
        patientDTO.dateOfBirth = patient.getDateOfBirth();
        patientDTO.id = patient.getId();

        return patientDTO;
    }

}
