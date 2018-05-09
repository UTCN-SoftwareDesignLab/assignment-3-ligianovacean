package clinic.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

public class ConsultationSecretaryDTO {

    public Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    public Date date;

    @Size(min = 8, message = "Username too short.\n")
    public String doctorUsername;

    @Min(value = 0, message = "Patient id must be a positive number.\n")
    public Integer patientId;

}
