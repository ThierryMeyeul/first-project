package Undertaker.HospiBook.controller;

import Undertaker.HospiBook.dto.PaymentDTO;
import Undertaker.HospiBook.model.entities.Invoice;
import Undertaker.HospiBook.model.entities.Payment;
import Undertaker.HospiBook.service.PaymentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "payment")
@SecurityRequirement(name = "bearerAuth")
public class PaymentController {

    /*private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<Payment> create(@RequestBody PaymentDTO paymentDTO){
        return ResponseEntity.ok(this.paymentService.create(paymentDTO));
    }

    @GetMapping
    public ResponseEntity<List<Payment>> read(){
        return ResponseEntity.ok(this.paymentService.read());
    }

    @GetMapping(path = "invoice/{id}")
    public ResponseEntity<Payment> readById(@PathVariable Long id){
        return ResponseEntity.ok(this.paymentService.readById(id));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Payment> update(@PathVariable Long id, @RequestBody PaymentDTO paymentDTO){
        return ResponseEntity.ok(this.paymentService.update(id, paymentDTO));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        return ResponseEntity.ok(this.paymentService.delete(id));
    }*/
}
