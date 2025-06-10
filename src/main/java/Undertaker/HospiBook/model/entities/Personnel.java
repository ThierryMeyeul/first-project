package Undertaker.HospiBook.model.entities;


import Undertaker.HospiBook.model.enums.BloodTypeEnum;
import Undertaker.HospiBook.model.enums.GenderEnum;
import Undertaker.HospiBook.model.enums.PatientStatusEnum;
import jakarta.persistence.*;

import java.util.Date;

import java.util.List;

@Entity
@Table(name = "Personnel")
public class Personnel extends Person{
    @Column(nullable = false)
    private Date hireDate;

    @Column(nullable = false)
    private String EmployeeNumber;

    @OneToMany(mappedBy = "personnel")
    private List<Visit> Visits;

    @OneToMany(mappedBy = "personnel")
    private List<AvailabilitySlot> availabilitySlots;

    @ManyToOne
    @JoinColumn(name = "hospitalId")
    private Hospital hospital;

    @ManyToOne
    @JoinColumn(name = "specialityId")
    private Speciality speciality;

    public Personnel(String firstName, String lastName, Date birthDate, String phoneNumber, GenderEnum genderEnum, String city, String address, Date hireDate, String employeeNumber) {
        super(firstName, lastName, birthDate, phoneNumber, genderEnum, city, address);
        this.hireDate = hireDate;
        this.EmployeeNumber = employeeNumber;
    }

    public Personnel() {

    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public String getEmployeeNumber() {
        return EmployeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        EmployeeNumber = employeeNumber;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }
}
