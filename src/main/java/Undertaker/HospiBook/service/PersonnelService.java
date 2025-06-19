package Undertaker.HospiBook.service;

import Undertaker.HospiBook.dto.PersonnelDTO;
import Undertaker.HospiBook.model.entities.*;
import Undertaker.HospiBook.model.enums.UserRoleEnum;
import Undertaker.HospiBook.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

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

    @Transactional
    public Personnel create(String email, PersonnelDTO personnelDTO){
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        Person person = this.personRepository.findByUser(user);
        System.out.println("ok");
        if(!this.personnelRepository.existsById(person.getId())){
            Set<Role> roles = new HashSet<>();
            roles = person.getUser().getRoles();
            Optional<Role> role;
            System.out.println("ok");
            List<Hospital> hospitals = this.hospitalRepository.findByDeleted(false);
            AtomicReference<Hospital> hospital;
            hospital = new AtomicReference<>(new Hospital());
            hospitals.forEach(hospital1 -> {
                if (Objects.equals(hospital1.getName(), personnelDTO.getHospitalAddress())){
                    hospital.set(hospital1);
                }

            });
            Personnel personnel = new Personnel(person.getFirstName(), person.getLastName(), person.getBirthDate(), person.getPhoneNumber(), person.getGenderEnum(), person.getCity(), person.getAddress(), personnelDTO.getHireDate(), personnelDTO.getEmployeeNumber());
            personnel.setHospital(hospital.get());
            if (personnelDTO.getRole() == UserRoleEnum.SECRETARY) {
                role = this.roleRepository.findByName(UserRoleEnum.SECRETARY);
            } else {
                role = this.roleRepository.findByName(UserRoleEnum.DOCTOR);
                List<Speciality> specialities = this.specialityRepository.findByDeleted(false);
                AtomicReference<Speciality> speciality;
                speciality = new AtomicReference<>(new Speciality());
                specialities.forEach(speciality1 -> {
                    if (speciality1.getName().equals(personnelDTO.getSpeciality())){
                        speciality.set(speciality1);
                    }
                });
                personnel.setSpeciality(speciality.get());
            }
            roles.add(role.get());
            user.setRoles(roles);
            this.userRepository.save(user);
            this.personRepository.delete(person);
            entityManager.flush();
            personnel.setUser(user);
            return this.personnelRepository.save(personnel);
        } else {
            Personnel personnel = this.personnelRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Personnel not found"));
            personnel.setEmployeeNumber(personnelDTO.getEmployeeNumber());
            personnel.setFirstName(personnelDTO.getFirstName());
            personnel.setLastName(personnelDTO.getLastName());
            personnel.setBirthDate(personnelDTO.getBirthDate());
            personnel.setGender(personnelDTO.getGender());
            personnel.setPhoneNumber(personnelDTO.getPhoneNumber());
            personnel.setCity(personnelDTO.getCity());
            personnel.setAddress(personnelDTO.getAddress());
            personnel.setEmployeeNumber(personnelDTO.getEmployeeNumber());
            personnel.setHireDate(personnelDTO.getHireDate());
            return this.personnelRepository.save(personnel);
        }
    }


    public List<Personnel> read() {
        return this.personnelRepository.findAll();
    }


    public Personnel readById(Long id) {
        return this.personnelRepository.findById(id).orElseThrow(() -> new RuntimeException("personnel not found"));
    }
}
