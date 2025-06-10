package Undertaker.HospiBook.controller;

import Undertaker.HospiBook.dto.SpecialityDTO;
import Undertaker.HospiBook.dto.SpecialityUpdateDTO;
import Undertaker.HospiBook.model.entities.Speciality;
import Undertaker.HospiBook.service.SpecialityService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "speciality")
@SecurityRequirement(name = "bearerAuth")
public class SpecialityController {
    private final SpecialityService specialityService;

    public SpecialityController(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }

    @PostMapping()
    public ResponseEntity<Speciality> create(@RequestBody SpecialityDTO specialityDTO){
        Speciality response = this.specialityService.create(specialityDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<List<Speciality>> read(){
        return ResponseEntity.ok(this.specialityService.read());
    }

    @GetMapping(path = "{name}")
    public ResponseEntity<Speciality> readName(@PathVariable String name){
        return ResponseEntity.ok(this.specialityService.readName(name));
    }

    @PutMapping(path = "{name}")
    public ResponseEntity<Speciality> update(@PathVariable String name, @RequestBody SpecialityUpdateDTO specialityUpdateDTO){
        Speciality response = this.specialityService.update(name, specialityUpdateDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "{name}")
    public ResponseEntity<String> update(@PathVariable String name){
        return ResponseEntity.ok(this.specialityService.delete(name));
    }
}
