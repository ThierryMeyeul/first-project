package Undertaker.HospiBook.service;

import Undertaker.HospiBook.dto.NotificationCreateDTO;
import Undertaker.HospiBook.model.entities.Notification;
import Undertaker.HospiBook.model.entities.User;
import Undertaker.HospiBook.repository.NotificationRepository;
import Undertaker.HospiBook.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    /*public Notification create(NotificationCreateDTO notificationCreateDTO) {
        Optional<User> user = this.userRepository.findByEmail(notificationCreateDTO.getEmailUser());
        if(user.isPresent()){
            Notification notification = new Notification(notificationCreateDTO.getTitle(), notificationCreateDTO.getText(), notificationCreateDTO.getType(), notificationCreateDTO.getValidFrom(), notificationCreateDTO.getValidTo(), user.get());
            return this.notificationRepository.save(notification);
        }
        return null;
    }

    public List<Notification> read() {
        return this.notificationRepository.findAllByDeleted(false);
    }

    public Notification readId(Long id) {
        Optional<Notification> notification = this.notificationRepository.findById(id);
        if(notification.isPresent()){
            if (!notification.get().isDeleted()){
                return notification.get();
            }return null;
        }
        return null;
    }

    public Notification update(Long id, NotificationCreateDTO notificationCreateDTO) {
        Optional<User> user = this.userRepository.findByEmail(notificationCreateDTO.getEmailUser());
        if(user.isPresent()){
            Notification notification = this.readId(id);
            notification.setTitle(notificationCreateDTO.getTitle());
            notification.setText(notificationCreateDTO.getText());
            notification.setType(notificationCreateDTO.getType());
            notification.setValidFrom(notificationCreateDTO.getValidFrom());
            notification.setValidTo(notificationCreateDTO.getValidTo());
            notification.setUser(user.get());
            return this.notificationRepository.save(notification);
        }
        return null;
    }

    public String delete(Long id) {
        Optional<Notification> notification = this.notificationRepository.findById(id);
        if(notification.isPresent()){
            notification.get().setDeleted(true);
            this.notificationRepository.save(notification.get());
            return "notification deleted successful";
        }
        return "notification doesn't exists";
    }

    */

    public Notification create(NotificationCreateDTO notificationCreateDTO){
        User user = this.userRepository.findByEmail(notificationCreateDTO.getEmailUser()).orElseThrow(() -> new RuntimeException("User not found"));
        Notification notification = new Notification(notificationCreateDTO.getTitle(), notificationCreateDTO.getText(), notificationCreateDTO.getType(), notificationCreateDTO.getValidFrom(), notificationCreateDTO.getValidTo(), user);
        return this.notificationRepository.save(notification);
    }

    public List<Notification> getUserNotification(String email) {
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        return this.notificationRepository.findByUserOrderByCreatedDate(user);
    }

    public List<Notification> getUnreadNotifications(String email) {
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        return this.notificationRepository.findByUserAndIsReadFalseOrderByCreatedDate(user);
    }

    public long getUnreadCount(String email) {
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        return this.notificationRepository.countByUserAndIsReadFalse(user);
    }

    @Transactional
    public void markAsRead(Long id) {
        this.notificationRepository.findById(id).ifPresent(notification -> {
            notification.setRead(true);
            this.notificationRepository.save(notification);
        });
    }

    @Transactional
    public void markAllAsRead (String email) {
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        List<Notification> unreadNotifications = getUnreadNotifications(email);
        unreadNotifications.forEach(notification -> notification.setRead(true));
        notificationRepository.saveAll(unreadNotifications);
    }

    public void delete (Long id) {
        this.notificationRepository.deleteById(id);
    }
}
