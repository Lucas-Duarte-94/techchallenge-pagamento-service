package com.tech_challenge.fiap_pagamento_service.adapter;

import com.tech_challenge.fiap_pagamento_service.dto.ExternalPaymentStatus;
import com.tech_challenge.fiap_pagamento_service.dto.WebhookRequestDTO;
import com.tech_challenge.fiap_pagamento_service.gateway.PedidoServiceClientGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MockAsyncNotificationServiceTest {

    private MockAsyncNotificationService mockAsyncNotificationService;
    private PedidoServiceClientGateway pedidoServiceClientGateway;

    @BeforeEach
    void setUp() {
        pedidoServiceClientGateway = Mockito.mock(PedidoServiceClientGateway.class);
        mockAsyncNotificationService = new MockAsyncNotificationService(pedidoServiceClientGateway);
    }

    @Test
    void notificarPedidoService_CallsHandleWebhook() throws InterruptedException {
        String pedidoId = "test-pedido-id";
        ExternalPaymentStatus status = ExternalPaymentStatus.APROVADO;

        mockAsyncNotificationService.notificarPedidoService(pedidoId, status);

        // Wait for the async method to complete (simulated)
        Thread.sleep(50); // Small delay to allow async execution

        ArgumentCaptor<WebhookRequestDTO> webhookRequestCaptor = ArgumentCaptor.forClass(WebhookRequestDTO.class);
        verify(pedidoServiceClientGateway, times(1)).handleWebhook(webhookRequestCaptor.capture());

        WebhookRequestDTO capturedWebhookRequest = webhookRequestCaptor.getValue();
        assertEquals(pedidoId, capturedWebhookRequest.pedidoId());
        assertEquals(status, capturedWebhookRequest.status());
    }

    @Test
    void notificarPedidoService_HandlesInterruptedException() throws InterruptedException {
        String pedidoId = "test-pedido-id";
        ExternalPaymentStatus status = ExternalPaymentStatus.APROVADO;

        // Simulate InterruptedException by interrupting the thread during sleep
        doAnswer(invocation -> {
            Thread.currentThread().interrupt();
            throw new InterruptedException();
        }).when(pedidoServiceClientGateway).handleWebhook(any(WebhookRequestDTO.class));

        mockAsyncNotificationService.notificarPedidoService(pedidoId, status);

        // No assertion needed, just ensure no unhandled exception
        // The test passes if it doesn't throw an exception
    }
}
