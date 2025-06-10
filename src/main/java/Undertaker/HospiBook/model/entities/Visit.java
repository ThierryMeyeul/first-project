package Undertaker.HospiBook.model.entities;

import Undertaker.HospiBook.model.audit.BaseEntity;
import jakarta.persistence.*;
import Undertaker.HospiBook.model.enums.VisitTypeEnum;

import java.time.LocalDateTime;

@Entity
@Table(name = "Visit")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
public class Visit extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VisitTypeEnum type;

    @Column(nullable = false)
    private boolean deleted;

    @Version
    private Long version;

    @ManyToOne
    @JoinColumn(name = "patientId")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "peronnelId")
    private Personnel personnel;

    @ManyToOne
    @JoinColumn(name = "HospitalId")
    private Hospital hospital;

    @ManyToOne
    @JoinColumn(name = "visitFreeId")
    private VisitFree visitFree;

    @OneToOne
    @JoinColumn(name = "invoiceId")
    private Invoice invoice;

    @OneToOne
    @JoinColumn(name = "medicalRecordId")
    private MedicalRecord medicalRecord;

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Visit(String reason, LocalDateTime startTime, LocalDateTime endTime, VisitTypeEnum type, Patient patient, Personnel personnel, Hospital hospital, VisitFree visitFree, Invoice invoice) {
        this.reason = reason;
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
        this.patient = patient;
        this.personnel = personnel;
        this.hospital = hospital;
        this.visitFree = visitFree;
        this.invoice = invoice;
        this.deleted = false;
    }

    public Visit() {
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public VisitTypeEnum getType() {
        return type;
    }

    public void setType(VisitTypeEnum type) {
        this.type = type;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public VisitFree getVisitFree() {
        return visitFree;
    }

    public void setVisitFree(VisitFree visitFree) {
        this.visitFree = visitFree;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}
