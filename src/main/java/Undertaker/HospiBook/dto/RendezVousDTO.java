package Undertaker.HospiBook.dto;

import Undertaker.HospiBook.model.enums.StatusRDVEnum;
import Undertaker.HospiBook.model.enums.VisitTypeEnum;

import java.time.LocalDateTime;

public class RendezVousDTO {
    private String reason;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private VisitTypeEnum type;
    private String addressHospital;
    private String emailPatient;
    private String emailDoctor;
    private String speciality;
    private StatusRDVEnum status;

    public RendezVousDTO(String reason, LocalDateTime startTime, LocalDateTime endTime, VisitTypeEnum type, String addressHospital, String emailPatient, String emailDoctor, String speciality, StatusRDVEnum status) {
        this.reason = reason;
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
        this.addressHospital = addressHospital;
        this.emailPatient = emailPatient;
        this.emailDoctor = emailDoctor;
        this.speciality = speciality;
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    public String getAddressHospital() {
        return addressHospital;
    }

    public void setAddressHospital(String addressHospital) {
        this.addressHospital = addressHospital;
    }

    public String getEmailPatient() {
        return emailPatient;
    }

    public void setEmailPatient(String emailPatient) {
        this.emailPatient = emailPatient;
    }

    public String getEmailDoctor() {
        return emailDoctor;
    }

    public void setEmailDoctor(String emailDoctor) {
        this.emailDoctor = emailDoctor;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public StatusRDVEnum getStatus() {
        return status;
    }

    public void setStatus(StatusRDVEnum status) {
        this.status = status;
    }
}
