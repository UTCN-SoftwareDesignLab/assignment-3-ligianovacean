package clinic;

import clinic.dto.ConsultationDoctorDTO;
import clinic.entity.Consultation;
import clinic.error.ErrorCollector;
import clinic.error.ValidationException;
import clinic.service.user.DoctorService;
import org.hibernate.boot.model.relational.SimpleAuxiliaryDatabaseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RestController
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    @RequestMapping(value = "/docConsultations", method = RequestMethod.GET)
    public List<Consultation> getPastConsultations() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedInDoctor = auth.getName();

        return doctorService.getPastConsultation(new Date(), loggedInDoctor);
    }

    @RequestMapping(value = "/updateDetails", method = RequestMethod.POST)
    public ResponseEntity<Consultation> updateConsultation(@RequestBody @Valid ConsultationDoctorDTO consultationDoctorDTO, BindingResult result) throws ValidationException {
        if (result.hasErrors()) {
            List<String> err = ErrorCollector.constructErrors(result);
            throw new ValidationException(ErrorCollector.errorListToString(err));
        }
        Consultation updatedConsultation = doctorService.addConsultationDetails(consultationDoctorDTO);
        return new ResponseEntity<>(updatedConsultation, HttpStatus.OK);
    }

}
