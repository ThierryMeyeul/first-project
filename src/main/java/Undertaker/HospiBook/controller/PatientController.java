package Undertaker.HospiBook.controller;

import Undertaker.HospiBook.dto.PatientDTO;
import Undertaker.HospiBook.model.entities.Patient;
import Undertaker.HospiBook.model.entities.Personnel;
import Undertaker.HospiBook.service.PatientService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "patient")
@SecurityRequirement(name = "bearerAuth")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping(path = "{email}")
    public ResponseEntity<Patient> create(@PathVariable String email, @RequestBody PatientDTO patientDTO){
        return ResponseEntity.ok(this.patientService.create(email, patientDTO));
    }

    @GetMapping
    public ResponseEntity<List<Patient>> read(){
        return ResponseEntity.ok(this.patientService.read());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Patient> readId(@PathVariable Long id){
        return ResponseEntity.ok(this.patientService.readId(id));
    }
}
