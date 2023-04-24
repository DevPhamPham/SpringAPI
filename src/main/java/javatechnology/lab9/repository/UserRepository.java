package javatechnology.lab9.repository;

import javatechnology.lab9.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByLastName(String lname);
    Optional<User> findByFirstName(String fname);
}