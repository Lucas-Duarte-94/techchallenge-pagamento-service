
package com.tech_challenge.fiap_pagamento_service.usecase;

import com.tech_challenge.fiap_pagamento_service.domain.entity.Pagamento;
import com.tech_challenge.fiap_pagamento_service.dto.PaymentStatus;
import com.tech_challenge.fiap_pagamento_service.dto.PaymentWithStatusResponseDTO;
import com.tech_challenge.fiap_pagamento_service.exception.PagamentoNotFoundException;
import com.tech_challenge.fiap_pagamento_service.gateway.PagamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class FindByidUseCaseImplTest {

    @Mock
    private PagamentoRepository pagamentoRepository;

    @InjectMocks
    private FindByidUseCaseImpl findByidUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecuteSuccess() {
        String paymentId = "123";
        Pagamento pagamento = new Pagamento();
        pagamento.setId(paymentId);
        pagamento.setStatus(PaymentStatus.APROVADO);

        when(pagamentoRepository.findById(paymentId)).thenReturn(Optional.of(pagamento));

        PaymentWithStatusResponseDTO response = findByidUseCase.execute(paymentId);

        assertNotNull(response);
        assertEquals(paymentId, response.id());
        assertEquals(PaymentStatus.APROVADO, response.status());
    }

    @Test
    void testExecuteNotFound() {
        String paymentId = "123";
        when(pagamentoRepository.findById(paymentId)).thenReturn(Optional.empty());

        assertThrows(PagamentoNotFoundException.class, () -> findByidUseCase.execute(paymentId));
    }
}
