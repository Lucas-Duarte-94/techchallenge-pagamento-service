package com.tech_challenge.fiap_pagamento_service.dto;

public record PaymentWithStatusResponseDTO(
        String id,
        PaymentStatus status) {

}
