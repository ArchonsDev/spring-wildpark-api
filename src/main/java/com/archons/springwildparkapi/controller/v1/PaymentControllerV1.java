package com.archons.springwildparkapi.controller.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.archons.springwildparkapi.dto.requests.AddPaymentRequest;
import com.archons.springwildparkapi.dto.requests.UpdatePaymentRequest;
import com.archons.springwildparkapi.model.PaymentEntity;
import com.archons.springwildparkapi.service.PaymentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentControllerV1 {
    /*
     * This controller class handles all payment related requests
     * 
     * Endpoints:
     * POST /api/v1/payments/
     * GET /api/v1/payments/{paymentId}
     * PUT /api/v1/payments/{paymentId}
     * 
     */
    private PaymentService paymentService;

    public PaymentControllerV1(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/")
    public ResponseEntity<PaymentEntity> addPayment(@RequestHeader(name = "Authorization") String authorization,
            @RequestBody AddPaymentRequest request) throws Exception {
        return ResponseEntity.ok(paymentService.addPayment(authorization, request));
    }

    @GetMapping("/")
    public ResponseEntity<List<PaymentEntity>> getAllPayments(
            @RequestHeader(name = "Authorization") String authorization) throws Exception {
        return ResponseEntity.ok(paymentService.getAllPayments(authorization));
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentEntity> getPaymentById(
            @RequestHeader(name = "Authorization") String authorization, @PathVariable int paymentId) throws Exception {
        return ResponseEntity.ok(paymentService.getPaymentById(authorization, paymentId));
    }

    @PutMapping("/{paymentId}")
    public ResponseEntity<PaymentEntity> updatePayment(@RequestHeader(name = "Authorization") String authorization,
            @RequestBody UpdatePaymentRequest request, @PathVariable int paymentId) throws Exception {
        return ResponseEntity.ok(paymentService.updatePayment(authorization, request, paymentId));
    }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<Void> deletePayment(@RequestHeader(name = "Authorization") String authorization,
            @PathVariable int paymentId) throws Exception {
        paymentService.deletePayment(authorization, paymentId);
        return ResponseEntity.ok().build();
    }
}