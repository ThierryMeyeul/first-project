package Undertaker.HospiBook.repository;

import Undertaker.HospiBook.model.entities.Notification;
import Undertaker.HospiBook.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByDeleted(boolean deleted);

    List<Notification> findByUserOrderByCreatedDate(User user);
    List<Notification> findByUserAndIsReadFalseOrderByCreatedDate(User user);
    long countByUserAndIsReadFalse(User user);
}
