package Undertaker.HospiBook.model.entities;

import Undertaker.HospiBook.model.enums.VisitTypeEnum;
import jakarta.persistence.*;
import Undertaker.HospiBook.model.enums.StatusRDVEnum;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("RDV")
public class RendezVous extends Visit{
    @Enumerated(EnumType.STRING)
    private StatusRDVEnum status;

    private LocalDateTime dateRdv;

    public RendezVous(String reason, LocalDateTime startTime, LocalDateTime endTime, VisitTypeEnum type, Patient patient, Personnel personnel, Hospital hospital, VisitFree visitFree, Invoice invoice, LocalDateTime dateRdv) {
        super(reason, startTime, endTime, type, patient, personnel, hospital, visitFree, invoice);
        this.dateRdv = dateRdv;
    }


    public StatusRDVEnum getStatus() {
        return status;
    }

    public void setStatus(StatusRDVEnum status) {
        this.status = status;
    }

    public LocalDateTime getDateRDV() {
        return dateRdv;
    }

    public void setDateRDV(LocalDateTime dateRdv) {
        this.dateRdv = dateRdv;
    }

    public RendezVous(){}
}
