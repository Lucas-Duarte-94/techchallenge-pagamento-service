package com.tech_challenge.fiap_pagamento_service.controller;

import com.tech_challenge.fiap_pagamento_service.exception.PagamentoNotFoundException;
import com.tech_challenge.fiap_pagamento_service.exception.PaymentMethodNotAllowedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalExceptionControllerTest {

    private GlobalExceptionController globalExceptionController;

    @BeforeEach
    void setUp() {
        globalExceptionController = new GlobalExceptionController();
    }

    @Test
    void handlePagamentoNotFoundException_ReturnsNotFound() {
        String testId = "123";
        PagamentoNotFoundException ex = new PagamentoNotFoundException(testId);
        ResponseEntity<String> response = globalExceptionController.handlePagamentoNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Pagamento não encontrado para o id " + testId, response.getBody());
    }

    @Test
    void handlePaymentMethodNotAllowedException_ReturnsForbidden() {
        PaymentMethodNotAllowedException ex = new PaymentMethodNotAllowedException("Método de pagamento não permitido");
        ResponseEntity<String> response = globalExceptionController.handlePaymentMethodNotAllowedException(ex);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Método de pagamento não permitido", response.getBody());
    }
}
