package Undertaker.HospiBook.dto;

import Undertaker.HospiBook.model.enums.NotificationTypeEnum;

import java.time.LocalDateTime;

public class NotificationCreateDTO {
    private String title;
    private String text;
    private NotificationTypeEnum type;
    private LocalDateTime validFrom;
    private LocalDateTime validTo;
    private String emailUser;

    public NotificationCreateDTO(String title, String text, NotificationTypeEnum type, LocalDateTime validFrom, LocalDateTime validTo, String emailUser) {
        this.title = title;
        this.text = text;
        this.type = type;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.emailUser = emailUser;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public NotificationTypeEnum getType() {
        return type;
    }

    public void setType(NotificationTypeEnum type) {
        this.type = type;
    }

    public LocalDateTime getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDateTime validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDateTime getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDateTime validTo) {
        this.validTo = validTo;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }
}
