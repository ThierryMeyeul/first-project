package Undertaker.HospiBook.repository;

import Undertaker.HospiBook.model.entities.Hospital;
import Undertaker.HospiBook.model.entities.Personnel;
import Undertaker.HospiBook.model.entities.User;
import Undertaker.HospiBook.model.entities.Visit;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PersonnelRepository extends JpaRepository<Personnel, Long> {

    Optional<Personnel> findByUser(User userDoctor);

    List<Personnel> findAllByHospital(Hospital hospital);
}
