package Undertaker.HospiBook.dto;

import Undertaker.HospiBook.model.entities.Hospital;
import Undertaker.HospiBook.model.enums.GenderEnum;
import Undertaker.HospiBook.model.enums.UserRoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

import java.util.Date;
import java.util.Set;

public class RegisterRequest {
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
    private String hospitalName;
    private UserRoleEnum role;

    public RegisterRequest(String userName, String email, String password, String firstName, String lastName, Date birthDate, String city, String phoneNumber, String address, GenderEnum gender, UserRoleEnum role) {
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
        this.role = role;
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

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public UserRoleEnum getRole() {
        return role;
    }

    public void setRole(UserRoleEnum role) {
        this.role = role;
    }
}
