package com.tech_challenge.fiap_pagamento_service.exception;

public class PaymentMethodNotAllowedException extends RuntimeException {
    public PaymentMethodNotAllowedException(String msg) {
        super(msg);
    }
}
