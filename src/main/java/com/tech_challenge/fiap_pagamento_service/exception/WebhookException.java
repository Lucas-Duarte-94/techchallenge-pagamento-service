package com.tech_challenge.fiap_pagamento_service.exception;

public class WebhookException extends RuntimeException {
    public WebhookException(String msg) {
        super(msg);
    }
}
