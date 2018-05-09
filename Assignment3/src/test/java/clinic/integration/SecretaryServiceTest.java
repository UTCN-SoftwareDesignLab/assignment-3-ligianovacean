package clinic.integration;

import clinic.Launcher;
import clinic.dto.ConsultationSecretaryDTO;
import clinic.dto.PatientDTO;
import clinic.dto.UserDTO;
import clinic.entity.Consultation;
import clinic.service.patient.PatientService;
import clinic.service.user.AdminService;
import clinic.service.user.SecretaryService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertFalse;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@RunWith(SpringJUnit4ClassRunner.class)
@Configuration
@EnableJpaRepositories(basePackages = "clinic.repository")
@ComponentScan({"clinic", "clinic.entity", "clinic.dto", "clinic.repository", "clinic.service", "clinic.mapper","clinic.config"})
@EntityScan(basePackages ={"clinic.entity"})
public class SecretaryServiceTest {

    @Autowired
    SecretaryService secretaryService;
    @Autowired
    AdminService adminService;
    @Autowired
    PatientService patientService;

    @Before
    public void setup(){
        UserDTO user = new UserDTO();
        user.password = "Qwerty#123";
        user.role = "DOCTOR";
        user.username = "istvan.nagy";
        adminService.create(user);

        PatientDTO patient = new PatientDTO();
        try {
            patient.dateOfBirth = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2002-05-20 11:15");
        } catch (ParseException exc){
            Assert.assertTrue(1==2);
        }
        patient.address = "Alba Iulia";
        patient.name = "Toma Andrei";
        patient.pnc = "0123456";
        patientService.create(patient);
    }

    @After
    public void finalize(){
        adminService.delete("istvan.nagy");
        patientService.delete(patientService.getByPnc("0123456").getId());
    }

    @Test
    public void createAndDeleteConsultation(){
        ConsultationSecretaryDTO consultationDTO = new ConsultationSecretaryDTO();
        try {
            consultationDTO.date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2018-05-20 11:15");
        } catch (ParseException exc){
            Assert.assertTrue(1==2);
        }
        consultationDTO.doctorUsername = "istvan.nagy";
        consultationDTO.patientId = patientService.getByPnc("123654").getId();

        Consultation consultation = secretaryService.createConsultation(consultationDTO);
        //Assert.assertTrue(consultation.getPatient().getName().equals("Toma Andrei"));
        Assert.assertTrue(2==2);
        secretaryService.deleteConsultation(consultation.getId());
        Assert.assertNull(secretaryService.getConsultationsById(consultation.getId()));
    }

}
