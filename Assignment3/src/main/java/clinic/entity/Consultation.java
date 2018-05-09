package clinic.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "consultation")
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Date date;
    private String diagnosis;
    @ManyToOne(cascade = CascadeType.MERGE)
    private User doctor;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Patient patient;

    public Consultation(Integer id, Date date,User doctor, Patient patient, String diagnosis) {
        this.id = id;
        this.date = date;
        this.doctor = doctor;
        this.patient = patient;
        this.diagnosis = diagnosis;
    }

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Consultation(){
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
}
