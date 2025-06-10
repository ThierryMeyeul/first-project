package Undertaker.HospiBook.repository;

import Undertaker.HospiBook.model.entities.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {
    List<RendezVous> findAllByDeleted(boolean deleted);
}
