package Undertaker.HospiBook.repository;

import Undertaker.HospiBook.model.entities.Speciality;
import Undertaker.HospiBook.model.entities.User;
import Undertaker.HospiBook.model.entities.VisitFree;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VisitFreeRepository extends JpaRepository<VisitFree, Long> {
    List<VisitFree> findByDeleted(boolean deleted);
}
