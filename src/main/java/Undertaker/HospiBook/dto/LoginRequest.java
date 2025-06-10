package Undertaker.HospiBook.dto;

public record LoginRequest(String email, String password) {
    @Override
    public String email() {
        return email;
    }

    @Override
    public String password() {
        return password;
    }
}
