package Undertaker.HospiBook.controller;

import Undertaker.HospiBook.dto.NotificationCreateDTO;
import Undertaker.HospiBook.model.entities.Notification;
import Undertaker.HospiBook.service.NotificationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class NotificationWebSocketController {
    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationService notificationService;

    public NotificationWebSocketController(SimpMessagingTemplate messagingTemplate,
                                           NotificationService notificationService) {
        this.messagingTemplate = messagingTemplate;
        this.notificationService = notificationService;
    }

    @MessageMapping("/send-notification")
    @SendTo("/topic/notifications")
    public Notification sendNotification(@RequestBody NotificationCreateDTO notificationCreateDTO) {
        Notification savedNotification = notificationService.create(notificationCreateDTO);

        // Envoi à l'utilisateur spécifique
        messagingTemplate.convertAndSendToUser(
                notificationCreateDTO.getEmailUser(),
                "/queue/notifications",
                savedNotification);

        return savedNotification;
    }

    @MessageMapping("/mark-as-read")
    public void handleMarkAsRead(Long notificationId) {
        notificationService.markAsRead(notificationId);
    }

    @MessageMapping("/mark-all-as-read")
    public void handleMarkAllAsRead(String userEmail) {
        notificationService.markAllAsRead(userEmail);
    }
}