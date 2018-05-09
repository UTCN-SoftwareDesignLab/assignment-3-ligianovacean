package clinic.repository;

import clinic.entity.Consultation;
import clinic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ConsultationRepository extends JpaRepository<Consultation, Integer> {
    List<Consultation> findByDoctorUsername(String username);
    List<Consultation> findByPatientId(Integer id);
    List<Consultation> findByDateBeforeAndDoctor(Date date, User doctor);
}
