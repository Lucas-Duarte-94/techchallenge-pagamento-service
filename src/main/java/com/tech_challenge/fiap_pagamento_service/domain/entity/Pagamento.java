package com.tech_challenge.fiap_pagamento_service.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.tech_challenge.fiap_pagamento_service.dto.PaymentMethod;
import com.tech_challenge.fiap_pagamento_service.dto.PaymentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String pedidoId;

    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private PaymentMethod metodoPagamento;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataAtualizacao;

    private String transacaoIdExterna;
}