package Undertaker.HospiBook.service;

import Undertaker.HospiBook.dto.PersonnelDTO;
import Undertaker.HospiBook.model.entities.*;
import Undertaker.HospiBook.model.enums.UserRoleEnum;
import Undertaker.HospiBook.repository.*;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PersonnelService {
    private final PersonnelRepository personnelRepository;
    private final PersonRepository personRepository;
    private EntityManager entityManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PatientRepository patientRepository;
    private final HospitalRepository hospitalRepository;
    private final SpecialityRepository specialityRepository;

    public PersonnelService(PersonnelRepository personnelRepository, PersonRepository personRepository, EntityManager entityManager, UserRepository userRepository, RoleRepository roleRepository, PatientRepository patientRepository, HospitalRepository hospitalRepository, SpecialityRepository specialityRepository) {
        this.personnelRepository = personnelRepository;
        this.personRepository = personRepository;
        this.entityManager = entityManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.patientRepository = patientRepository;
        this.hospitalRepository = hospitalRepository;
        this.specialityRepository = specialityRepository;
    }

    public Personnel create(String email, PersonnelDTO personnelDTO){
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        Person person = this.personRepository.findByUser(user);
        System.out.println("hospital" + personnelDTO.getHospitalAddress());
        Hospital hospital = this.hospitalRepository.findByAddress(personnelDTO.getHospitalAddress()).orElseThrow(() -> new RuntimeException("hospital not found"));
        Speciality speciality = this.specialityRepository.findByName(personnelDTO.getSpeciality()).orElseThrow(() -> new RuntimeException("speciality not found"));
        Optional<Personnel> personnel = this.personnelRepository.findById(person.getId());
        Optional<Patient> patient = this.patientRepository.findById(person.getId());
        if(personnel.isPresent()){
            return null;
        }
        if(patient.isEmpty()){
            Set<Role> roles = new HashSet<>();
            roles = user.getRoles();
            if(personnelDTO.getRole() == UserRoleEnum.DOCTOR){
                Optional<Role> role = this.roleRepository.findByName(UserRoleEnum.DOCTOR);
                roles.add(role.get());
                user.setRoles(roles);
                this.userRepository.save(user);
                this.personRepository.delete(person);
            }else if(personnelDTO.getRole() == UserRoleEnum.SECRETARY){
                Optional<Role> role = this.roleRepository.findByName(UserRoleEnum.SECRETARY);
                roles.add(role.get());
                user.setRoles(roles);
                this.userRepository.save(user);
            }else {
                return null;
            }
        }
        return null;
    }


    public List<Personnel> read() {
        return this.personnelRepository.findAll();
    }


    public Personnel readById(Long id) {
        return this.personnelRepository.findById(id).orElseThrow(() -> new RuntimeException("personnel not found"));
    }
}
