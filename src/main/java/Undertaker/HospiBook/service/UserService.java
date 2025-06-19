package Undertaker.HospiBook.service;

import Undertaker.HospiBook.dto.PatientDTO;
import Undertaker.HospiBook.dto.PersonnelDTO;
import Undertaker.HospiBook.dto.RegisterRequest;
import Undertaker.HospiBook.model.entities.*;
import Undertaker.HospiBook.model.enums.UserRoleEnum;
import Undertaker.HospiBook.repository.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;
    private final PersonRepository personRepository;
    private final HospitalRepository hospitalRepository;
    private final SpecialityRepository specialityRepository;
    private final PersonnelRepository personnelRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, PersonRepository personRepository, HospitalRepository hospitalRepository, SpecialityRepository specialityRepository, PersonnelRepository personnelRepository, PatientRepository patientRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.personRepository = personRepository;
        this.hospitalRepository = hospitalRepository;
        this.specialityRepository = specialityRepository;
        this.personnelRepository = personnelRepository;
        this.patientRepository = patientRepository;
    }


    /*public void login(LoginRequest loginRequest){

        User user = userRepository.findByEmail(loginRequest.email()).orElseThrow(() -> new RuntimeException("User not found"));
        if(!passwordEncoder.matches(loginRequest.password(), user.getPassword())){
            throw new RuntimeException("Invalid Password");
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.email(),
                            loginRequest.password() // Doit être le mot de passe en clair
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Identifiant ou mot de passe incorrect", e);
        }
    }*/

    public Person register(RegisterRequest registerRequest){
        if(this.userRepository.existsByEmail(registerRequest.getEmail())){
            throw new RuntimeException("Error : Email is already in use");
        }
        registerRequest.setPassword(this.passwordEncoder.encode(registerRequest.getPassword()));
        if (registerRequest.getEmail().contains("@")  && registerRequest.getEmail().contains(".")){
            User user = new User(registerRequest.getPassword(), registerRequest.getUserName(), registerRequest.getEmail());
            Person person = new Person(registerRequest.getFirstName(),
                    registerRequest.getLastName(), registerRequest.getBirthDate(),
                    registerRequest.getPhoneNumber(), registerRequest.getGender(),
                    registerRequest.getCity(), registerRequest.getAddress());
            Role role = this.roleRepository.findByName(registerRequest.getRole()).orElseThrow(() -> new RuntimeException("Role not found"));
            Set<Role> roles = new HashSet<>();
            if(role.getName() == UserRoleEnum.ADMIN){
                roles.add(role);
                user.setRoles(roles);
                person.setUser(user);
                this.userRepository.save(user);
                person.setHospitalName(registerRequest.getHospitalName());
                return this.personRepository.save(person);
            }else if (role.getName() == UserRoleEnum.SUPER_ADMIN){
                roles.add(role);
                user.setRoles(roles);
                person.setUser(user);
                this.userRepository.save(user);
                return this.personRepository.save(person);
            }else{
                throw new RuntimeException("enter a valid role");
            }
        }
        return null;
    }

    public User readEmail(String email){
        Optional<User> optionalUser = this.userRepository.findByEmail(email);
        return optionalUser.orElseThrow(() -> new RuntimeException("Error: email not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Error: Email not found"));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), Collections.emptyList());
    }

    public Patient registerPatient(PatientDTO patientDTO) {
        if (this.userRepository.existsByEmail(patientDTO.getEmail())) { throw new RuntimeException("Error : Email is already in use"); }
        patientDTO.setPassword(this.passwordEncoder.encode(patientDTO.getPassword()));
        if (patientDTO.getEmail().contains("@") && patientDTO.getEmail().contains(".")) {
            User user = new User(patientDTO.getPassword(), patientDTO.getUserName(), patientDTO.getEmail());
            Patient patient = new Patient(patientDTO.getFirstName(), patientDTO.getLastName(), patientDTO.getBirthDate(), patientDTO.getPhoneNumber(), patientDTO.getGender(), patientDTO.getCity(), patientDTO.getAddress(), patientDTO.getBloodTypeEnum(), patientDTO.getEmergencyContact(), patientDTO.getPatientStatusEnum());
            Set<Role> roles = new HashSet<>();
            Role role = this.roleRepository.findByName(UserRoleEnum.USER).orElseThrow(() -> new RuntimeException("Role not found"));
            roles.add(role);
            user.setRoles(roles);
            patient.setUser(user);
            this.userRepository.save(user);
            return this.patientRepository.save(patient);
        }
        return null;
    }

    public Personnel registerPersonnel(PersonnelDTO personnelDTO) {
        if (this.userRepository.existsByEmail(personnelDTO.getEmail())) {
            throw new RuntimeException("Error : Email is already in use");
        }
        personnelDTO.setPassword(this.passwordEncoder.encode(personnelDTO.getPassword()));

        Role role = this.roleRepository.findByName(personnelDTO.getRole()).orElseThrow(() -> new RuntimeException("Role not found"));

        List<Hospital> hospitals = this.hospitalRepository.findByDeleted(false);
        if (hospitals != null) {
            AtomicReference<Hospital> hospital;
            hospital = new AtomicReference<>(new Hospital());
            hospitals.forEach(hospital1 -> {
                if (Objects.equals(hospital1.getName(), personnelDTO.getHospitalAddress())){
                    hospital.set(hospital1);
                }
            });

            if(role.getName() == UserRoleEnum.DOCTOR){
                List<Speciality> specialities = this.specialityRepository.findByDeleted(false);
                if(specialities != null) {
                    AtomicReference<Speciality> speciality;
                    speciality = new AtomicReference<>(new Speciality());
                    specialities.forEach(speciality1 -> {
                        if (Objects.equals(speciality1.getName(), personnelDTO.getSpeciality())) {
                            speciality.set(speciality1);
                        }
                    });
                    if (hospital.get().getName() == null || speciality.get().getName() == null) {
                        throw new RuntimeException("hospital or speciality not found");
                    }
                    Set<Speciality> specialitySet = hospital.get().getSpecialities();
                    if (specialitySet.contains(speciality.get())) {
                        if (personnelDTO.getEmail().contains("@") && personnelDTO.getEmail().contains(".")) {
                            User user = new User(personnelDTO.getPassword(), personnelDTO.getUserName(), personnelDTO.getEmail());
                            Personnel personnel = new Personnel(personnelDTO.getFirstName(), personnelDTO.getLastName(), personnelDTO.getBirthDate(), personnelDTO.getPhoneNumber(), personnelDTO.getGender(), personnelDTO.getCity(), personnelDTO.getAddress(), personnelDTO.getHireDate(), personnelDTO.getEmployeeNumber());
                            personnel.setSpeciality(speciality.get());
                            personnel.setHospital(hospital.get());
                            Set<Role> roles = new HashSet<>();
                            roles.add(role);
                            user.setRoles(roles);
                            this.userRepository.save(user);
                            personnel.setUser(user);
                            return this.personnelRepository.save(personnel);
                        }
                        return null;
                    }
                    return null;
                }
                return null;
            } else if (role.getName() == UserRoleEnum.SECRETARY){
                if (personnelDTO.getEmail().contains("@") && personnelDTO.getEmail().contains(".")) {
                    User user = new User(personnelDTO.getPassword(), personnelDTO.getUserName(), personnelDTO.getEmail());
                    Personnel personnel = new Personnel(personnelDTO.getFirstName(), personnelDTO.getLastName(), personnelDTO.getBirthDate(), personnelDTO.getPhoneNumber(), personnelDTO.getGender(), personnelDTO.getCity(), personnelDTO.getAddress(), personnelDTO.getHireDate(), personnelDTO.getEmployeeNumber());
                    personnel.setHospital(hospital.get());
                    Set<Role> roles = new HashSet<>();
                    roles.add(role);
                    user.setRoles(roles);
                    this.userRepository.save(user);
                    personnel.setUser(user);
                    return this.personnelRepository.save(personnel);
                }
            }
            return null;
        }
        return null;
    }

    public List<Person> readAdmin() {
        List<Person> persons = this.personRepository.findAll();
        Role adminRole = this.roleRepository.findByName(UserRoleEnum.ADMIN)
                .orElseThrow(() -> new RuntimeException("Role admin not found"));
        Role superAdminRole = this.roleRepository.findByName(UserRoleEnum.SUPER_ADMIN)
                .orElseThrow(() -> new RuntimeException("Role superadmin not found"));

        persons.removeIf(person ->
                !person.getUser().getRoles().contains(adminRole) &&
                        !person.getUser().getRoles().contains(superAdminRole)
        );
        return persons;
    }

    public List<Personnel> readPersonnel() {
        return this.personnelRepository.findAll();
    }

    public List<Patient> readPatient() {
        return this.patientRepository.findAll();
    }

    public List<Personnel> readByHospitalName(String name) {
        List<Hospital> hospitals = this.hospitalRepository.findByDeleted(false);
        AtomicReference<Hospital> hospital;
        hospital = new AtomicReference<>(new Hospital());
        if(hospitals != null){
            hospitals.forEach(hospital1 -> {
                if (Objects.equals(hospital1.getName(), name)){
                    hospital.set(hospital1);
                }
            });
        }
        return this.personnelRepository.findAllByHospital(hospital.get());
    }

    public List<Personnel> readDoctorByHospitalName(String name) {
        List<Hospital> hospitals = this.hospitalRepository.findByDeleted(false);
        AtomicReference<Hospital> hospital = new AtomicReference<>(new Hospital());
        List<Personnel> personnelList = new ArrayList<>();
        if(hospitals != null){
            hospitals.forEach(hospital1 -> {
                if (Objects.equals(hospital1.getName(), name)){
                    hospital.set(hospital1);
                }
            });
            List<Personnel> personnels = this.personnelRepository.findAllByHospital(hospital.get());
            Role role = this.roleRepository.findByName(UserRoleEnum.DOCTOR).orElseThrow(() -> new RuntimeException("role not found"));
            personnels.forEach(personnel -> {
                if (personnel.getUser().getRoles().contains(role)){
                    personnelList.add(personnel);
                }
            });
        }
        return personnelList;
    }

    public List<Person> readAllUser() {
        return this.personRepository.findAll();
    }

    public void toggleAccountStatus(String email, boolean status) {
        this.userRepository.findByEmail(email).ifPresent(user -> {
            user.setActive(status);
            if(status){
                user.setFailedLoginAttempts(0);
                user.setLockTime(null);
            }
            userRepository.save(user);
        });
    }

    public Person addRole(String email, UserRoleEnum role, String hospitalName) {
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        Role role1 = this.roleRepository.findByName(role).orElseThrow(() -> new RuntimeException("role not found"));
        Set<Role> roles = new HashSet<>();
        roles = user.getRoles();
        roles.add(role1);
        if (role1.getName() == UserRoleEnum.ADMIN) {
            List<Hospital> hospitals = this.hospitalRepository.findByDeleted(false);
            Hospital hospital = new Hospital();
            if (hospitals != null) {
                hospitals.forEach(hospital1 -> {
                    if (Objects.equals(hospital1.getName(), hospitalName)) {
                        Person person = this.personRepository.findByUser(user);
                        person.setHospitalName(hospitalName);
                        this.personRepository.save(person);
                    }
                });
            }
        }
        this.userRepository.save(user);
        return this.personRepository.findByUser(user);
    }
    public Person deleteRole(String email, UserRoleEnum role) {
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        Role role1 = this.roleRepository.findByName(role).orElseThrow(() -> new RuntimeException("role not found"));
        Person person = this.personRepository.findByUser(user);
        Set<Role> roles = new HashSet<>();
        roles = user.getRoles();
        if (roles.contains(role1)) {
            if (role1.getName() == UserRoleEnum.ADMIN) {
                roles.remove(role1);
                person.setHospitalName(null);
            } else if (role1.getName() == UserRoleEnum.SUPER_ADMIN) {
                roles.remove(role1);
            }
        }
        user.setRoles(roles);
        this.userRepository.save(user);
        return this.personRepository.findByUser(user);
    }

    public Person getProfile(String email) {
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        return this.personRepository.findByUser(user);
    }
}
