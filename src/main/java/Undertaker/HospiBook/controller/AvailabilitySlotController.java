package Undertaker.HospiBook.controller;

import Undertaker.HospiBook.dto.AvailabilitySlotDTO;
import Undertaker.HospiBook.model.entities.AvailabilitySlot;
import Undertaker.HospiBook.service.AvailabilitySlotService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping(path = "availabilitySlot")
@SecurityRequirement(name = "bearerAuth")
public class AvailabilitySlotController {

    private final AvailabilitySlotService availabilitySlotService;

    public AvailabilitySlotController(AvailabilitySlotService availabilitySlotService) {
        this.availabilitySlotService = availabilitySlotService;
    }

    /*@PostMapping
    public ResponseEntity<AvailabilitySlot> create(@RequestBody AvailabilitySlotDTO availabilitySlotDTO){
        return ResponseEntity.ok(this.availabilitySlotService.create(availabilitySlotDTO));
    }*/

    @GetMapping(path = "{id}")
    public ResponseEntity<AvailabilitySlot> readById(@PathVariable Long id){
        return ResponseEntity.ok(this.availabilitySlotService.readById(id));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<AvailabilitySlot> update(@PathVariable Long id, @RequestBody AvailabilitySlotDTO availabilitySlotDTO){
        return ResponseEntity.ok(this.availabilitySlotService.update(id, availabilitySlotDTO));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        return ResponseEntity.ok(this.availabilitySlotService.delete(id));
    }

    @GetMapping()
    public ResponseEntity<List<AvailabilitySlot>> readByDoctor(@RequestParam String doctorEmail, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate date){
        return ResponseEntity.ok(this.availabilitySlotService.readByDoctor(doctorEmail, date));
    }

    @PostMapping(path = "{email}/slots")
    public ResponseEntity<List<AvailabilitySlot>> createSlots(@PathVariable String email, @RequestBody List<AvailabilitySlotDTO> availabilitySlotDTOS){
        return ResponseEntity.ok(this.availabilitySlotService.createSlots(email, availabilitySlotDTOS));
    }

    @GetMapping(path = "{email}/slots")
    public ResponseEntity<List<AvailabilitySlot>> readSlots(@PathVariable String email){
        return ResponseEntity.ok(this.availabilitySlotService.readSlots(email));
    }

    @GetMapping("/by-start-time")
    public List<AvailabilitySlot> getAvailabilitySlotsByStartTime(@RequestParam String startTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime parsedStartTime = LocalDateTime.parse(startTime, formatter);

        return availabilitySlotService.readSlotByDate(parsedStartTime);
    }
}
