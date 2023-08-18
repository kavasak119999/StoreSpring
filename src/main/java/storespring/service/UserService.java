package storespring.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import storespring.dto.User;

import java.util.Properties;

public interface UserService extends UserDetailsService {
    Properties register(User user);
}