package clinic;

import clinic.dto.UserDTO;
import clinic.entity.User;
import clinic.error.ErrorCollector;
import clinic.error.ValidationException;
import clinic.service.user.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return adminService.getAll();
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody @Valid UserDTO userDTO, BindingResult result) throws ValidationException{
        if (result.hasErrors()) {
            List<String> err = ErrorCollector.constructErrors(result);
            throw new ValidationException(ErrorCollector.errorListToString(err));
        }
        User createdUser = adminService.create(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public ResponseEntity<User> updateUser(@RequestBody @Valid UserDTO userDTO, BindingResult result) throws ValidationException{
        if (result.hasErrors()) {
            List<String> err = ErrorCollector.constructErrors(result);
            throw new ValidationException(ErrorCollector.errorListToString(err));
        }
        User updatedUser = adminService.update(userDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public ResponseEntity deleteUser(@RequestBody String username) {
        adminService.delete(username.split("=")[1]);
        return new ResponseEntity(HttpStatus.OK);
    }
}
