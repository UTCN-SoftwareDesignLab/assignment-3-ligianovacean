package clinic.service.user;

import clinic.dto.UserDTO;
import clinic.entity.User;

import java.util.List;

public interface AdminService {

    List<User> getAll();
    User getByUsername(String username);
    User create(UserDTO userDTO);
    User update(UserDTO userDTO);
    void delete(String username);
}
