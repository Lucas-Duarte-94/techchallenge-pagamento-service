package com.tech_challenge.fiap_pagamento_service.dto;

import java.math.BigDecimal;

public record CreatePaymentDTO(
        String pedidoId,
        BigDecimal valor,
        PaymentMethod metodoPagamento,
        CreditCardRequestDTO creditCard) {

}
