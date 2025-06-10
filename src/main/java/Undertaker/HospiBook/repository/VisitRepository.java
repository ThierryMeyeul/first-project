package Undertaker.HospiBook.repository;

import Undertaker.HospiBook.model.entities.Hospital;
import Undertaker.HospiBook.model.entities.Patient;
import Undertaker.HospiBook.model.entities.Visit;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.lang.annotation.Target;
import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Long> {
    List<Visit> findAllByDeleted(boolean deleted);

    List<Visit> findAllByPatient(Patient patient);

    List<Visit> findByHospital(Hospital hospital);
}
