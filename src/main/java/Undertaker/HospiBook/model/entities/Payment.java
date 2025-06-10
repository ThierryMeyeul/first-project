package Undertaker.HospiBook.model.entities;

import Undertaker.HospiBook.model.audit.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import Undertaker.HospiBook.model.enums.PaymentMethodEnum;
import Undertaker.HospiBook.model.enums.PaymentStatusEnum;

@Entity
@Table(name = "Payment")
public class Payment extends BaseEntity {
    @Id
    private Long id;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatusEnum status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethodEnum method;

    @Version
    private Long version;

    private boolean deleted;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public PaymentStatusEnum getStatus() {
        return status;
    }

    public void setStatus(PaymentStatusEnum status) {
        this.status = status;
    }

    public PaymentMethodEnum getMethod() {
        return method;
    }

    public void setMethod(PaymentMethodEnum method) {
        this.method = method;
    }


    public Payment(PaymentMethodEnum method, PaymentStatusEnum status, LocalDateTime date) {
        this.method = method;
        this.status = status;
        this.date = date;
        this.deleted = false;
    }

    public Payment() {
    }
}
