package clinic.mapper;

import clinic.dto.UserDTO;
import clinic.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User fromDTO(UserDTO userDTO){
        User user = new User();

        byte enabled = 1;
        user.setEnabled(enabled);
        user.setUsername(userDTO.username);
        user.setPassword(userDTO.password);
        user.setRole(userDTO.role);

        return user;
    }

    public UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.password = user.getPassword();
        userDTO.role = user.getRole();
        userDTO.username = user.getUsername();

        return userDTO;
    }

}
