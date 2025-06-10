package Undertaker.HospiBook.repository;

import Undertaker.HospiBook.model.entities.Hospital;
import Undertaker.HospiBook.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
}
