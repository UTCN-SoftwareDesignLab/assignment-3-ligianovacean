package clinic.service.user;

import clinic.dto.UserDTO;
import clinic.entity.User;
import clinic.mapper.UserMapper;
import clinic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    UserRepository userRepository;
    UserMapper userMapper;

    @Autowired
    public AdminServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User create(UserDTO userDTO) {
        User user = userMapper.fromDTO(userDTO);
        user.setPassword(new ShaPasswordEncoder().encodePassword(user.getPassword(), null));
        return userRepository.save(user);
    }

    @Override
    public User update(UserDTO userDTO) {
        userDTO.role = userRepository.findByUsername(userDTO.username).getRole();
        userDTO.password = new ShaPasswordEncoder().encodePassword(userDTO.password, null);
        User user = userMapper.fromDTO(userDTO);
        return userRepository.save(user);
    }

    @Override
    public void delete(String username) {
        userRepository.delete(username);
    }
}
