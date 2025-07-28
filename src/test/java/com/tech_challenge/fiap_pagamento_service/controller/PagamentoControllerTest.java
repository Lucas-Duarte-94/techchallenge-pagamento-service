
package com.tech_challenge.fiap_pagamento_service.controller;

import com.tech_challenge.fiap_pagamento_service.dto.*;
import com.tech_challenge.fiap_pagamento_service.usecase.PaymentUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class PagamentoControllerTest {

    @Mock
    private PaymentUseCase paymentUseCase;

    @InjectMocks
    private PagamentoController pagamentoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePayment() {
        CreditCardRequestDTO creditCardRequestDTO = new CreditCardRequestDTO("1234567890123456", "123", "Test Owner", "12/25");
        CreatePaymentDTO createPaymentDTO = new CreatePaymentDTO("order123", new java.math.BigDecimal("100.00"), PaymentMethod.CARTAO, creditCardRequestDTO);
        PaymentPublicResponseDTO paymentPublicResponseDTO = new PaymentPublicResponseDTO("pay123", PaymentStatus.PENDENTE);
        when(paymentUseCase.createPayment(createPaymentDTO)).thenReturn(paymentPublicResponseDTO);

        ResponseEntity<PaymentPublicResponseDTO> response = pagamentoController.createPayment(createPaymentDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(paymentPublicResponseDTO, response.getBody());
    }

    @Test
    void testGetPaymentStatus() {
        String paymentId = "123";
        PaymentWithStatusResponseDTO paymentWithStatusResponseDTO = new PaymentWithStatusResponseDTO(paymentId, PaymentStatus.APROVADO);
        when(paymentUseCase.getById(paymentId)).thenReturn(paymentWithStatusResponseDTO);

        ResponseEntity<PaymentWithStatusResponseDTO> response = pagamentoController.getPaymentStatus(paymentId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(paymentWithStatusResponseDTO, response.getBody());
    }

    @Test
    void testHandleWebhook() {
        WebhookRequestDTO webhookRequestDTO = new WebhookRequestDTO("order123", ExternalPaymentStatus.APROVADO);
        ResponseEntity<Void> response = pagamentoController.handleWebhook(webhookRequestDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdatePagamento() {
        UpdatePaymentDTO updatePaymentDTO = new UpdatePaymentDTO("order123", PaymentStatus.APROVADO);
        ResponseEntity<Void> response = pagamentoController.updatePagamento(updatePaymentDTO);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
