package Undertaker.HospiBook.service;

import Undertaker.HospiBook.dto.MedicalRecordDTO;
import Undertaker.HospiBook.dto.RendezVousDTO;
import Undertaker.HospiBook.dto.VisitDTO;
import Undertaker.HospiBook.model.entities.*;
import Undertaker.HospiBook.model.enums.PaymentStatusEnum;
import Undertaker.HospiBook.model.enums.StatusRDVEnum;
import Undertaker.HospiBook.model.enums.UserRoleEnum;
import Undertaker.HospiBook.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class RendezVousService {
    private final InvoiceRepository invoiceRepository;
    private final HospitalRepository hospitalRepository;
    private final PatientRepository patientRepository;
    private final PersonnelRepository personnelRepository;
    private final SpecialityRepository specialityRepository;
    private final VisitFreeRepository visitFreeRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RendezVousRepository rendezVousRepository;
    private final MedicalRecordRepository medicalRecordRepository;

    public RendezVousService(InvoiceRepository invoiceRepository, HospitalRepository hospitalRepository, PatientRepository patientRepository, PersonnelRepository personnelRepository, SpecialityRepository specialityRepository, VisitFreeRepository visitFreeRepository, UserRepository userRepository, RoleRepository roleRepository, RendezVousRepository rendezVousRepository, MedicalRecordRepository medicalRecordRepository) {
        this.invoiceRepository = invoiceRepository;
        this.hospitalRepository = hospitalRepository;
        this.patientRepository = patientRepository;
        this.personnelRepository = personnelRepository;
        this.specialityRepository = specialityRepository;
        this.visitFreeRepository = visitFreeRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.rendezVousRepository = rendezVousRepository;
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public RendezVous create(VisitDTO visitDTO) {
        List<Hospital> hospitals = this.hospitalRepository.findByDeleted(false);
        if (hospitals != null) {
            AtomicReference<Hospital> hospital;
            hospital = new AtomicReference<>(new Hospital());
            hospitals.forEach(hospital1 -> {
                if (Objects.equals(hospital1.getName(), visitDTO.getAddressHospital())){
                    hospital.set(hospital1);
                }
            });
            List<Speciality> specialities = this.specialityRepository.findByDeleted(false);
            if (specialities != null) {
                AtomicReference<Speciality> speciality;
                speciality = new AtomicReference<>(new Speciality());
                specialities.forEach(speciality1 -> {
                    if (Objects.equals(speciality1.getName(), visitDTO.getSpeciality())){
                        speciality.set(speciality1);
                    }
                });
                if (hospital.get().getName() == null){
                    throw new RuntimeException("hospital is null");
                }
                User userPatient = this.userRepository.findByEmail(visitDTO.getEmailPatient()).orElseThrow(() -> new RuntimeException("user patient not found"));
                Personnel personnel = new Personnel();
                if (!Objects.equals(visitDTO.getEmailDoctor(), "")) {
                    User userDoctor = this.userRepository.findByEmail(visitDTO.getEmailDoctor()).orElseThrow(() -> new RuntimeException("user Doctor not found"));
                    personnel = this.personnelRepository.findByUser(userDoctor).orElseThrow(() -> new RuntimeException("this person is not a personnel"));
                    Role role = this.roleRepository.findByName(UserRoleEnum.DOCTOR).orElseThrow(() -> new RuntimeException("Role Doctor not found"));
                    if(!userDoctor.getRoles().contains(role)){
                        throw new RuntimeException("this personnel is not a doctor");
                    }
                } else {
                    personnel = null;
                }
                
                Patient patient = this.patientRepository.findByUser(userPatient).orElseThrow(() -> new RuntimeException("This person is not a patient"));
                VisitFree visitFree = speciality.get().getVisitFree();
                Invoice invoice = new Invoice(new Date(System.currentTimeMillis()));
                invoice.setVisitFree(visitFree);
                this.invoiceRepository.save(invoice);
                RendezVous rendezVous = new RendezVous(visitDTO.getReason(), visitDTO.getStartTime(), visitDTO.getEndTime(), visitDTO.getType(), patient, personnel, hospital.get(), visitFree, invoice, visitDTO.getStartTime());
                rendezVous.setStatus(StatusRDVEnum.REQUEST);
                return this.rendezVousRepository.save(rendezVous);
            }
            return null;
        }
        return null;
    }

    public List<RendezVous> read() {
        return this.rendezVousRepository.findAllByDeleted(false);
    }

    public RendezVous readId(Long id) {
        RendezVous rendezVous = this.rendezVousRepository.findById(id).orElseThrow(() -> new RuntimeException("Rendez Vous not found"));
        if (rendezVous.isDeleted()){
            return null;
        }
        return rendezVous;
    }

    public RendezVous update(Long id, RendezVousDTO rendezVousDTO) {
        RendezVous rendezVous = this.readId(id);
        if(rendezVous == null){
            return null;
        }
        List<Hospital> hospitals = this.hospitalRepository.findByDeleted(false);
        if (hospitals != null) {
            AtomicReference<Hospital> hospital;
            hospital = new AtomicReference<>(new Hospital());
            hospitals.forEach(hospital1 -> {
                if (Objects.equals(hospital1.getName(), rendezVousDTO.getAddressHospital())) {
                    hospital.set(hospital1);
                }
            });
            List<Speciality> specialities = this.specialityRepository.findByDeleted(false);
            if (specialities != null) {
                AtomicReference<Speciality> speciality;
                speciality = new AtomicReference<>(new Speciality());
                specialities.forEach(speciality1 -> {
                    if (Objects.equals(speciality1.getName(), rendezVousDTO.getSpeciality())) {
                        speciality.set(speciality1);
                    }
                });
                if (hospital.get().getName() == null || speciality.get().getName() == null) {
                    throw new RuntimeException("hospital or speciality is null");
                }
                User userPatient = this.userRepository.findByEmail(rendezVousDTO.getEmailPatient()).orElseThrow(() -> new RuntimeException("user patient not found"));
                User userDoctor = this.userRepository.findByEmail(rendezVousDTO.getEmailDoctor()).orElseThrow(() -> new RuntimeException("user Doctor not found"));
                Patient patient = this.patientRepository.findByUser(userPatient).orElseThrow(() -> new RuntimeException("This person is not a patient"));
                Personnel personnel = this.personnelRepository.findByUser(userDoctor).orElseThrow(() -> new RuntimeException("this person is not a personnel"));

                Role role = this.roleRepository.findByName(UserRoleEnum.DOCTOR).orElseThrow(() -> new RuntimeException("Role Doctor not found"));
                if(!userDoctor.getRoles().contains(role)){
                    throw new RuntimeException("this personnel is not a doctor");
                }
                VisitFree visitFree = speciality.get().getVisitFree();
                Invoice invoice = rendezVous.getInvoice();
                invoice.setDate(new Date(System.currentTimeMillis()));
                invoice.setVisitFree(visitFree);
                this.invoiceRepository.save(invoice);

                rendezVous.setStatus(rendezVousDTO.getStatus());
                rendezVous.setDateRDV(rendezVousDTO.getStartTime());
                rendezVous.setHospital(hospital.get());
                rendezVous.setEndTime(rendezVousDTO.getEndTime());
                rendezVous.setInvoice(invoice);
                rendezVous.setPatient(patient);
                rendezVous.setPersonnel(personnel);
                rendezVous.setReason(rendezVousDTO.getReason());
                rendezVous.setStartTime(rendezVousDTO.getStartTime());
                rendezVous.setType(rendezVousDTO.getType());
                rendezVous.setVisitFree(visitFree);
                return this.rendezVousRepository.save(rendezVous);
            }
            return null;
        }
        return null;
    }

    public String delete(Long id) {
        RendezVous rendezVous = this.readId(id);
        if (rendezVous == null) {
            return "rendez vous doesn't exists";
        }
        rendezVous.setDeleted(true);
        this.rendezVousRepository.save(rendezVous);
        return "rendez vous deleted";
    }

    public List<RendezVous> upcomingAppointment(String email) {
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Patient not found"));
        Patient patient = this.patientRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Patient not found"));
        List<RendezVous> rendezVous = this.rendezVousRepository.findAllByDeleted(false);
        List<RendezVous> rendezVousList = new ArrayList<>();
        if (rendezVous != null){
            rendezVous.forEach(rendezVous1 -> {
                if (patient.equals(rendezVous1.getPatient()) && rendezVous1.getStartTime().isAfter(LocalDateTime.now()) && rendezVous1.getStatus() == StatusRDVEnum.CONFIRM){
                    rendezVousList.add(rendezVous1);
                }
            });
        }
        return rendezVousList;
    }

    public List<RendezVous> passedAppointment(String email) {
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Patient not found"));
        Patient patient = this.patientRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Patient not found"));
        List<RendezVous> rendezVous = this.rendezVousRepository.findAllByDeleted(false);
        List<RendezVous> rendezVousList = new ArrayList<>();
        if (rendezVous != null){
            rendezVous.forEach(rendezVous1 -> {
                if (patient.equals(rendezVous1.getPatient()) && rendezVous1.getStartTime().isBefore(LocalDateTime.now())){
                    rendezVousList.add(rendezVous1);
                }
            });
        }
        return rendezVousList;
    }

    public List<RendezVous> readByPersonnelAppointmentRequest(String email) {
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("user not found"));
        Personnel personnel = this.personnelRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Personnel not found"));
        List<RendezVous> rendezVous = this.rendezVousRepository.findAllByDeleted(false);
        List<RendezVous> rendezVousList = new ArrayList<>();
        if (rendezVous != null) {
            rendezVous.forEach(rendezVous1 -> {
                if (personnel.equals(rendezVous1.getPersonnel()) && rendezVous1.getStartTime().isAfter(LocalDateTime.now()) && rendezVous1.getStatus() == StatusRDVEnum.REQUEST) {
                    rendezVousList.add(rendezVous1);
                }
            });
        }
        return rendezVousList;
    }

    public List<RendezVous> readByAppointment(String email) {
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("user not found"));
        Personnel personnel = this.personnelRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Personnel not found"));
        List<RendezVous> rendezVous = this.rendezVousRepository.findAllByDeleted(false);
        List<RendezVous> rendezVousList = new ArrayList<>();
        if (rendezVous != null) {
            rendezVous.forEach(rendezVous1 -> {
                if (personnel.equals(rendezVous1.getPersonnel()) && rendezVous1.getStartTime().isAfter(LocalDateTime.now()) && rendezVous1.getStatus() == StatusRDVEnum.CONFIRM && rendezVous1.getInvoice().getPayment() != null) {
                    rendezVousList.add(rendezVous1);
                }
            });
        }
        return rendezVousList;
    }

    public List<RendezVous> readBySecretaryAppointmentRequest(String email) {
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("user not found"));
        Personnel personnel = this.personnelRepository.findByUser(user).orElseThrow(() -> new RuntimeException("personnel not found"));
        Role role = this.roleRepository.findByName(UserRoleEnum.SECRETARY).orElseThrow(() -> new RuntimeException("role not found"));
        if (user.getRoles().contains(role)){

            List<RendezVous> rendezVous = this.rendezVousRepository.findAllByDeleted(false);
            List<RendezVous> rendezVousList = new ArrayList<>();
            if (rendezVous != null) {
                rendezVous.forEach(rendezVous1 -> {
                    if (rendezVous1.getPersonnel() == null && rendezVous1.getStartTime().isAfter(LocalDateTime.now()) && rendezVous1.getStatus() == StatusRDVEnum.REQUEST && rendezVous1.getHospital() == personnel.getHospital()) {
                        rendezVousList.add(rendezVous1);
                    }
                });
            }
            return rendezVousList;
        }
        return null;
    }

    public List<RendezVous> listRDV(String email) {
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Patient not found"));
        Patient patient = this.patientRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Patient not found"));
        List<RendezVous> rendezVous = this.rendezVousRepository.findAllByDeleted(false);
        List<RendezVous> rendezVousList = new ArrayList<>();
        if (rendezVous != null){
            rendezVous.forEach(rendezVous1 -> {
                if (patient.equals(rendezVous1.getPatient()) && rendezVous1.getStartTime().isAfter(LocalDateTime.now())){
                    rendezVousList.add(rendezVous1);
                }
            });
        }
        return rendezVousList;
    }

}
