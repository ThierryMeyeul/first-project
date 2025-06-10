package Undertaker.HospiBook.controller;

import Undertaker.HospiBook.dto.PersonnelDTO;
import Undertaker.HospiBook.model.entities.Person;
import Undertaker.HospiBook.model.entities.Personnel;
import Undertaker.HospiBook.model.entities.Role;
import Undertaker.HospiBook.service.PersonnelService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "personnel")
@SecurityRequirement(name = "bearerAuth")
public class PersonnelController {
    private final PersonnelService personnelService;

    public PersonnelController(PersonnelService personnelService) {
        this.personnelService = personnelService;
    }

    @PostMapping(path = "{email}")
    public ResponseEntity<Personnel> create(@PathVariable String email, @RequestBody PersonnelDTO personnelDTO){
        return ResponseEntity.ok(this.personnelService.create(email, personnelDTO));
    }

    @GetMapping()
    public ResponseEntity<List<Personnel>> read(){
        return ResponseEntity.ok(this.personnelService.read());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Personnel> readById(@PathVariable Long id){
        return ResponseEntity.ok(this.personnelService.readById(id));
    }


}
