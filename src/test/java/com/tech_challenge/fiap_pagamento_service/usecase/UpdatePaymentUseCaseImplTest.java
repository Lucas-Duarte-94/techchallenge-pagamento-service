
package com.tech_challenge.fiap_pagamento_service.usecase;

import com.tech_challenge.fiap_pagamento_service.domain.entity.Pagamento;
import com.tech_challenge.fiap_pagamento_service.dto.PaymentStatus;
import com.tech_challenge.fiap_pagamento_service.dto.UpdatePaymentDTO;
import com.tech_challenge.fiap_pagamento_service.exception.PagamentoNotFoundException;
import com.tech_challenge.fiap_pagamento_service.gateway.PagamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UpdatePaymentUseCaseImplTest {

    @Mock
    private PagamentoRepository pagamentoRepository;

    @InjectMocks
    private UpdatePaymentUseCaseImpl updatePaymentUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecuteSuccess() {
        UpdatePaymentDTO updatePaymentDTO = new UpdatePaymentDTO("order123", PaymentStatus.APROVADO);
        Pagamento pagamento = new Pagamento();
        when(pagamentoRepository.findByPedidoId("order123")).thenReturn(Optional.of(pagamento));

        updatePaymentUseCase.execute(updatePaymentDTO);

        verify(pagamentoRepository, times(1)).save(pagamento);
        assert(pagamento.getStatus() == PaymentStatus.APROVADO);
    }

    @Test
    void testExecuteNotFound() {
        UpdatePaymentDTO updatePaymentDTO = new UpdatePaymentDTO("order123", PaymentStatus.APROVADO);
        when(pagamentoRepository.findByPedidoId("order123")).thenReturn(Optional.empty());

        assertThrows(PagamentoNotFoundException.class, () -> updatePaymentUseCase.execute(updatePaymentDTO));
    }

    @Test
    void testInvalidStatus() {
        UpdatePaymentDTO updatePaymentDTO = new UpdatePaymentDTO("order123", null);
        Pagamento pagamento = new Pagamento();
        when(pagamentoRepository.findByPedidoId("order123")).thenReturn(Optional.of(pagamento));

        assertThrows(IllegalArgumentException.class, () -> updatePaymentUseCase.execute(updatePaymentDTO));
    }
}
