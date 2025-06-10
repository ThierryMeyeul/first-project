package Undertaker.HospiBook.controller;

import Undertaker.HospiBook.dto.MedicalRecordDTO;
import Undertaker.HospiBook.dto.RendezVousDTO;
import Undertaker.HospiBook.dto.VisitDTO;
import Undertaker.HospiBook.model.entities.Person;
import Undertaker.HospiBook.model.entities.RendezVous;
import Undertaker.HospiBook.model.entities.Visit;
import Undertaker.HospiBook.service.RendezVousService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "rdv")
@SecurityRequirement(name = "bearerAuth")
public class RendezVousController {
    private final RendezVousService rendezVousService;

    public RendezVousController(RendezVousService rendezVousService) {
        this.rendezVousService = rendezVousService;
    }

    @PostMapping()
    public ResponseEntity<RendezVous> create(@RequestBody VisitDTO visitDTO){
        return ResponseEntity.ok(this.rendezVousService.create(visitDTO));
    }

    @GetMapping()
    public ResponseEntity<List<RendezVous>> read(){
        return ResponseEntity.ok(this.rendezVousService.read());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<RendezVous> readId(@PathVariable Long id){
        return ResponseEntity.ok(this.rendezVousService.readId(id));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<RendezVous> update(@PathVariable Long id, @RequestBody RendezVousDTO rendezVousDTO){
        return ResponseEntity.ok(this.rendezVousService.update(id, rendezVousDTO));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> delete(Long id){
        return ResponseEntity.ok(this.rendezVousService.delete(id));
    }

    @GetMapping(path = "upcoming/{email}")
    public ResponseEntity<List<RendezVous>> upcomingAppointment(@PathVariable String email){
        return ResponseEntity.ok(this.rendezVousService.upcomingAppointment(email));
    }

    @GetMapping(path = "passed/{email}")
    public ResponseEntity<List<RendezVous>> passedAppointment(@PathVariable String email){
        return ResponseEntity.ok(this.rendezVousService.passedAppointment(email));
    }

    @GetMapping(path = "personnel/{email}")
    public ResponseEntity<List<RendezVous>> readByPersonnelAppointmentRequest(@PathVariable String email){
        return ResponseEntity.ok(this.rendezVousService.readByPersonnelAppointmentRequest(email));
    }

    @GetMapping(path = "{email}/personnel")
    public ResponseEntity<List<RendezVous>> readByAppointment(@PathVariable String email){
        return ResponseEntity.ok(this.rendezVousService.readByAppointment(email));
    }

    @GetMapping(path = "secretary/{email}")
    public ResponseEntity<List<RendezVous>> readBySecretaryAppointmentRequest(@PathVariable String email){
        return ResponseEntity.ok(this.rendezVousService.readBySecretaryAppointmentRequest(email));
    }

    @GetMapping(path = "patient/{email}")
    public ResponseEntity<List<RendezVous>> listRDV(@PathVariable String email){
        return ResponseEntity.ok(this.rendezVousService.listRDV(email));
    }
}
