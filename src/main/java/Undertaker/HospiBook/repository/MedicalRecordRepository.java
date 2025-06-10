package Undertaker.HospiBook.repository;

import Undertaker.HospiBook.model.entities.MedicalRecord;
import Undertaker.HospiBook.model.entities.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    List<MedicalRecord> findAllByDeleted(boolean deleted);

    // MedicalRecord findByVisit(Visit visit);
}
