
package com.tech_challenge.fiap_pagamento_service.usecase;

import com.tech_challenge.fiap_pagamento_service.domain.entity.Pagamento;
import com.tech_challenge.fiap_pagamento_service.dto.*;
import com.tech_challenge.fiap_pagamento_service.exception.PaymentMethodNotAllowedException;
import com.tech_challenge.fiap_pagamento_service.gateway.CreditCardPaymentGateway;
import com.tech_challenge.fiap_pagamento_service.gateway.PagamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CreatePaymentUseCaseImplTest {

    @Mock
    private PagamentoRepository pagamentoRepository;

    @Mock
    private CreditCardPaymentGateway creditCardPaymentGateway;

    @InjectMocks
    private CreatePaymentUseCaseImpl createPaymentUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecuteCreditCardPayment() {
        CreditCardRequestDTO creditCardRequestDTO = new CreditCardRequestDTO("1234567890123456", "123", "Test Owner", "12/25");
        CreatePaymentDTO createPaymentDTO = new CreatePaymentDTO("order123", new java.math.BigDecimal("100.00"), PaymentMethod.CARTAO, creditCardRequestDTO);

        ExternalPaymentResponseDTO externalPaymentResponseDTO = new ExternalPaymentResponseDTO("ext123");
        when(creditCardPaymentGateway.processCreditCardPayment(any(Pagamento.class), any(CreditCardData.class))).thenReturn(externalPaymentResponseDTO);

        Pagamento savedPagamento = new Pagamento();
        savedPagamento.setId("pay123");
        savedPagamento.setStatus(PaymentStatus.APROVADO);
        when(pagamentoRepository.save(any(Pagamento.class))).thenReturn(savedPagamento);

        PaymentPublicResponseDTO response = createPaymentUseCase.execute(createPaymentDTO);

        assertNotNull(response);
        assertEquals("pay123", response.id());
        assertEquals(PaymentStatus.APROVADO, response.status());
    }

    @Test
    void testExecuteBoletoPayment() {
        CreatePaymentDTO createPaymentDTO = new CreatePaymentDTO("order123", new java.math.BigDecimal("100.00"), PaymentMethod.BOLETO, null);
        assertThrows(PaymentMethodNotAllowedException.class, () -> createPaymentUseCase.execute(createPaymentDTO));
    }

    @Test
    void testExecutePixPayment() {
        CreatePaymentDTO createPaymentDTO = new CreatePaymentDTO("order123", new java.math.BigDecimal("100.00"), PaymentMethod.PIX, null);
        assertThrows(PaymentMethodNotAllowedException.class, () -> createPaymentUseCase.execute(createPaymentDTO));
    }
}
