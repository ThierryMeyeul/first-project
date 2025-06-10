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

    @PostMapping()
    public ResponseEntity<Notification> create(@RequestBody NotificationCreateDTO notificationCreateDTO){
        return ResponseEntity.ok(this.notificationService.create(notificationCreateDTO));
    }

    @GetMapping()
    public ResponseEntity<List<Notification>> read(){
        return ResponseEntity.ok(this.notificationService.read());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Notification> readId(@PathVariable Long id){
        return ResponseEntity.ok(this.notificationService.readId(id));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Notification> update(@PathVariable Long id, @RequestBody NotificationCreateDTO notificationCreateDTO){
        return ResponseEntity.ok(this.notificationService.update(id, notificationCreateDTO));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        return ResponseEntity.ok(this.notificationService.delete(id));
    }
}
