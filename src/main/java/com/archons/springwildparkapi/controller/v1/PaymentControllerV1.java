package com.archons.springwildparkapi.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.archons.springwildparkapi.model.PaymentEntity;
import com.archons.springwildparkapi.service.PaymentService;

import java.util.Optional;
@RestController
@RequestMapping("/api/v1/payments")
public class PaymentControllerV1 {
    /*
     * This controller class handles all payment related requests
     * 
     * Endpoints:
     * TODO: POST /api/v1/payments/
     * TODO: GET /api/v1/payments/{paymentId}
     * TODO: PUT /api/v1/payments/{paymentId}
     * 
     */
    private PaymentService paymentService;

    @Autowired
    public PaymentControllerV1(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    @GetMapping("/print")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("OKAY!");
    }
    /*
    @PostMapping("/")
    public ResponseEntity<Optional<PaymentEntity>> addPayment(@RequestBody AccountEntity requester,
            @RequestBody PaymentEntity payment) {
        try {
            return ResponseEntity.ok(paymentService.addPayment(requester, payment));
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
     */
    @GetMapping("/{paymentId}")
    public ResponseEntity<Optional<PaymentEntity>> getPaymentById(@PathVariable int paymentId) {
            return ResponseEntity.ok(paymentService.getPaymentById(paymentId));
    }
    @PutMapping("/{paymentId}")
    public ResponseEntity<Optional<PaymentEntity>> updatePayment(
            @RequestBody PaymentEntity updatedPayment, @RequestBody int paymentId) {
        return ResponseEntity.ok(paymentService.updatePayment(updatedPayment));
    }
    @DeleteMapping("/{paymentId}")
    public ResponseEntity<Void> deletePayment(@PathVariable int paymentId) {
            if(paymentService.deletePayment(paymentId)){
                return ResponseEntity.ok().build();
            }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    /* 
    //naa try catch
    @GetMapping("/{paymentId}")
    public ResponseEntity<Optional<PaymentEntity>> getPaymentById(
            @PathVariable int paymentId) {
                System.out.println("getPaymnetById: " + paymentId);
        try {
            return ResponseEntity.ok(paymentService.getPaymentById(paymentId));
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } 
    }
    @PutMapping("/{paymentId}")
    public ResponseEntity<Optional<PaymentEntity>> updatePayment(
            @RequestBody PaymentEntity updatedPayment, @PathVariable int paymentId) {
        try {
            return ResponseEntity.ok(paymentService.updatePayment(updatedPayment));
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
    @DeleteMapping("/{paymentId}")
    public ResponseEntity<Void> deletePayment(@RequestParam int paymentId) {
        try {
            if(paymentService.deletePayment(paymentId)){
                return ResponseEntity.ok().build();
            }
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

     */
    /*Testing path variable
    @GetMapping("/{paymentId}")
	public String getAllPayment(@PathVariable int paymentId){
        return "The id is: " + paymentId;
	}
    */
    //Testing
}