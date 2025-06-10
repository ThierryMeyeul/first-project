package Undertaker.HospiBook.service;

import Undertaker.HospiBook.dto.AvailabilitySlotDTO;
import Undertaker.HospiBook.model.entities.*;
import Undertaker.HospiBook.model.enums.UserRoleEnum;
import Undertaker.HospiBook.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AvailabilitySlotService {
    private final AvailabilitySlotRepository availabilitySlotRepository;
    private final UserRepository userRepository;
    private final PersonRepository personRepository;
    private final PersonnelRepository personnelRepository;
    private final RoleRepository roleRepository;

    public AvailabilitySlotService(AvailabilitySlotRepository availabilitySlotRepository, UserRepository userRepository, PersonRepository personRepository, PersonnelRepository personnelRepository, RoleRepository roleRepository) {
        this.availabilitySlotRepository = availabilitySlotRepository;
        this.userRepository = userRepository;
        this.personRepository = personRepository;
        this.personnelRepository = personnelRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public AvailabilitySlot create(AvailabilitySlotDTO availabilitySlotDTO) {
        User user = this.userRepository.findByEmail(availabilitySlotDTO.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
        Person person = this.personRepository.findByUser(user);
        Personnel personnel = this.personnelRepository.findById(person.getId()).orElseThrow(() -> new RuntimeException("personnel not found"));
        Optional<Role> role = this.roleRepository.findByName(UserRoleEnum.DOCTOR);
        if(person.getUser().getRoles().contains(role.get())){
            AvailabilitySlot availabilitySlot = new AvailabilitySlot(Date.from(Instant.now()), availabilitySlotDTO.getStartTime(), availabilitySlotDTO.getEndTime(), personnel);
            return this.availabilitySlotRepository.save(availabilitySlot);
        }
        return null;
    }

    public AvailabilitySlot update(Long id, AvailabilitySlotDTO availabilitySlotDTO) {
        AvailabilitySlot availabilitySlot = this.readById(id);
        if(availabilitySlot != null){
            User user = this.userRepository.findByEmail(availabilitySlotDTO.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
            Personnel personnel = this.personnelRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Personnel not found"));
            Role role = this.roleRepository.findByName(UserRoleEnum.DOCTOR).orElseThrow(() -> new RuntimeException("Role not found"));
            if (personnel.getUser().getRoles().contains(role)) {
                availabilitySlot.setAvailability(false);
                availabilitySlot.setStartTime(availabilitySlot.getStartTime());
                availabilitySlot.setEndTime(availabilitySlot.getEndTime());
                return this.availabilitySlotRepository.save(availabilitySlot);
            } else {
                return null;
            }
        }
        return null;
    }

    public String delete(Long id) {
        AvailabilitySlot availabilitySlot = this.availabilitySlotRepository.findById(id).orElseThrow(() -> new RuntimeException("availability slot not found"));
        availabilitySlot.setDeleted(true);
        this.availabilitySlotRepository.save(availabilitySlot);
        return "availability slot deleted successful";
    }

    public List<AvailabilitySlot> readByDoctor(String doctorEmail, LocalDate date) {
        User user = this.userRepository.findByEmail(doctorEmail).orElseThrow(() -> new RuntimeException("User not found"));
        Personnel personnel = this.personnelRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Personnel not found"));
        Role role = this.roleRepository.findByName(UserRoleEnum.DOCTOR).orElseThrow(() -> new RuntimeException("Role not found"));

        if (!personnel.getUser().getRoles().contains(role)){
            return null;
        }

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);

        return availabilitySlotRepository.findDoctorAndDateTimeRange(personnel.getId(), startOfDay, endOfDay, true);
    }

    public AvailabilitySlot readById(Long id) {
        AvailabilitySlot availabilitySlot = this.availabilitySlotRepository.findById(id).orElseThrow(() -> new RuntimeException("Availability slot not found"));
        if (availabilitySlot.isDeleted()){
            return null;
        }
        return availabilitySlot;
    }

    public List<AvailabilitySlot> createSlots(String email, List<AvailabilitySlotDTO> availabilitySlotDTOS) {
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        Personnel personnel = this.personnelRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Personnel not found"));
        List<AvailabilitySlot> slots = availabilitySlotDTOS.stream().map(dto -> {
            return new AvailabilitySlot(Date.from(Instant.now()), dto.getStartTime(), dto.getEndTime(), personnel);
        }).toList();
        return this.availabilitySlotRepository.saveAll(slots);
    }

    public List<AvailabilitySlot> readSlots(String email) {
        List<AvailabilitySlot> slots = this.availabilitySlotRepository.findAllByDeleted(false);
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        Personnel personnel = this.personnelRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Personnel not found"));
        List<AvailabilitySlot> availabilitySlots = new ArrayList<>();
        slots.forEach(availabilitySlot -> {
            if (personnel.equals(availabilitySlot.getPersonnel())){
                availabilitySlots.add(availabilitySlot);
            }
        });
        return availabilitySlots;
    }

    public List<AvailabilitySlot> readSlotByDate(LocalDateTime date) {
        return this.availabilitySlotRepository.findByStartTime(date);
    }
}
