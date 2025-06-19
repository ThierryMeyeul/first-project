package Undertaker.HospiBook.controller;

import Undertaker.HospiBook.dto.NotificationCreateDTO;
import Undertaker.HospiBook.model.entities.Notification;
import Undertaker.HospiBook.service.NotificationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "notification")
@SecurityRequirement(name = "bearerAuth")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody NotificationCreateDTO notification) {
        return ResponseEntity.ok(notificationService.create(notification));
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<List<Notification>> getUserNotifications(@PathVariable String email) {
        return ResponseEntity.ok(notificationService.getUserNotification(email));
    }

    @GetMapping("/user/{email}/unread")
    public ResponseEntity<List<Notification>> getUnreadNotifications(@PathVariable String email) {
        return ResponseEntity.ok(notificationService.getUnreadNotifications(email));
    }

    @GetMapping("/user/{email}/unread-count")
    public ResponseEntity<Long> getUnreadCount(@PathVariable String email) {
        return ResponseEntity.ok(notificationService.getUnreadCount(email));
    }

    @PutMapping("/{id}/mark-as-read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/user/{email}/mark-all-as-read")
    public ResponseEntity<Void> markAllAsRead(@PathVariable String email) {
        notificationService.markAllAsRead(email);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        notificationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}