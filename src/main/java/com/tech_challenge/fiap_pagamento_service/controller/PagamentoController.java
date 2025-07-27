package com.tech_challenge.fiap_pagamento_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.tech_challenge.fiap_pagamento_service.dto.CreatePaymentDTO;
import com.tech_challenge.fiap_pagamento_service.dto.PaymentPublicResponseDTO;
import com.tech_challenge.fiap_pagamento_service.dto.PaymentWithStatusResponseDTO;
import com.tech_challenge.fiap_pagamento_service.dto.UpdatePaymentDTO;
import com.tech_challenge.fiap_pagamento_service.dto.WebhookRequestDTO;
import com.tech_challenge.fiap_pagamento_service.usecase.PaymentUseCase;

@RestController
@RequestMapping("/pagamento")
public class PagamentoController {
    private final PaymentUseCase paymentMethod;

    public PagamentoController(PaymentUseCase paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @PostMapping
    public ResponseEntity<PaymentPublicResponseDTO> createPayment(@RequestBody CreatePaymentDTO paymentDTO) {
        var payment = this.paymentMethod.createPayment(paymentDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(payment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentWithStatusResponseDTO> getPaymentStatus(@PathVariable("id") String id) {
        var payment = this.paymentMethod.getById(id);

        return ResponseEntity.ok().body(payment);
    }

    @PostMapping("/webhook")
    public ResponseEntity<Void> handleWebhook(@RequestBody WebhookRequestDTO requestDTO) {
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updatePagamento(@RequestBody UpdatePaymentDTO requestDTO) {

        return ResponseEntity.noContent().build();
    }

}
