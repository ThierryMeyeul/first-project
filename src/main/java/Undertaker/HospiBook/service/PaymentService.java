package Undertaker.HospiBook.service;

import Undertaker.HospiBook.dto.PaymentDTO;
import Undertaker.HospiBook.model.entities.Invoice;
import Undertaker.HospiBook.model.entities.Payment;
import Undertaker.HospiBook.model.enums.PaymentStatusEnum;
import Undertaker.HospiBook.repository.InvoiceRepository;
import Undertaker.HospiBook.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    /*private final InvoiceRepository invoiceRepository;
    private final PaymentRepository paymentRepository;

    public PaymentService(InvoiceRepository invoiceRepository, PaymentRepository paymentRepository) {
        this.invoiceRepository = invoiceRepository;
        this.paymentRepository = paymentRepository;
    }

    public Payment create(PaymentDTO paymentDTO) {
        Optional<Invoice> invoice = this.invoiceRepository.findById(paymentDTO.getInvoiceId());
        if(invoice.isEmpty()){
            return null;
        }
        Payment payment = new Payment(invoice.get(), paymentDTO.getMethod(), PaymentStatusEnum.PENDING, LocalDateTime.now());
        return this.paymentRepository.save(payment);
    }

    public List<Payment> read() {
        return this.paymentRepository.findAllByDeleted(false);
    }

    public Payment readById(Long id) {
        Invoice invoice = this.invoiceRepository.findById(id).orElseThrow(() -> new RuntimeException("invoice not found"));

        return this.paymentRepository.findByInvoice(invoice);
    }

    public Payment update(Long id, PaymentDTO paymentDTO) {
        Optional<Payment> payment = this.paymentRepository.findById(id);
        Optional<Invoice> invoice = this.invoiceRepository.findById(paymentDTO.getInvoiceId());
        if(payment.isPresent()){
            if(invoice.isEmpty()){
                return null;
            }
            return this.paymentRepository.save(payment.get());
        }
        return null;
    }

    public String delete(Long id) {
        Optional<Payment> payment = this.paymentRepository.findById(id);
        if(payment.isEmpty() || payment.get().isDeleted()){
            return "payment doesn't exists";
        }
        payment.get().setDeleted(true);
        this.paymentRepository.save(payment.get());
        return "payment deleted successful";
    }*/
}
