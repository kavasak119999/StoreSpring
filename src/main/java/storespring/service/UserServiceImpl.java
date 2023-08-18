package storespring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import storespring.dto.User;
import storespring.entity.RoleEntity;
import storespring.entity.UserEntity;
import storespring.repository.RoleRepository;
import storespring.repository.UserRepository;
import storespring.tools.DtoToEntity;

import java.util.Optional;
import java.util.Properties;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public Properties register(User user) {
        Properties properties = new Properties();
        if (userRepository.findById(user.getUsername()).isPresent()) {
            properties.setProperty("username", "Вказаний логін вже існує");
            return properties;
        }

        RoleEntity roleUser = roleRepository.findByName("ROLE_USER");

        userRepository.save(DtoToEntity.userDtoToEntity(user, roleUser));
        return properties;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Optional<UserEntity> optionalUserEntity = userRepository
                .findById(id);
        if (!optionalUserEntity.isPresent()) {
            throw new UsernameNotFoundException(
                    "User with id: " + id + " not found");
        } else {
            return optionalUserEntity.get();
        }
    }

}