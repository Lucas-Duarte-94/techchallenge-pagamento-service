package com.tech_challenge.fiap_pagamento_service.adapter;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.tech_challenge.fiap_pagamento_service.dto.ExternalPaymentStatus;
import com.tech_challenge.fiap_pagamento_service.dto.WebhookRequestDTO;
import com.tech_challenge.fiap_pagamento_service.gateway.PedidoServiceClientGateway;

@Component
public class MockAsyncNotificationService {
    // Supondo que você tenha o gateway para o pedido-service
    private final PedidoServiceClientGateway pedidoServiceClientGateway;

    public MockAsyncNotificationService(PedidoServiceClientGateway pedidoServiceClientGateway) {
        this.pedidoServiceClientGateway = pedidoServiceClientGateway;
    }

    @Async
    public void notificarPedidoService(String pedidoId, ExternalPaymentStatus status) {
        try {
            // Simula o tempo que o processador de pagamento leva para confirmar
            Thread.sleep(5000); // Espera 5 segundos

            System.out.println("--- TAREFA ASSÍNCRONA ---");
            System.out.println("Simulação de pagamento concluída. Notificando o serviço de pedido...");
            System.out.println(pedidoId);
            WebhookRequestDTO webhookRequest = new WebhookRequestDTO(pedidoId, status);
            this.pedidoServiceClientGateway.handleWebhook(webhookRequest);
            System.out.println("-------------------------");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
