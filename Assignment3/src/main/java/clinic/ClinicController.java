package clinic;

import clinic.service.consultation.ConsultationService;
import clinic.service.patient.PatientService;
import clinic.service.user.AdminService;
import clinic.service.user.DoctorService;
import clinic.service.user.SecretaryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
public class ClinicController {

    @Autowired
    AdminService userService;
    @Autowired
    PatientService patientService;
    @Autowired
    ConsultationService consultationService;
    @Autowired
    SecretaryServiceImpl secretaryService;
    @Autowired
    DoctorService doctorService;

    @RequestMapping("/admin")
    String admin(Model model){
        model.addAttribute("user", userService.getAll());
        model.addAttribute("role", Constants.roles);
        return "admin";
    }

    @RequestMapping("/secretary")
    String secretary(Model model) {
        model.addAttribute("secretaryPatients", patientService.getAll());
        model.addAttribute("secretaryConsultations", consultationService.getAll());
        model.addAttribute("doctors", secretaryService.getDoctors());
        return "secretary";
    }

    @RequestMapping("/doctor")
    String doctor(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedInDoctor = auth.getName();
        model.addAttribute("doctorConsultations", doctorService.getPastConsultation(new Date(), loggedInDoctor));
        model.addAttribute("doctorPatients", patientService.getAll());
        return "doctor";
    }

}
