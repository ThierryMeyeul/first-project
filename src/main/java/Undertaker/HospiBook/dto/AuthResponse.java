package Undertaker.HospiBook.dto;

import Undertaker.HospiBook.model.entities.Payment;
import Undertaker.HospiBook.model.entities.Role;
import Undertaker.HospiBook.model.enums.UserRoleEnum;

import java.util.List;
import java.util.Set;

public class AuthResponse {
    private Long id;
    private String username;
    private String email;
    private String token;
    private List<UserRoleEnum> roles;
    private String hospitalName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AuthResponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<UserRoleEnum> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRoleEnum> roles) {
        this.roles = roles;
    }

    public AuthResponse(Long id, String username, String email, String token, String hospitalName) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.token = token;
        this.hospitalName = hospitalName;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }
}
