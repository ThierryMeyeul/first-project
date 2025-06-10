package Undertaker.HospiBook.controller;

import Undertaker.HospiBook.model.entities.Hospital;
import Undertaker.HospiBook.model.entities.Speciality;
import Undertaker.HospiBook.service.HospitalService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "hospital")
@SecurityRequirement(name = "bearerAuth")
public class HospitalController {
    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Hospital> create(@RequestBody Hospital hospital) {
        return ResponseEntity.ok(this.hospitalService.create(hospital));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Hospital> read() {
        return this.hospitalService.read();
    }

    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Hospital readId(@PathVariable Long id) {
        return this.hospitalService.readId(id);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(this.hospitalService.delete(id));
    }

    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Hospital> update(@PathVariable Long id, @RequestBody Hospital hospital) {
        return ResponseEntity.ok(this.hospitalService.update(id, hospital));
    }

    @PostMapping(path = "speciality/{name}")
    public ResponseEntity<Hospital> addSpeciality(@PathVariable String name, String speciality) {
        return ResponseEntity.ok(this.hospitalService.addSpeciality(name, speciality));
    }

    @GetMapping(path = "speciality/{name}")
    public ResponseEntity<Set<Speciality>> getSpecialities(@PathVariable String name) {
        return ResponseEntity.ok(this.hospitalService.getSpecialities(name));
    }

    @DeleteMapping(path = "speciality/{name}")
    public ResponseEntity<String> deleteSpeciality(@PathVariable String name, String speciality) {
        return ResponseEntity.ok(this.hospitalService.deleteSpeciality(name, speciality));
    }

    @GetMapping(path = "name/{name}")
    public ResponseEntity<Hospital> readByName(@PathVariable String name)     {
        return ResponseEntity.ok(this.hospitalService.readByName(name));
    }

    @GetMapping(path = "{speciality}/hospitals")
    public ResponseEntity<List<Hospital>> readBySpeciality(@PathVariable String speciality){
        return ResponseEntity.ok(this.hospitalService.readBySpeciality(speciality));
    }

    /*@PutMapping(path = "adminUpdate/{id}")
    public ResponseEntity<Hospital> updateHospital(@PathVariable Long id, @RequestPart Hospital hospital, @RequestPart(required = false)MultipartFile logo) throws IOException {
        return ResponseEntity.ok(this.hospitalService.updateHospital(id, hospital, logo));
    }

     */

    @PutMapping(path = "logo/{name}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Hospital> updateLogo(@PathVariable String name, @RequestPart(required = false) MultipartFile logo) throws  IOException {
        return ResponseEntity.ok(this.hospitalService.updateLogo(name, logo));
    }

    @PutMapping(path = "website/{name}")
    public ResponseEntity<Hospital> updateWebsite(@PathVariable String name, String website) throws  IOException {
        return ResponseEntity.ok(this.hospitalService.updateWebsite(name, website));
    }
}
