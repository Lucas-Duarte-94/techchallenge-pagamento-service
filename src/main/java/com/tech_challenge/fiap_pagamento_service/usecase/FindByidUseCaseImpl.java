package com.tech_challenge.fiap_pagamento_service.usecase;

import org.springframework.stereotype.Component;

import com.tech_challenge.fiap_pagamento_service.dto.PaymentWithStatusResponseDTO;
import com.tech_challenge.fiap_pagamento_service.exception.PagamentoNotFoundException;
import com.tech_challenge.fiap_pagamento_service.gateway.PagamentoRepository;

@Component
public class FindByidUseCaseImpl implements FindByIdUseCase {
    private final PagamentoRepository pagamentoRepository;

    public FindByidUseCaseImpl(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    @Override
    public PaymentWithStatusResponseDTO execute(String id) {
        var pagamento = this.pagamentoRepository.findById(id).orElseThrow(() -> new PagamentoNotFoundException(id));

        return new PaymentWithStatusResponseDTO(pagamento.getId(), pagamento.getStatus());
    }

}
