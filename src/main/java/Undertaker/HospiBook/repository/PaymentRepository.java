package Undertaker.HospiBook.repository;

import Undertaker.HospiBook.model.entities.Invoice;
import Undertaker.HospiBook.model.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllByDeleted(boolean deleted);
}
