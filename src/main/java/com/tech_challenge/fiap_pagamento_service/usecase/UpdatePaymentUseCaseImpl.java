package com.tech_challenge.fiap_pagamento_service.usecase;

import com.tech_challenge.fiap_pagamento_service.dto.PaymentStatus;
import com.tech_challenge.fiap_pagamento_service.dto.UpdatePaymentDTO;
import com.tech_challenge.fiap_pagamento_service.exception.PagamentoNotFoundException;
import com.tech_challenge.fiap_pagamento_service.gateway.PagamentoRepository;

import java.util.Arrays;

import org.springframework.stereotype.Component;

@Component
public class UpdatePaymentUseCaseImpl implements UpdatePaymentUseCase {
    private final PagamentoRepository pagamentoRepository;

    public UpdatePaymentUseCaseImpl(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    @Override
    public void execute(UpdatePaymentDTO request) {
        if (Arrays.stream(PaymentStatus.values()).noneMatch(s -> s.name().equals(request.status()))) {
            throw new IllegalArgumentException("Status de pagamento inválido: " + request.status());
        }

        var pagamento = this.pagamentoRepository.findByPedidoId(request.pedidoId())
                .orElseThrow(() -> new PagamentoNotFoundException(request.pedidoId()));

        pagamento.setStatus(request.status());
        pagamentoRepository.save(pagamento);
    }

}
