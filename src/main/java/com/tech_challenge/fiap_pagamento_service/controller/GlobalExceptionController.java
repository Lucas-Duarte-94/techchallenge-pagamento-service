package com.tech_challenge.fiap_pagamento_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tech_challenge.fiap_pagamento_service.exception.PagamentoNotFoundException;
import com.tech_challenge.fiap_pagamento_service.exception.PaymentMethodNotAllowedException;

@RestControllerAdvice
public class GlobalExceptionController {
    @ExceptionHandler(PagamentoNotFoundException.class)
    public ResponseEntity<String> handlePagamentoNotFoundException(PagamentoNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(PaymentMethodNotAllowedException.class)
    public ResponseEntity<String> handlePaymentMethodNotAllowedException(PaymentMethodNotAllowedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }
}
