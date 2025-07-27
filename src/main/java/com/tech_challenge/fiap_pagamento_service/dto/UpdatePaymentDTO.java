package com.tech_challenge.fiap_pagamento_service.dto;

public record UpdatePaymentDTO(
        String pedidoId,
        PaymentStatus status) {

}
