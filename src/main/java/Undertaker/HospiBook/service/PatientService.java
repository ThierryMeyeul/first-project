package Undertaker.HospiBook.service;

import Undertaker.HospiBook.dto.PatientDTO;
import Undertaker.HospiBook.model.entities.*;
import Undertaker.HospiBook.model.enums.UserRoleEnum;
import Undertaker.HospiBook.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final PersonRepository personRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PersonnelRepository personnelRepository;
    private final EntityManager entityManager;

    public PatientService(PatientRepository patientRepository, PersonRepository personRepository, UserRepository userRepository, RoleRepository roleRepository, PersonnelRepository personnelRepository, EntityManager entityManager) {
        this.patientRepository = patientRepository;
        this.personRepository = personRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.personnelRepository = personnelRepository;
        this.entityManager = entityManager;
    }

    @Transactional
    public Patient create(String email, PatientDTO patientDTO){
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        Person person = this.personRepository.findByUser(user);
        if(!this.patientRepository.existsById(person.getId())){
            Set<Role> roles = new HashSet<>();
            roles = person.getUser().getRoles();
            Optional<Role> role = this.roleRepository.findByName(UserRoleEnum.USER);
            roles.add(role.get());
            user.setRoles(roles);
            this.userRepository.save(user);
            Patient patient = new Patient(person.getFirstName(), person.getLastName(), person.getBirthDate(), person.getPhoneNumber(), person.getGenderEnum(), person.getCity(), person.getAddress(), patientDTO.getBloodTypeEnum(), patientDTO.getEmergencyContact(), patientDTO.getPatientStatusEnum());
            this.personRepository.delete(person);
            entityManager.flush();
            patient.setUser(user);
            return this.patientRepository.save(patient);
        } else {
            Patient patient = this.patientRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Patient not found"));
            patient.setStatus(patientDTO.getPatientStatusEnum());
            patient.setCity(patientDTO.getCity());
            patient.setEmergencyContact(patientDTO.getEmergencyContact());
            patient.setBloodType(patientDTO.getBloodTypeEnum());
            patient.setPhoneNumber(patientDTO.getPhoneNumber());
            patient.setGender(patientDTO.getGender());
            patient.setLastName(patientDTO.getLastName());
            patient.setFirstName(patientDTO.getFirstName());
            patient.setBirthDate(patientDTO.getBirthDate());
            patient.setAddress(patientDTO.getAddress());
            return this.patientRepository.save(patient);
        }
    }

    /*public String create(Long id, PatientDTO patientDTO){
        Person person = this.personRepository.findById(id).orElseThrow(() -> new RuntimeException("Person doesn't exists"));

        this.personRepository.delete(person);
        entityManager.flush();
        Patient patient = new Patient(person, BloodTypeEnum.A_NEGATIVE, "6987698", PatientStatusEnum.INACTIVE);
        patient.setId(person.getId());
        this.patientRepository.save(patient);
        return "true";
    }*/


    public List<Patient> read() {
        return this.patientRepository.findAll();
    }

    public Patient readId(Long id) {
        return this.patientRepository.findById(id).orElseThrow(() -> new RuntimeException("Patient doesn't exists"));
    }

    /*@Transactional
    public String create(Long id, PatientDTO patientDTO) {
        Person person = this.personRepository.findById(id).orElseThrow(() -> new RuntimeException("Person doesn't exists"));
        Patient patient = new Patient();
        copierProprietes(person, patient);
        patient.setBloodType(patientDTO.getBloodTypeEnum());
        patient.setStatus(patientDTO.getPatientStatusEnum());
        patient.setEmergencyContact(patientDTO.getEmergencyContact());
        this.patientRepository.save(patient);
        return "error";
    }*/
}
