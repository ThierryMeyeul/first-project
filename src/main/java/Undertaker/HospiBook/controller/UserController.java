package Undertaker.HospiBook.controller;

import Undertaker.HospiBook.component.JwtUtil;
import Undertaker.HospiBook.dto.*;
import Undertaker.HospiBook.model.entities.*;
import Undertaker.HospiBook.model.enums.UserRoleEnum;
import Undertaker.HospiBook.repository.PersonRepository;
import Undertaker.HospiBook.service.AccountService;
import Undertaker.HospiBook.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PersonRepository personRepository;
    private final AccountService accountService;

    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil, PersonRepository personRepository, AccountService accountService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.personRepository = personRepository;
        this.accountService = accountService;
    }

    @PostMapping("superAdmin/auth/register")
    public ResponseEntity<Person> register(@RequestBody @Valid RegisterRequest request) {
        Person response = this.userService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("auth/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        try {
            User user = this.userService.readEmail(request.email());
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse(null, null, null, "Email ou mot de passe incorrect", null));
            }
            if (!user.isActive()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse(null, null, null, "Compte bloqué. Contactez l'administrateur.", null));
            }
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
            accountService.resetFailedAttempts(request.email());
            final String token = jwtUtil.generateToken(request.email());
            Person person = this.personRepository.findByUser(user);
            if (person instanceof Personnel) {
                AuthResponse authResponse = new AuthResponse(user.getId(), user.getUserName(), user.getEmail(), token, ((Personnel) person).getHospital().getName());
                authResponse.setRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
                return ResponseEntity.ok(authResponse);
            } else {
                AuthResponse authResponse = new AuthResponse(user.getId(), user.getUserName(), user.getEmail(), token, person.getHospitalName());
                authResponse.setRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
                return ResponseEntity.ok(authResponse);
            }

        } catch (AuthenticationException e) {
            User user = userService.readEmail(request.email());
            if (user != null) {
                accountService.incrementFailedAttemps(user);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse(null, null, null, "Email ou mot de passe incorrect", null));
        }
    }

    @PostMapping(path = "auth/register")
    public ResponseEntity<Patient> registerPatient(@RequestBody PatientDTO patientDTO){
        return ResponseEntity.ok(this.userService.registerPatient(patientDTO));
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping(path = "admin/auth/register")
    public ResponseEntity<Personnel> registerPersonnel(@RequestBody PersonnelDTO personnelDTO){
        return ResponseEntity.ok(this.userService.registerPersonnel(personnelDTO));
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping(path = "superAdmin/admin")
    public ResponseEntity<List<Person>> readAdmin(){
        return ResponseEntity.ok(this.userService.readAdmin());
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping(path = "admin/personnel")
    public ResponseEntity<List<Personnel>> readPersonnel(){
        return ResponseEntity.ok(this.userService.readPersonnel());
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping(path = "personnel/patient")
    public ResponseEntity<List<Patient>> readPatient(){
        return ResponseEntity.ok(this.userService.readPatient());
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping(path = "{name}/personnel")
    public ResponseEntity<List<Personnel>> readByHospitalName(@PathVariable String name){
        return ResponseEntity.ok(this.userService.readByHospitalName(name));
    }

    @GetMapping(path = "{name}/personnel/doctor")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<List<Personnel>> readDoctorByHospitalName(@PathVariable String name){
        return ResponseEntity.ok(this.userService.readDoctorByHospitalName(name));
    }

    @GetMapping(path = "allUser")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<List<Person>> readAllUser(){
        return ResponseEntity.ok(this.userService.readAllUser());
    }

    @PutMapping(path = "roles/{email}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Person> addRoles(@PathVariable String email, @RequestParam UserRoleEnum role, @RequestParam String hospitalName){
        return ResponseEntity.ok(this.userService.addRole(email, role, hospitalName));
    }

    @DeleteMapping(path = "roles/{email}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Person> deleteRole(@PathVariable String email, @RequestParam UserRoleEnum role) {
        return ResponseEntity.ok(this.userService.deleteRole(email, role));
    }

    @GetMapping(path = "profile/{email}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Person> getProfile(@PathVariable String email){
        return ResponseEntity.ok(this.userService.getProfile(email));
    }
}
