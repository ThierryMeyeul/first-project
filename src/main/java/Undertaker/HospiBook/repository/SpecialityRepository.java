package Undertaker.HospiBook.repository;

import Undertaker.HospiBook.model.entities.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpecialityRepository extends JpaRepository<Speciality, Long> {
    Optional<Speciality> findByName(String name);
    List<Speciality> findByDeleted(boolean deleted);
    List<Speciality> findAllByName(String name);
}
