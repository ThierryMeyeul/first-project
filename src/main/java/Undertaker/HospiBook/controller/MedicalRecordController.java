package Undertaker.HospiBook.controller;


import Undertaker.HospiBook.dto.MedicalRecordDTO;
import Undertaker.HospiBook.model.entities.MedicalRecord;
import Undertaker.HospiBook.service.MedicalRecordService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "medicalRecord")
@SecurityRequirement(name = "bearerAuth")
public class MedicalRecordController {

    /*private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @PostMapping(path = "/visit/{id}")
    public ResponseEntity<MedicalRecord> create(@PathVariable Long id, @RequestBody MedicalRecordDTO medicalRecordDTO){
        return ResponseEntity.ok(this.medicalRecordService.create(id, medicalRecordDTO));
    }

    @GetMapping
    public ResponseEntity<List<MedicalRecord>> read(){
        return ResponseEntity.ok(this.medicalRecordService.read());
    }

    @GetMapping(path = "/visit/{id}")
    public ResponseEntity<MedicalRecord> readById(@PathVariable Long id){
        return ResponseEntity.ok(this.medicalRecordService.readById(id));
    }

    @PutMapping(path = "/visit/{id}")
    public ResponseEntity<MedicalRecord> update(@PathVariable Long id, MedicalRecordDTO medicalRecordDTO){
        return ResponseEntity.ok(this.medicalRecordService.update(id, medicalRecordDTO));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> deleted(@PathVariable Long id){
        return ResponseEntity.ok(this.medicalRecordService.delete(id));
    }

     */
}
