package clinic.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

public class PatientDTO {

    public Integer id;

    @Size(min = 1, message="Patient name cannot be null.\n")
    public String name;

    @Pattern(regexp = "[0-9]+", message = "PNC must contain only letters.\n")
    @Size(min = 5, max = 15, message = "PNC must contain between 5 and 15 characters")
    public String pnc;

    public Date dateOfBirth;

    @Size(min=1, message = "Address cannot be null.\n")
    public String address;
}
