package clinic;


import clinic.config.Message;
import clinic.dto.ConsultationSecretaryDTO;
import clinic.dto.PatientDTO;
import clinic.entity.Consultation;
import clinic.entity.Patient;
import clinic.error.ErrorCollector;
import clinic.error.ValidationException;
import clinic.service.consultation.ConsultationService;
import clinic.service.patient.PatientService;
import clinic.service.user.SecretaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class SecretaryController {

    @Autowired
    SecretaryService secretaryService;
    @Autowired
    ConsultationService consultationService;
    @Autowired
    PatientService patientService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @RequestMapping(value = "/patients", method = RequestMethod.GET)
    public List<Patient> getAllPatients(){
        return secretaryService.getPatients();
    }

    @RequestMapping(value = "/consultations", method = RequestMethod.GET)
    public List<Consultation> getAllConsultations() {
        return consultationService.getAll();}

    @RequestMapping(value = "/createPatient", method = RequestMethod.POST)
    public ResponseEntity<Patient> createPatient(@RequestBody @Valid PatientDTO patientDTO, BindingResult result) throws ValidationException {
        if (result.hasErrors()) {
            List<String> err = ErrorCollector.constructErrors(result);
            throw new ValidationException(ErrorCollector.errorListToString(err));
        }
        Patient createdPatient = secretaryService.createPatient(patientDTO);
        return new ResponseEntity<>(createdPatient, HttpStatus.OK);
    }

    @RequestMapping(value = "/updatePatient", method = RequestMethod.POST)
    public ResponseEntity<Patient> updatePatient(@RequestBody @Valid PatientDTO patientDTO, BindingResult result) throws ValidationException {
        if (result.hasErrors()) {
            List<String> err = ErrorCollector.constructErrors(result);
            throw new ValidationException(ErrorCollector.errorListToString(err));
        }
        Patient updatedPatient = secretaryService.updatePatient(patientDTO);
        return new ResponseEntity<>(updatedPatient, HttpStatus.OK);
    }

    @RequestMapping(value = "/createConsultation", method = RequestMethod.POST)
    public ResponseEntity<Consultation> createConsultation(@RequestBody ConsultationSecretaryDTO consultationDTO) {
        Consultation createdConsultation = secretaryService.createConsultation(consultationDTO);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedInSecretary = auth.getName();
        Message message = new Message();
        message.setContent("Secretary " + loggedInSecretary + " added consultation for patient " +
                patientService.getById(consultationDTO.patientId).getName().toUpperCase() + " on date " + consultationDTO.date);
        messagingTemplate.convertAndSendToUser(consultationDTO.doctorUsername, "/queue/reply", message);

        return new ResponseEntity<>(createdConsultation, HttpStatus.OK);
    }

    @RequestMapping(value = "/updateConsultation", method = RequestMethod.POST)
    public ResponseEntity<Consultation> updateConsultation(@RequestBody ConsultationSecretaryDTO consultationDTO) {
        Consultation updateConsultation = secretaryService.updateConsultation(consultationDTO);
        return new ResponseEntity<>(updateConsultation, HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteConsultation", method = RequestMethod.POST)
    public ResponseEntity deleteConsultation(@RequestBody Integer id) {
        secretaryService.deleteConsultation(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/checkIn", method = RequestMethod.POST)
    public ResponseEntity checkIn(@RequestBody ConsultationSecretaryDTO consultationSecretaryDTO){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Integer id = consultationSecretaryDTO.id;
        String loggedInSecretary = auth.getName();
        Message message = new Message();
        message.setContent("Patient " + consultationService.getById(id).getPatient().getName() +
                " was checked-in for the " + consultationService.getById(id).getDate() + " appointment.");
        messagingTemplate.convertAndSendToUser(consultationService.getById(id).getDoctor().getUsername(), "/queue/reply", message);
        return new ResponseEntity(HttpStatus.OK);
    }

}
