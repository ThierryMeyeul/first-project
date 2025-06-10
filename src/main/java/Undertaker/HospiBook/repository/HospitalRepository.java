package Undertaker.HospiBook.repository;

import Undertaker.HospiBook.model.entities.Hospital;
import Undertaker.HospiBook.model.entities.Personnel;
import Undertaker.HospiBook.model.entities.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    List<Hospital> findByDeleted(Boolean deleted);

    Optional<Hospital> findByAddress(String address);

    Optional<Hospital> findByName(String name);

    //List<Hospital> findBySpeciality(Set<Speciality> specialities);

    @Query("SELECT DISTINCT h FROM Hospital h JOIN h.specialities s WHERE s.name LIKE %:specialityName%")
    List<Hospital> findBySpecialityNameContaining(@Param("specialityName") String specialityName);
}
