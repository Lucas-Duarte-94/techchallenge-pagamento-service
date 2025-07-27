package com.tech_challenge.fiap_pagamento_service.gateway;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tech_challenge.fiap_pagamento_service.domain.entity.Pagamento;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, String> {
    Optional<Pagamento> findByPedidoId(String pedidoId);
}
