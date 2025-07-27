package com.tech_challenge.fiap_pagamento_service.domain.entity;

import java.math.BigDecimal;

import com.tech_challenge.fiap_pagamento_service.dto.CreditCardData;
import com.tech_challenge.fiap_pagamento_service.dto.ExternalPaymentStatus;
import com.tech_challenge.fiap_pagamento_service.dto.PaymentMethod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ExternalPaymentEntity {
    private String id;
    private String internalId;
    private BigDecimal valor;
    private PaymentMethod metodoPagamento;
    private CreditCardData creditCardData;
    private ExternalPaymentStatus status;
}
