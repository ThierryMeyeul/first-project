package Undertaker.HospiBook.model.entities;

import Undertaker.HospiBook.model.audit.BaseEntity;
import Undertaker.HospiBook.model.enums.InvoiceEnum;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Invoice")
public class Invoice extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date date;

    @Version
    private Long version;

    public Invoice(Date date) {
        this.date = date;
        this.status = InvoiceEnum.PENDING;
    }

    public Invoice() {
    }

    @Override
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

    public InvoiceEnum getStatus() {
        return status;
    }

    public void setStatus(InvoiceEnum status) {
        this.status = status;
    }

    public VisitFree getVisitFree() {
        return visitFree;
    }

    public void setVisitFree(VisitFree visitFree) {
        this.visitFree = visitFree;
    }

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private InvoiceEnum status;

    @ManyToOne
    @JoinColumn(name = "visitFreeId")
    private VisitFree visitFree;

    @OneToOne
    @JoinColumn(name = "paymentId")
    private Payment payment;

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
