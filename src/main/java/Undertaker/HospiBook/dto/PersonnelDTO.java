package Undertaker.HospiBook.dto;

import Undertaker.HospiBook.model.enums.GenderEnum;
import Undertaker.HospiBook.model.enums.UserRoleEnum;
import jakarta.validation.constraints.Email;

import java.util.Date;

public class PersonnelDTO {
    private Date hireDate;
    private String employeeNumber;
    private UserRoleEnum role;
    private String hospitalAddress;
    private String speciality;
    private String userName;
    @Email(message = "L'email doit être valide")
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String city;
    private String phoneNumber;
    private String address;
    private GenderEnum gender;

    public PersonnelDTO(Date hireDate, String employeeNumber, UserRoleEnum role, String hospitalAddress, String speciality, String userName, String email, String password, String firstName, String lastName, Date birthDate, String city, String phoneNumber, String address, GenderEnum gender) {
        this.hireDate = hireDate;
        this.employeeNumber = employeeNumber;
        this.role = role;
        this.hospitalAddress = hospitalAddress;
        this.speciality = speciality;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.gender = gender;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public UserRoleEnum getRole() {
        return role;
    }

    public void setRole(UserRoleEnum role) {
        this.role = role;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }
}