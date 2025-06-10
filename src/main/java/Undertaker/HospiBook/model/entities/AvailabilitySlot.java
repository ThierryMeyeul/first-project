package Undertaker.HospiBook.model.entities;

import Undertaker.HospiBook.model.audit.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "AvailabilitySlot")
public class AvailabilitySlot extends BaseEntity {
    @Id
    private Long id;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false)
    private boolean availability;

    @Column(nullable = false)
    private boolean deleted;

    @Version
    private Long version;

    @ManyToOne
    @JoinColumn(name = "personnelId")
    private Personnel personnel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public AvailabilitySlot(Date date, LocalDateTime startTime, LocalDateTime endTime, Personnel personnel) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.personnel = personnel;
        this.availability = true;
        this.deleted = false;
    }

    public AvailabilitySlot() {
    }

    public Personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }
}
