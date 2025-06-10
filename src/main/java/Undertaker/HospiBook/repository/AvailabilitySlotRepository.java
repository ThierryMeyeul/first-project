package Undertaker.HospiBook.repository;

import Undertaker.HospiBook.model.entities.AvailabilitySlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AvailabilitySlotRepository extends JpaRepository<AvailabilitySlot, Long> {
    List<AvailabilitySlot> findAllByDeleted(boolean deleted);

    @Query("SELECT a FROM AvailabilitySlot a " +
            "WHERE a.personnel.id = :id " +
            "AND a.startTime BETWEEN :startOfDay AND :endOfDay " +
            "AND a.availability = :b " +
            "ORDER BY a.startTime")
    List<AvailabilitySlot> findDoctorAndDateTimeRange(
            @Param("id") Long id,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay,
            @Param("b") boolean b);

    List<AvailabilitySlot> findByDate(LocalDateTime startTime);

    List<AvailabilitySlot> findByStartTime(LocalDateTime startTime);

    @Query("SELECT a FROM AvailabilitySlot a WHERE a.startTime = :startTime")
    List<AvailabilitySlot> findSlotsByExactStartTime(@Param("startTime") LocalDateTime startTime);

    @Query("SELECT a FROM AvailabilitySlot a WHERE DATE(a.startTime) = DATE(:startTime)")
    List<AvailabilitySlot> findSlotsByDate(@Param("startTime") LocalDateTime startTime);
}
