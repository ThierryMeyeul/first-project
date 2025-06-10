package Undertaker.HospiBook.dto;

import Undertaker.HospiBook.model.enums.BloodTypeEnum;
import Undertaker.HospiBook.model.enums.GenderEnum;
import Undertaker.HospiBook.model.enums.PatientStatusEnum;
import jakarta.validation.constraints.Email;

import java.util.Date;

public class PatientDTO {
    private BloodTypeEnum bloodTypeEnum;
    private PatientStatusEnum patientStatusEnum;
    private String emergencyContact;
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

    public PatientDTO(BloodTypeEnum bloodTypeEnum, PatientStatusEnum patientStatusEnum, String emergencyContact, String userName, String email, String password, String firstName, String lastName, Date birthDate, String city, String phoneNumber, String address, GenderEnum gender) {
        this.bloodTypeEnum = bloodTypeEnum;
        this.patientStatusEnum = patientStatusEnum;
        this.emergencyContact = emergencyContact;
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

    public BloodTypeEnum getBloodTypeEnum() {
        return bloodTypeEnum;
    }

    public void setBloodTypeEnum(BloodTypeEnum bloodTypeEnum) {
        this.bloodTypeEnum = bloodTypeEnum;
    }

    public PatientStatusEnum getPatientStatusEnum() {
        return patientStatusEnum;
    }

    public void setPatientStatusEnum(PatientStatusEnum patientStatusEnum) {
        this.patientStatusEnum = patientStatusEnum;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
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
