package com.tech_challenge.fiap_pagamento_service.dto;

public record WebhookRequestDTO(
                String pedidoId,
                ExternalPaymentStatus status) {

}
