package javatechnology.lab9.service;

import javatechnology.lab9.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    List<User> getAllUser();
    User updateUser(User user);
    void removeUserById(Long id);
    Optional<User> getUserById(Long id);
    Optional<User> getUserByUsername(String username);
    Optional<User> getUserByEmail(String email);
    User saveUser(User user);

}