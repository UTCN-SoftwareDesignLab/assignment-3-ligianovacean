package clinic.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

public class ConsultationDoctorDTO {

    public Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    public Date date;

    @NotNull(message = "Diagnosis cannot be null.\n")
    public String diagnosis;
}
