package com.archons.springwildparkapi.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.archons.springwildparkapi.service.PaymentService;

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

}
