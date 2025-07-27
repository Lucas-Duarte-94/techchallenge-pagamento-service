package com.tech_challenge.fiap_pagamento_service.exception;

public class PagamentoNotFoundException extends RuntimeException {
    public PagamentoNotFoundException(String id) {
        super("Pagamento não encontrado para o id " + id);
    }
}
