package Undertaker.HospiBook.repository;

import Undertaker.HospiBook.model.entities.Patient;
import Undertaker.HospiBook.model.entities.User;
import Undertaker.HospiBook.model.entities.Visit;
import Undertaker.HospiBook.model.enums.BloodTypeEnum;
import Undertaker.HospiBook.model.enums.PatientStatusEnum;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Modifying
    @Query(value = "INSERT INTO patient(id, status, emergency_contact, blood_type_enum)" + "SELECT p.id, :status, :emergencyContact, :bloodTypeEnum " + "FROM person p WHERE p.id = :id", nativeQuery = true)
    @Transactional
    void create(@Param("id") Long id, @Param("emergencyContact") String emergencyContact, @Param("status") PatientStatusEnum status, @Param("bloodTypeEnum")BloodTypeEnum bloodTypeEnum);


    Optional<Patient> findByUser(User user);
}
