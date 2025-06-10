package Undertaker.HospiBook.controller;

import Undertaker.HospiBook.dto.MedicalRecordDTO;
import Undertaker.HospiBook.dto.PaymentDTO;
import Undertaker.HospiBook.dto.VisitDTO;
import Undertaker.HospiBook.model.entities.User;
import Undertaker.HospiBook.model.entities.Visit;
import Undertaker.HospiBook.service.VisitService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "visit")
@SecurityRequirement(name = "bearerAuth")
public class VisitController {
    private final VisitService visitService;

    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    @PostMapping()
    public ResponseEntity<Visit> create(@RequestBody VisitDTO visitDTO){
        Visit response = this.visitService.create(visitDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<Visit>> read(){
        return ResponseEntity.ok(this.visitService.read());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Visit> readById(@PathVariable Long id){
        return ResponseEntity.ok(this.visitService.readById(id));
    }

    @PutMapping(path ="{id}")
    public ResponseEntity<Visit> update(@PathVariable Long id, @RequestBody VisitDTO visitDTO){
        return ResponseEntity.ok(this.visitService.update(id, visitDTO));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        return ResponseEntity.ok(this.visitService.delete(id));
    }


    @GetMapping(path = "patient/{email}")
    public ResponseEntity<List<Visit>> getByEmailPatient(@PathVariable String email){
        return ResponseEntity.ok(this.visitService.getByEmailPatient(email));
    }

    @GetMapping(path = "hospital/{name}")
    public ResponseEntity<List<Visit>> getVisitByHospitalName(@PathVariable String name){
        return ResponseEntity.ok(this.visitService.getVisitByHospitalName(name));
    }

    @PutMapping(path = "medicalRecord/{id}")
    public ResponseEntity<Visit> createOrUpdateMedicalRecord(@PathVariable Long id, @RequestBody MedicalRecordDTO medicalRecordDTO) {
        return ResponseEntity.ok(this.visitService.createOrUpdateMedicalRecord(id, medicalRecordDTO));
    }

    @PutMapping(path = "payment/{id}")
    public ResponseEntity<Visit> createOrUpdatePayment(@PathVariable Long id, @RequestBody PaymentDTO paymentDTO) {
        return ResponseEntity.ok(this.visitService.createOrUpdatePayment(id, paymentDTO));
    }
}
