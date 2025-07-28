package com.tech_challenge.fiap_pagamento_service.adapter;

import com.tech_challenge.fiap_pagamento_service.domain.entity.Pagamento;
import com.tech_challenge.fiap_pagamento_service.dto.CreditCardData;
import com.tech_challenge.fiap_pagamento_service.dto.ExternalPaymentResponseDTO;
import com.tech_challenge.fiap_pagamento_service.dto.ExternalPaymentStatus;
import com.tech_challenge.fiap_pagamento_service.dto.PaymentMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

public class CreditCardPaymentMockTest {

    private CreditCardPaymentMock creditCardPaymentMock;
    private MockAsyncNotificationService mockAsyncNotificationService;

    @BeforeEach
    void setUp() {
        mockAsyncNotificationService = Mockito.mock(MockAsyncNotificationService.class);
        creditCardPaymentMock = new CreditCardPaymentMock(mockAsyncNotificationService);
    }

    @Test
    void processCreditCardPayment_Approved() {
        Pagamento pagamento = Pagamento.builder()
                .id(UUID.randomUUID().toString())
                .pedidoId(UUID.randomUUID().toString())
                .valor(BigDecimal.valueOf(100.00))
                .metodoPagamento(PaymentMethod.CARTAO)
                .build();
        CreditCardData creditCardData = new CreditCardData("1234-5678-9012-3456", "123", "Test User", YearMonth.of(2025, 12));

        ExternalPaymentResponseDTO response = creditCardPaymentMock.processCreditCardPayment(pagamento, creditCardData);

        assertNotNull(response);
        assertNotNull(response.id());

        ArgumentCaptor<String> pedidoIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<ExternalPaymentStatus> statusCaptor = ArgumentCaptor.forClass(ExternalPaymentStatus.class);
        verify(mockAsyncNotificationService).notificarPedidoService(pedidoIdCaptor.capture(), statusCaptor.capture());

        assertEquals(pagamento.getPedidoId(), pedidoIdCaptor.getValue());
        assertEquals(ExternalPaymentStatus.APROVADO, statusCaptor.getValue());
    }

    @Test
    void processCreditCardPayment_Declined() {
        Pagamento pagamento = Pagamento.builder()
                .id(UUID.randomUUID().toString())
                .pedidoId(UUID.randomUUID().toString())
                .valor(BigDecimal.valueOf(50.00))
                .metodoPagamento(PaymentMethod.CARTAO)
                .build();
        CreditCardData creditCardData = new CreditCardData("9999-9999-9999-9999", "123", "Test User", YearMonth.of(2025, 12));

        ExternalPaymentResponseDTO response = creditCardPaymentMock.processCreditCardPayment(pagamento, creditCardData);

        assertNotNull(response);
        assertNotNull(response.id());

        ArgumentCaptor<String> pedidoIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<ExternalPaymentStatus> statusCaptor = ArgumentCaptor.forClass(ExternalPaymentStatus.class);
        verify(mockAsyncNotificationService).notificarPedidoService(pedidoIdCaptor.capture(), statusCaptor.capture());

        assertEquals(pagamento.getPedidoId(), pedidoIdCaptor.getValue());
        assertEquals(ExternalPaymentStatus.RECUSADO, statusCaptor.getValue());
    }
}
