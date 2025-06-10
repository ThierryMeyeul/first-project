package Undertaker.HospiBook.repository;

import Undertaker.HospiBook.model.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
