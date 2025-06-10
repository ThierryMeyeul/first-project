package Undertaker.HospiBook.dto;

import java.time.LocalDateTime;
import java.util.Date;

public class AvailabilitySlotDTO {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String email;

    public AvailabilitySlotDTO(LocalDateTime startTime, LocalDateTime endTime, String email) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
