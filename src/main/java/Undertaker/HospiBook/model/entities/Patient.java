package Undertaker.HospiBook.model.entities;

import Undertaker.HospiBook.model.enums.GenderEnum;
import jakarta.persistence.*;

import Undertaker.HospiBook.model.enums.BloodTypeEnum;
import Undertaker.HospiBook.model.enums.PatientStatusEnum;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Patient")
public class Patient extends Person{
    @Enumerated(EnumType.STRING)
    private BloodTypeEnum bloodTypeEnum;

    @Column(nullable = false)
    private String emergencyContact;

    @Enumerated(EnumType.STRING)
    private PatientStatusEnum status;

    @OneToMany(mappedBy = "patient")
    private List<Visit> Visits;

    public Patient(String firstName, String lastName, Date birthDate, String phoneNumber, GenderEnum genderEnum, String city, String address, BloodTypeEnum bloodType, String emergencyContact, PatientStatusEnum status) {
        super(firstName, lastName, birthDate, phoneNumber, genderEnum, city, address);
        this.bloodTypeEnum = bloodType;
        this.emergencyContact = emergencyContact;
        this.status = status;
    }

    public Patient(){}

    public BloodTypeEnum getBloodType() {
        return bloodTypeEnum;
    }

    public void setBloodType(BloodTypeEnum bloodTypeEnum) {
        this.bloodTypeEnum = bloodTypeEnum;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public PatientStatusEnum getStatus() {
        return status;
    }

    public void setStatus(PatientStatusEnum status) {
        this.status = status;
    }
}
