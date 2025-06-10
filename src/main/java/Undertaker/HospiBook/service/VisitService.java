package Undertaker.HospiBook.service;

import Undertaker.HospiBook.dto.MedicalRecordDTO;
import Undertaker.HospiBook.dto.PaymentDTO;
import Undertaker.HospiBook.dto.VisitDTO;
import Undertaker.HospiBook.model.entities.*;
import Undertaker.HospiBook.model.enums.UserRoleEnum;
import Undertaker.HospiBook.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class VisitService {
    private final VisitRepository visitRepository;
    private final InvoiceRepository invoiceRepository;
    private final HospitalRepository hospitalRepository;
    private final PatientRepository patientRepository;
    private final PersonnelRepository personnelRepository;
    private final SpecialityRepository specialityRepository;
    private final VisitFreeRepository visitFreeRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final PaymentRepository paymentRepository;

    public VisitService(VisitRepository visitRepository, InvoiceRepository invoiceRepository, HospitalRepository hospitalRepository, PatientRepository patientRepository, PersonnelRepository personnelRepository, SpecialityRepository specialityRepository, VisitFreeRepository visitFreeRepository, UserRepository userRepository, RoleRepository roleRepository, MedicalRecordRepository medicalRecordRepository, PaymentRepository paymentRepository) {
        this.visitRepository = visitRepository;
        this.invoiceRepository = invoiceRepository;
        this.hospitalRepository = hospitalRepository;
        this.patientRepository = patientRepository;
        this.personnelRepository = personnelRepository;
        this.specialityRepository = specialityRepository;
        this.visitFreeRepository = visitFreeRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        this.paymentRepository = paymentRepository;
    }

    public Visit create(VisitDTO visitDTO) {
        List<Hospital> hospitals = this.hospitalRepository.findByDeleted(false);
        AtomicReference<Hospital> hospital = new AtomicReference<>(new Hospital());
        hospitals.forEach(hospital1 -> {
            if (Objects.equals(hospital1.getName(), visitDTO.getAddressHospital())){
                hospital.set(hospital1);
            }
        });
        //Hospital hospital = this.hospitalRepository.findByAddress(visitDTO.getAddressHospital()).orElseThrow(() -> new RuntimeException("hospital not found"));
        User userPatient = this.userRepository.findByEmail(visitDTO.getEmailPatient()).orElseThrow(() -> new RuntimeException("user patient not found"));
        User userDoctor = this.userRepository.findByEmail(visitDTO.getEmailDoctor()).orElseThrow(() -> new RuntimeException("user Doctor not found"));
        Patient patient = this.patientRepository.findByUser(userPatient).orElseThrow(() -> new RuntimeException("This person is not a patient"));
        Personnel personnel = this.personnelRepository.findByUser(userDoctor).orElseThrow(() -> new RuntimeException("this person is not a personnel"));
        Speciality speciality = this.specialityRepository.findByName(visitDTO.getSpeciality()).orElseThrow(() -> new RuntimeException("speciality not found"));

        Role role = this.roleRepository.findByName(UserRoleEnum.DOCTOR).orElseThrow(() -> new RuntimeException("Role Doctor not found"));
        if(!userDoctor.getRoles().contains(role)){
            return null;
        }

        VisitFree visitFree = speciality.getVisitFree();
        Invoice invoice = new Invoice(new Date(System.currentTimeMillis()));
        invoice.setVisitFree(visitFree);
        this.invoiceRepository.save(invoice);
        Visit visit = new Visit(visitDTO.getReason(), visitDTO.getStartTime(), visitDTO.getEndTime(), visitDTO.getType(), patient, personnel, hospital.get(), visitFree, invoice);
        return this. visitRepository.save(visit);
    }

    @Transactional
    public List<Visit> read() {
        return this.visitRepository.findAllByDeleted(false);
    }

    public Visit readById(Long id) {
        Optional<Visit> visit = this.visitRepository.findById(id);
        if (visit.isEmpty() || visit.get().isDeleted()){
            return null;
        }
        return visit.get();
    }

    public Visit update(Long id, VisitDTO visitDTO) {

        Visit visit = this.readById(id);
        if(visit == null){
            return null;
        }

        Hospital hospital = this.hospitalRepository.findByAddress(visitDTO.getAddressHospital()).orElseThrow(() -> new RuntimeException("hospital not found"));
        User userPatient = this.userRepository.findByEmail(visitDTO.getEmailPatient()).orElseThrow(() -> new RuntimeException("user patient not found"));
        User userDoctor = this.userRepository.findByEmail(visitDTO.getEmailDoctor()).orElseThrow(() -> new RuntimeException("user Doctor not found"));
        Patient patient = this.patientRepository.findByUser(userPatient).orElseThrow(() -> new RuntimeException("This person is not a patient"));
        Personnel personnel = this.personnelRepository.findByUser(userDoctor).orElseThrow(() -> new RuntimeException("this person is not a personnel"));
        Speciality speciality = this.specialityRepository.findByName(visitDTO.getSpeciality()).orElseThrow(() -> new RuntimeException("speciality not found"));

        Role role = this.roleRepository.findByName(UserRoleEnum.DOCTOR).orElseThrow(() -> new RuntimeException("Role Doctor not found"));
        if(!userDoctor.getRoles().contains(role)){
            return null;
        }

        VisitFree visitFree = speciality.getVisitFree();
        Invoice invoice = visit.getInvoice();
        invoice.setDate(new Date(System.currentTimeMillis()));
        invoice.setVisitFree(visitFree);
        this.invoiceRepository.save(invoice);
        visit.setHospital(hospital);
        visit.setPatient(patient);
        visit.setInvoice(invoice);
        visit.setPersonnel(personnel);
        visit.setVisitFree(visitFree);
        visit.setEndTime(visitDTO.getEndTime());
        visit.setStartTime(visitDTO.getStartTime());
        visit.setReason(visitDTO.getReason());
        visit.setType(visitDTO.getType());
        return this.visitRepository.save(visit);
    }

    public String delete(Long id) {
        Optional<Visit> visit = this.visitRepository.findById(id);
        if (visit.isEmpty() || visit.get().isDeleted()){
            return "Visit doesn't exists";
        }
        visit.get().setDeleted(true);
        Invoice invoice = visit.get().getInvoice();
        this.visitRepository.delete(visit.get());
        return "visit deleted successful";
    }

    public List<Visit> getByEmailPatient(String email) {
        List<Visit> visits = this.visitRepository.findAllByDeleted(false);
        List<Visit> visitList = new ArrayList<>();
        visits.forEach(visit -> {
            if (Objects.equals(visit.getPatient().getUser().getEmail(), email) && !(visit instanceof RendezVous)){
                visitList.add(visit);
            }
        });
        return visitList;
    }

    public List<Visit> getVisitByHospitalName(String name) {
        List<Hospital> hospitals = this.hospitalRepository.findByDeleted(false);
        AtomicReference<Hospital> hospital = new AtomicReference<>(new Hospital());
        if (hospitals != null) {
            hospitals.forEach(hospital1 -> {
                if (Objects.equals(hospital1.getName(), name)){
                    hospital.set(hospital1);
                }
            });

            if (hospital.get().getName() == null){
                throw new RuntimeException("hospital not found");
            }

            return this.visitRepository.findByHospital(hospital.get());
        }
        return null;
    }

    public Visit createOrUpdateMedicalRecord(Long id, MedicalRecordDTO medicalRecordDTO) {
        Visit visit = this.readById(id);
        if (visit == null) {
            return null;
        }

        MedicalRecord medicalRecord;
        if (visit.getMedicalRecord() != null) {
            medicalRecord = visit.getMedicalRecord();
            medicalRecord.setTemperature(medicalRecordDTO.getTemperature());
            medicalRecord.setBloodPresure(medicalRecordDTO.getBloodPressure());
            medicalRecord.setHeight(medicalRecordDTO.getHeight());
            medicalRecord.setWeight(medicalRecord.getWeight());
        } else {
            medicalRecord = new MedicalRecord(medicalRecordDTO.getWeight(), medicalRecordDTO.getHeight(), medicalRecordDTO.getBloodPressure(), medicalRecordDTO.getTemperature());
        }
        visit.setMedicalRecord(medicalRecord);
        this.medicalRecordRepository.save(medicalRecord);
        return this.visitRepository.save(visit);
    }

    public Visit createOrUpdatePayment(Long id, PaymentDTO paymentDTO) {
        Visit visit = this.readById(id);
        if (visit == null) {
            return null;
        }

        Payment payment;
        if (visit.getInvoice().getPayment() != null) {
            payment = visit.getInvoice().getPayment();
            payment.setMethod(paymentDTO.getMethod());
            payment.setDate(LocalDateTime.now());
            payment.setStatus(paymentDTO.getPaymentStatusEnum());
        } else {
            payment = new Payment(paymentDTO.getMethod(), paymentDTO.getPaymentStatusEnum(), LocalDateTime.now());
        }
        this.paymentRepository.save(payment);
        Invoice invoice = visit.getInvoice();
        invoice.setPayment(payment);
        this.invoiceRepository.save(invoice);
        visit.setInvoice(invoice);
        return this.visitRepository.save(visit);
    }
}
