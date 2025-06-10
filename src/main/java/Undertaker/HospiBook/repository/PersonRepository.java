package Undertaker.HospiBook.repository;

import Undertaker.HospiBook.model.entities.Person;
import Undertaker.HospiBook.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findByUser(User user);
}
